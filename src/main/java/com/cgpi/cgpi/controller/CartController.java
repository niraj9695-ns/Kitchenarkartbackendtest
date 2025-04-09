package com.cgpi.cgpi.controller;



import com.cgpi.cgpi.entity.Cart;
import com.cgpi.cgpi.services.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping("/api/carts")
public class CartController {

	@Autowired
	private CartService cartService;

	@GetMapping("/{userId}")
	public ResponseEntity<Cart> getCart(@PathVariable Long userId) {
		return ResponseEntity.ok(cartService.getCartByUserId(userId));
	}

	@PostMapping("/{userId}/add")
	public ResponseEntity<Cart> addItemToCart(@PathVariable Long userId, @RequestParam Long productId,
			@RequestParam Long productVariantId, @RequestParam Integer quantity) {
		return ResponseEntity.ok(cartService.addItemToCart(userId, productId, productVariantId, quantity));
	}

	@PutMapping("/{userId}/update")
	public ResponseEntity<Cart> updateItemQuantity(@PathVariable Long userId, @RequestParam Long productId,
			@RequestParam Long productVariantId, @RequestParam Integer quantity) {
		return ResponseEntity.ok(cartService.updateItemQuantity(userId, productId, productVariantId, quantity));
	}

//    @DeleteMapping("/{userId}/clear")
//    public ResponseEntity<Void> clearCart(@PathVariable Long userId) {
//        cartService.removeCart(userId);
//        return ResponseEntity.noContent().build();
//    }
	@DeleteMapping("/{userId}/clear")
	public ResponseEntity<Void> clearCart(@PathVariable Long userId, @RequestParam(required = false) Long itemId) {
		cartService.removeCart(userId, itemId);
		return ResponseEntity.noContent().build();
	}

}