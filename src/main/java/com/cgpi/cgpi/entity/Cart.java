package com.cgpi.cgpi.entity;


import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;


@Entity
@Table(name = "carts")
public class Cart {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	// One-to-One relationship with the User entity
	// Ensures each user has only one cart, and it's linked by user_id in the
	// database
	@OneToOne
	@JoinColumn(name = "user_id", nullable = false)
	@JsonIgnore
	private User user;

	// One-to-Many relationship with CartItem
	// CascadeType.ALL: Operations on Cart will cascade to its items (e.g., save,
	// delete)
	// OrphanRemoval: Items will be deleted when removed from the cart
	@OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<CartItem> items = new ArrayList<>();

	// Default constructor (required by JPA)
	public Cart() {
	}

	// Getter and Setter for ID
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	// Getter and Setter for User
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	// Getter and Setter for Cart Items
	public List<CartItem> getItems() {
		return items;
	}

	public void setItems(List<CartItem> items) {
		this.items = items;
	}

	/**
	 * Calculate the total price of all items in the cart.
	 * 
	 * - Uses Java Streams to iterate over all items. - For each item, calls its
	 * getTotalPrice() method. - Reduces the stream to calculate the total sum. -
	 * BigDecimal.ZERO is used as the initial value for safety in arithmetic
	 * operations.
	 * 
	 * @return Total price of the cart as BigDecimal.
	 */
	public BigDecimal getTotalPrice() {
		return items.stream().map(CartItem::getTotalPrice) // Get total price of each cart item
				.reduce(BigDecimal.ZERO, BigDecimal::add); // Sum up all item prices
	}
}