package com.cgpi.cgpi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cgpi.cgpi.entity.User;
import com.cgpi.cgpi.entity.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByUser(User user);
}
