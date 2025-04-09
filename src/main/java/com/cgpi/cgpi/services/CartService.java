package com.cgpi.cgpi.services;



import com.cgpi.cgpi.entity.*;
import com.cgpi.cgpi.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductVariantRepository productVariantRepository;

    public Cart getCartByUserId(Long userId) {
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new RuntimeException("User not found"));

        return cartRepository.findByUser(user)
            .orElseGet(() -> {
                Cart newCart = new Cart();
                newCart.setUser(user);
                return cartRepository.save(newCart);
            });
    }

    public Cart addItemToCart(Long userId, Long productId, Long productVariantId, Integer quantity) {
        Cart cart = getCartByUserId(userId);

        Product product = productRepository.findById(productId)
            .orElseThrow(() -> new RuntimeException("Product not found"));

        ProductVariant productVariant = productVariantRepository.findById(productVariantId)
            .filter(variant -> variant.getProduct().getId().equals(productId))
            .orElseThrow(() -> new RuntimeException("Product variant not found or does not match the product"));

        Optional<CartItem> existingItem = cart.getItems().stream()
            .filter(item -> item.getProductVariant().getId().equals(productVariantId))
            .findFirst();

        if (existingItem.isPresent()) {
            CartItem item = existingItem.get();
            item.setQuantity(item.getQuantity() + quantity);
        } else {
            CartItem newItem = new CartItem();
            newItem.setProduct(product);
            newItem.setCart(cart);
            newItem.setProductVariant(productVariant);
            newItem.setQuantity(quantity);
            cart.getItems().add(newItem);
        }

        return cartRepository.save(cart);
    }

    public Cart updateItemQuantity(Long userId, Long productId, Long productVariantId, Integer quantity) {
        Cart cart = getCartByUserId(userId);

        CartItem item = cart.getItems().stream()
            .filter(cartItem -> cartItem.getProductVariant().getId().equals(productVariantId) &&
                cartItem.getProductVariant().getProduct().getId().equals(productId))
            .findFirst()
            .orElseThrow(() -> new RuntimeException("Product not in cart"));

        if (quantity <= 0) {
            cart.getItems().remove(item);
        } else {
            item.setQuantity(quantity);
        }

        return cartRepository.save(cart);
    }

    public void removeCart(Long userId, Long itemId) {
        Cart cart = getCartByUserId(userId);

        if (itemId != null) {
            CartItem item = cart.getItems().stream()
                .filter(cartItem -> cartItem.getId().equals(itemId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Item not found in cart"));

            cart.getItems().remove(item);
            cartRepository.save(cart); // Save after removing item
        } else {
            cartRepository.delete(cart); // Delete entire cart if no itemId provided
        }
    }

}