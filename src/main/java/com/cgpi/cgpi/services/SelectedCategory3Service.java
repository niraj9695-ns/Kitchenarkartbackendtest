package com.cgpi.cgpi.services;

import com.cgpi.cgpi.entity.Category;
import com.cgpi.cgpi.entity.SelectedCategory3;
import com.cgpi.cgpi.repository.CategoryRepository;
import com.cgpi.cgpi.repository.SelectedCategory3Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SelectedCategory3Service {

    @Autowired
    private SelectedCategory3Repository selectedCategory3Repository;

    @Autowired
    private CategoryRepository categoryRepository;

    public String getSelectedCategoryName() {
        SelectedCategory3 selectedCategory = selectedCategory3Repository.findById(1L).orElse(null);
        if (selectedCategory == null) {
            return "No category selected";
        }
        Long categoryId = selectedCategory.getCategoryId();
        return categoryRepository.findById(categoryId).map(Category::getName).orElse("Category not found");
    }

    public byte[] getSelectedCategoryImage() {
        SelectedCategory3 selectedCategory = selectedCategory3Repository.findById(1L).orElse(null);
        if (selectedCategory == null) {
            return null;
        }
        Long categoryId = selectedCategory.getCategoryId();
        return categoryRepository.findById(categoryId).map(Category::getImage).orElse(null);
    }

    public void updateSelectedCategory(Long categoryId) {
        SelectedCategory3 existingCategory = selectedCategory3Repository.findById(1L).orElse(null);
        if (existingCategory != null) {
            existingCategory.setCategoryId(categoryId);
            selectedCategory3Repository.save(existingCategory);
        } else {
            SelectedCategory3 selectedCategory = new SelectedCategory3();
            selectedCategory.setCategoryId(categoryId);
            selectedCategory3Repository.save(selectedCategory);
        }
    }

    public Long getSelectedCategoryId() {
        SelectedCategory3 selectedCategory = selectedCategory3Repository.findById(1L).orElse(null);
        return (selectedCategory != null) ? selectedCategory.getCategoryId() : null;
    }
}
