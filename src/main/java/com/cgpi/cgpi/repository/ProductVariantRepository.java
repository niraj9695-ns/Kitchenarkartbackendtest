package com.cgpi.cgpi.repository;

import com.cgpi.cgpi.entity.Product;
import com.cgpi.cgpi.entity.ProductVariant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductVariantRepository extends JpaRepository<ProductVariant, Long> {
    List<ProductVariant> findByProductId(Long productId);
    List<ProductVariant> findByProduct(Product product);
   
}
