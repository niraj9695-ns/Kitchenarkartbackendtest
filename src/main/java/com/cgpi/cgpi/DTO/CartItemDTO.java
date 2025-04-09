package com.cgpi.cgpi.DTO;

import java.math.BigDecimal;
import java.util.List;


public class CartItemDTO {
	private Long cartItemId;
	private Long productId;
	private String productName;
	private Long variantId;
	private String color;
	private BigDecimal price;
	private Integer quantity;
	private List<byte[]> image;

	public Long getCartItemId() {
		return cartItemId;
	}

	public void setCartItemId(Long cartItemId) {
		this.cartItemId = cartItemId;
	}

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public Long getVariantId() {
		return variantId;
	}

	public void setVariantId(Long variantId) {
		this.variantId = variantId;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public void setImage(List<byte[]> image) {
		// TODO Auto-generated method stub
		this.image = image;
	}

	public List<byte[]> getImage(List<byte[]> image) {
		// TODO Auto-generated method stub
		return image;
	}
}