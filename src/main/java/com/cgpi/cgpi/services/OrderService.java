package com.cgpi.cgpi.services;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cgpi.cgpi.DTO.OrderRequest;
import com.cgpi.cgpi.entity.*;
import com.cgpi.cgpi.repository.OrderRepository;
import com.cgpi.cgpi.repository.OrderItemRepository;
import com.razorpay.RazorpayClient;
import com.razorpay.Utils;

@Service
public class OrderService {

	@Autowired
	private OrderRepository orderRepository;

	@Autowired
	private OrderItemRepository orderItemRepository;

	@Autowired
	private CartService cartService;

	@Autowired
	private UserService userService;

	@Autowired
	private ShippingService shippingService;

	private RazorpayClient razorpayClient;
	
	@Autowired
	private EmailService emailService;

	private Map<String, Long> pendingOrders = new HashMap<>(); // Temporary storage for orders

	public OrderService() throws Exception {
		this.razorpayClient = new RazorpayClient("rzp_test_O0q3Xj5r9INCmy", "XaOS375yVOmG7NGcvvfhwhCz");
	}

	// Razorpay Order Creation (Does NOT save in DB)
	public JSONObject createRazorpayOrder(Long userId) {
		try {
			User user = userService.getUserById(userId);
			Cart cart = cartService.getCartByUserId(userId);

			String region = user.getState();
			if (region == null || region.isEmpty()) {
				throw new RuntimeException("User's region (state) is not specified");
			}

			Double shippingCharges = shippingService.calculateShipping(region, cart.getTotalPrice().doubleValue());

			double totalAmount = 0.0;
			for (CartItem cartItem : cart.getItems()) {
				totalAmount += cartItem.getProductVariant().getPrice().doubleValue() * cartItem.getQuantity();
			}

			double finalAmount = totalAmount + shippingCharges;

			JSONObject orderRequest = new JSONObject();
			orderRequest.put("amount", (int) (finalAmount * 100)); // Convert to paise
			orderRequest.put("currency", "INR");
			orderRequest.put("receipt", "txn_" + System.currentTimeMillis());

			com.razorpay.Order razorpayOrder = razorpayClient.orders.create(orderRequest);

			// Store the order against razorpay_order_id (without saving in DB yet)
			pendingOrders.put(razorpayOrder.get("id"), userId);

			return razorpayOrder.toJson();
		} catch (Exception e) {
			throw new RuntimeException("Error creating Razorpay order: " + e.getMessage());
		}
	}

	
	public Order verifyPayment(Map<String, String> paymentDetails) throws Exception {
	    try {
	        String razorpayOrderId = paymentDetails.get("razorpay_order_id");
	        String razorpayPaymentId = paymentDetails.get("razorpay_payment_id");
	        String razorpaySignature = paymentDetails.get("razorpay_signature");

	        // ✅ Verify Razorpay Payment Signature
	        JSONObject paymentDetailsJson = new JSONObject(paymentDetails);
	        Utils.verifyPaymentSignature(paymentDetailsJson, "XaOS375yVOmG7NGcvvfhwhCz");

	        Long userId = pendingOrders.get(razorpayOrderId);
	        if (userId == null) {
	            throw new RuntimeException("No pending order found for this payment");
	        }

	        // ✅ Fetch User Details
	        User user = userService.getUserById(userId);
	        OrderRequest orderRequest = new OrderRequest();
	        orderRequest.setAddress(user.getAddress());
	        orderRequest.setState(user.getState());
	        orderRequest.setCountry(user.getCountry());
	        orderRequest.setPincode(user.getPincode());
	        orderRequest.setContactNumber(user.getContactNumber());

	        // ✅ Create and Save Order
	        Order order = createOrder(userId, razorpayPaymentId, orderRequest);
	        pendingOrders.remove(razorpayOrderId);

	        // ✅ Send Text-Based Invoice Email
	        sendOrderInvoiceEmail(user, order);

	        return order;
	    } catch (Exception e) {
	        throw new RuntimeException("Payment verification failed: " + e.getMessage());
	    }
	}

