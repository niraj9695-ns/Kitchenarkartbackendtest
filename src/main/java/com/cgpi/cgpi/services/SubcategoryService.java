package com.cgpi.cgpi.services;

import com.cgpi.cgpi.entity.Product;
import com.cgpi.cgpi.entity.Subcategory;
import com.cgpi.cgpi.repository.ProductRepository;
import com.cgpi.cgpi.repository.SubcategoryRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class SubcategoryService {
	@Autowired
    private ProductRepository productRepository;

    private final SubcategoryRepository subcategoryRepository;

    public SubcategoryService(SubcategoryRepository subcategoryRepository) {
        this.subcategoryRepository = subcategoryRepository;
    }

    public List<Subcategory> getAllSubcategories() {
        return subcategoryRepository.findAll();
    }

    public Subcategory saveSubcategory(Subcategory subcategory) {
        return subcategoryRepository.save(subcategory);
    }

    public Subcategory getSubcategoryById(Long id) {
        return subcategoryRepository.findById(id).orElseThrow(() -> new RuntimeException("Subcategory not found"));
    }

    public void deleteSubcategory(Long id) {
        subcategoryRepository.deleteById(id);
    }
    public List<Subcategory> getSubcategoriesByCategoryId(Long categoryId) {
        return subcategoryRepository.findByCategoryId(categoryId);
    }
    public List<Product> getProductsBySubcategoryId(Long subcategoryId) {
        return productRepository.findBySubcategoryId(subcategoryId); // Assuming JPA repository method
    }


}
