package com.cgpi.cgpi.entity;

import java.math.BigDecimal;
import java.util.Optional;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.*;

@Entity
@Cacheable
public class OrderItem {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "order_id", nullable = false)
	// Use @JsonBackReference to prevent serialization of the 'order' field
	@JsonBackReference
	private Order order;

	@ManyToOne
	@JoinColumn(name = "product_id", nullable = false)
	private Product product;

	// Getters and Setters

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal bigDecimal) {
		this.price = bigDecimal;
	}

	private Integer quantity;
	private BigDecimal price;

	public void setProduct(Optional<Product> productById) {
		// TODO Auto-generated method stub

	}

}