	private void sendOrderInvoiceEmail(User user, Order order) {
	    String subject = "Order Confirmation - #" + order.getId();
	    
	    StringBuilder message = new StringBuilder();
	    message.append("Dear ").append(user.getUsername()).append(",\n\n")
	           .append("Thank you for your order! Here are your order details:\n\n")
	           .append("Order ID: ").append(order.getId()).append("\n")
	           .append("Order Date: ").append(order.getOrderDate()).append("\n")
	           .append("Status: ").append(order.getStatus()).append("\n")
	           .append("Shipping Address:\n")
	           .append(order.getAddress()).append(", ")
	           .append(order.getState()).append(", ")
	           .append(order.getCountry()).append(", ")
	           .append("Pincode: ").append(order.getPincode()).append("\n\n")
	           .append("Order Items:\n");

	    for (OrderItem item : order.getItems()) {
	        message.append("- ").append(item.getProduct().getName())
	               .append(" (Qty: ").append(item.getQuantity())
	               .append(", Price: ₹").append(item.getPrice()).append(")\n");
	    }

	    message.append("\nSubtotal: ₹").append(order.getTotalAmount())
	           .append("\nShipping Charges: ₹").append(order.getShippingCharges())
	           .append("\nFinal Amount: ₹").append(order.getFinalAmount())
	           .append("\n\nWe appreciate your business!\n\nBest Regards,\nE-Commerce Team");

	    // ✅ Send Email
	    emailService.sendEmail(user.getEmail(), subject, message.toString());
	}


	private Order createOrder(Long userId, String paymentId, OrderRequest orderRequest) {
		User user = userService.getUserById(userId);
		Cart cart = cartService.getCartByUserId(userId);

		if (cart == null || cart.getItems().isEmpty()) {
			throw new RuntimeException("Cart is empty. Cannot place order.");
		}

		Order order = new Order();
		order.setUser(user);
		order.setStatus(Order.STATUS_PLACED);
		order.setOrderDate(LocalDateTime.now());

		// ✅ Store the correct shipping address
		order.setAddress(orderRequest != null && orderRequest.getAddress() != null ? orderRequest.getAddress()
				: user.getAddress());
		order.setState(
				orderRequest != null && orderRequest.getState() != null ? orderRequest.getState() : user.getState());
		order.setCountry(orderRequest != null && orderRequest.getCountry() != null ? orderRequest.getCountry()
				: user.getCountry());
		order.setPincode(orderRequest != null && orderRequest.getPincode() != null ? orderRequest.getPincode()
				: user.getPincode());
		order.setContactNumber(
				orderRequest != null && orderRequest.getContactNumber() != null ? orderRequest.getContactNumber()
						: user.getContactNumber());

		if (order.getState() == null || order.getState().isEmpty()) {
			throw new RuntimeException("Shipping state is required.");
		}

		// ✅ Calculate Shipping Charges
		Double shippingCharges = shippingService.calculateShipping(order.getState(),
				cart.getTotalPrice().doubleValue());
		order.setShippingCharges(shippingCharges);

		List<OrderItem> orderItems = new ArrayList<>();
		double totalAmount = 0.0;

		for (CartItem cartItem : cart.getItems()) {
			ProductVariant variant = cartItem.getProductVariant();
			if (variant.getStock() < cartItem.getQuantity()) {
				throw new RuntimeException("Not enough stock for variant: " + variant.getProductName());
			}

			Product product = variant.getProduct();
			OrderItem orderItem = new OrderItem();
			orderItem.setProduct(product);
			orderItem.setQuantity(cartItem.getQuantity());
			orderItem.setPrice(variant.getPrice().multiply(BigDecimal.valueOf(cartItem.getQuantity())));
			orderItem.setOrder(order);
			orderItems.add(orderItem);

			totalAmount += orderItem.getPrice().doubleValue();

			// ✅ Reduce Stock
			variant.setStock(variant.getStock() - cartItem.getQuantity());
			
		}

		
		order.setTotalAmount(totalAmount);
		order.setFinalAmount(totalAmount + shippingCharges);
		order.setItems(orderItems);

		// ✅ Save Order
		orderRepository.save(order);
		orderItemRepository.saveAll(orderItems);

		return order;
	}

	// Get Orders by User
	public List<Order> getOrdersByUser(Long userId) {
		User user = userService.getUserById(userId);
		return orderRepository.findByUser(user);
	}

	// Fetch All Orders for Admin
	public List<Order> getAllOrders() {
		return orderRepository.findAll();
	}

	// Validate Status
	private boolean isValidStatus(String status) {
	    status = status.trim().toLowerCase(); // ✅ Convert input to lowercase

	    return status.equals("pending") || 
	           status.equals("placed") || 
	           status.equals("processing") || 
	           status.equals("shipped") || 
	           status.equals("delivered") || 
	           status.equals("failed") || 
	           status.equals("canceled") || 
	           status.equals("refunded");
	}

	
	public Order getOrderByUser(Long orderId) {
	    return orderRepository.findById(orderId).orElse(null);
	}
	
	public Order getOrderById(Long orderId) {
	    return orderRepository.findById(orderId)
	            .map(order -> {
	                order.getItems().size(); // ✅ Force load cart items
	                return order;
	            })
	            .orElse(null);
	}


	public Order updateOrderStatus(Long orderId, String status) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found with ID: " + orderId));

        order.setStatus(status);
        return orderRepository.save(order);
    }
}
