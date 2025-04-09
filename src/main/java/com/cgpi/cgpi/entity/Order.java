package com.cgpi.cgpi.entity;

import jakarta.persistence.Entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.cgpi.cgpi.entity.User;

import jakarta.persistence.*;

@Entity
@Cacheable
@Table(name = "`order`")
public class Order {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "user_id", nullable = false)
	private User user;

	private Double totalAmount;
	private Double taxAmount;
	private Double shippingCharges;
	private Double finalAmount;
	private String address;
	private String state;
	private String country;
	private String pincode;
	private String contactNumber;

	public static final String STATUS_PENDING = "Pending";
	public static final String STATUS_PLACED = "Placed";
	public static final String STATUS_PROCESSING = "Processing";
	public static final String STATUS_SHIPPED = "Shipped";
	public static final String STATUS_DELIVERED = "Delivered";
	public static final String STATUS_FAILED = "Failed";
	public static final String STATUS_CANCELED = "Canceled";
	public static final String STATUS_REFUNDED = "Refunded";

	@OneToMany(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JsonManagedReference
	private List<OrderItem> items = new ArrayList<>();

	private String status; // e.g., 'Placed', 'Shipped', 'Delivered'

	private LocalDateTime orderDate;

	// Getters and Setters
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Double getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(Double totalAmount) {
		this.totalAmount = totalAmount;
	}

	public Double getTaxAmount() {
		return taxAmount;
	}

	public void setTaxAmount(Double taxAmount) {
		this.taxAmount = taxAmount;
	}

	public Double getShippingCharges() {
		return shippingCharges;
	}

	public void setShippingCharges(Double shippingCharges) {
		this.shippingCharges = shippingCharges;
	}

	public Double getFinalAmount() {
		return finalAmount;
	}

	public void setFinalAmount(Double finalAmount) {
		this.finalAmount = finalAmount;
	}

	public List<OrderItem> getItems() {
		return items;
	}

	public void setItems(List<OrderItem> items) {
		this.items = items;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public LocalDateTime getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(LocalDateTime orderDate) {
		this.orderDate = orderDate;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getPincode() {
		return pincode;
	}

	public void setPincode(String pincode) {
		this.pincode = pincode;
	}

	public String getContactNumber() {
		return contactNumber;
	}

	public void setContactNumber(String contactNumber) {
		this.contactNumber = contactNumber;
	}
}

//==============order saving update ===================


