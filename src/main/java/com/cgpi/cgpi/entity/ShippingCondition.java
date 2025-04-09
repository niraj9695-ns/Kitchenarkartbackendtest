package com.cgpi.cgpi.entity;

import java.time.LocalDateTime;

import jakarta.persistence.*;

@Entity
public class ShippingCondition {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String region;
    private Double minCartTotal;
    private Double maxCartTotal;
    private Double shippingPrice;
    private Boolean active;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    
    // Getters and Setters
    
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getRegion() {
		return region;
	}
	public void setRegion(String region) {
		this.region = region;
	}
	public Double getMinCartTotal() {
		return minCartTotal;
	}
	public void setMinCartTotal(Double minCartTotal) {
		this.minCartTotal = minCartTotal;
	}
	public Double getMaxCartTotal() {
		return maxCartTotal;
	}
	public void setMaxCartTotal(Double maxCartTotal) {
		this.maxCartTotal = maxCartTotal;
	}
	public Double getShippingPrice() {
		return shippingPrice;
	}
	public void setShippingPrice(Double shippingPrice) {
		this.shippingPrice = shippingPrice;
	}
	public Boolean getActive() {
		return active;
	}
	public void setActive(Boolean active) {
		this.active = active;
	}
	public LocalDateTime getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}
	public LocalDateTime getUpdatedAt() {
		return updatedAt;
	}
	public void setUpdatedAt(LocalDateTime updatedAt) {
		this.updatedAt = updatedAt;
	}

   
}
