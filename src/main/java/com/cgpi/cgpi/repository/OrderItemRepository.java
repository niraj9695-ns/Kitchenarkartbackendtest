package com.cgpi.cgpi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cgpi.cgpi.entity.OrderItem;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
}
