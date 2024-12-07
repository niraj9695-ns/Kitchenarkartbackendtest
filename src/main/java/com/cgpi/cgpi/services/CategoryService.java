package com.cgpi.cgpi.services;

import com.cgpi.cgpi.entity.Category;
import com.cgpi.cgpi.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    public Category saveCategory(Category category) {
        return categoryRepository.save(category);
    }

    public List<Category> getTopCategories() {
        return categoryRepository.findByTopCategoryTrue(); // Query to find top categories
    }

    public Category getCategoryById(Long id) {
        return categoryRepository.findById(id).orElseThrow(() -> new RuntimeException("Category not found"));
    }

    public void deleteCategory(Long id) {
        categoryRepository.deleteById(id);
    }

    public Category updateCategory(Long id, String name, byte[] image, Boolean topCategory) throws IOException {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found"));

        // Update name if provided
        if (name != null && !name.isEmpty()) {
            category.setName(name);
        }

        // Update image if provided
        if (image != null && image.length > 0) {
            category.setImage(image);
        }

        // Update topCategory status if provided
        if (topCategory != null) {
            category.setTopCategory(topCategory);
        }

        return categoryRepository.save(category);
    }
}
