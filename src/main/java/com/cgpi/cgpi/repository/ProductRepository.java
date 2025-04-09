
package com.cgpi.cgpi.repository;

import com.cgpi.cgpi.entity.Product;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
	List<Product> findBySubsubcategoryId(Long subsubcategoryId);
	List<Product> findByCategoryId(Long categoryId);
    List<Product> findBySubcategoryId(Long subcategoryId);
    List<Product> findByIsNewArrival(boolean isNewArrival);
    List<Product> findByNameContainingIgnoreCaseOrCategory_NameContainingIgnoreCase(String name, String category);
    List<Product> findByIsMostSelling(boolean isMostSelling);

}
