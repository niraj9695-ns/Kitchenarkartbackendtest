package com.cgpi.cgpi.repository;



import com.cgpi.cgpi.entity.Cart;
import com.cgpi.cgpi.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {

    /**
     * Find a cart by the associated user.
     * 
     * @param user The user whose cart needs to be retrieved.
     * @return An Optional containing the cart if found, otherwise empty.
     */
    Optional<Cart> findByUser(User user);
}