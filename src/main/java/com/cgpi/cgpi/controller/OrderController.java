package com.cgpi.cgpi.controller;

import java.util.List;
import java.util.Map;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.cgpi.cgpi.entity.Order;
import com.cgpi.cgpi.services.EmailServices;
import com.cgpi.cgpi.services.OrderService;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/orders")
public class OrderController {

	@Autowired
	private OrderService orderService;
	
	@Autowired
	private EmailServices emailServices;

	@PostMapping("/razorpay-order")
	public ResponseEntity<Map<String, Object>> createRazorpayOrder(@RequestParam Long userId) {
		JSONObject razorpayOrder = orderService.createRazorpayOrder(userId);
		return ResponseEntity.ok(razorpayOrder.toMap());
	}

	@PostMapping("/confirm-payment")
	public ResponseEntity<Order> confirmPayment(@RequestBody Map<String, String> paymentDetails) {
		try {
			Order order = orderService.verifyPayment(paymentDetails);
			return ResponseEntity.ok(order);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
		}
	}

	@GetMapping("/user/{userId}")
	public ResponseEntity<List<Order>> getOrdersByUser(@PathVariable Long userId) {
		List<Order> orders = orderService.getOrdersByUser(userId);
		return ResponseEntity.ok(orders);
	}
	
	@GetMapping("/{orderId}")
	public ResponseEntity<Order> getOrderById(@PathVariable Long orderId) {
	    Order order = orderService.getOrderById(orderId);

	    if (order == null) {
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
	    }

	    return ResponseEntity.ok(order);
	}


	@GetMapping("/admin/all")
	public ResponseEntity<List<Order>> getAllOrdersForAdmin() {
		List<Order> orders = orderService.getAllOrders();
		return ResponseEntity.ok(orders);
	}
	
//	@PostMapping("/admin/update-status/{orderId}")
//    public ResponseEntity<?> updateOrderStatus(@PathVariable Long orderId, @RequestParam String status) {
//        try {
//            Order order = orderService.updateOrderStatus(orderId, status);
//
//            // ✅ Send Order Status Update Email
//            String userEmail = order.getUser().getEmail();
//            String subject = "Order Status Updated";
//            String message = "Dear " + order.getUser().getUsername() + ",\n\n"
//                    + "Your order (ID: " + order.getId() + ") status has been updated to: " + status + ".\n\n"
//                    + "Thank you for shopping with us!";
//
//            emailServices.sendEmail(userEmail, subject, message);
//
//            return ResponseEntity.ok("Order status updated and email sent successfully.");
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error updating status: " + e.getMessage());
//        }
//    }
	@PostMapping("/admin/update-status/{orderId}")
	public ResponseEntity<?> updateOrderStatus(@PathVariable Long orderId, @RequestParam String status) {
	    try {
	        Order order = orderService.updateOrderStatus(orderId, status);

	        // ✅ Send Order Status Update Email
	        String userEmail = order.getUser().getEmail();
	        String subject = "Order Status Update - Order #" + order.getId();

	        String message = "Dear " + order.getUser().getUsername() + ",\n\n"
	                + "We would like to inform you that the status of your order (ID: " + order.getId() + ") "
	                + "has been updated to: " + status.toUpperCase() + ".\n\n"
	                + "📌 Order Details:\n"
	                + "------------------------------------\n"
	                + "🔹 Order ID: " + order.getId() + "\n"
	                + "🔹 Current Status: " + status + "\n"
	                + "🔹 Order Date: " + order.getOrderDate() + "\n"
	                + "🔹 Total Amount: $" + order.getFinalAmount() + "\n"
	                + "------------------------------------\n\n"
	                + "🚚 If your order is shipped, you will receive a tracking number soon.\n"
	                + "📦 If your order is delivered, we hope you enjoy your purchase!\n"
	                + "❌ If your order is cancelled, any applicable refunds will be processed soon.\n\n"
	                + "If you have any questions, feel free to contact our support team.\n\n"
	                + "Thank you for shopping with us!\n"
	                + "Best regards,\n"
	                + "Your Company Name\n"
	                + "Customer Support Team\n"
	                + "📧 support@yourcompany.com | ☎ +1-800-123-4567";

	        emailServices.sendEmail(userEmail, subject, message);

	        return ResponseEntity.ok("Order status updated and email sent successfully.");
	    } catch (Exception e) {
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error updating status: " + e.getMessage());
	    }
	}
	
	@PostMapping("/send")
    public ResponseEntity<String> sendTestEmail(@RequestParam String to) {
        emailServices.sendEmail(to, "Test Email", "This is a test email from your Spring Boot application.");
        return ResponseEntity.ok("Email sent successfully to " + to);
    }
}