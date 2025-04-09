package com.cgpi.cgpi.services;

import com.cgpi.cgpi.entity.Category;
import com.cgpi.cgpi.entity.SelectedCategory5;
import com.cgpi.cgpi.repository.CategoryRepository;
import com.cgpi.cgpi.repository.SelectedCategory5Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class SelectedCategory5Service {

    @Autowired
    private SelectedCategory5Repository selectedCategory5Repository;

    @Autowired
    private CategoryRepository categoryRepository;

    // Fetch the ID of the selected category
    public Long getSelectedCategoryId() {
        return selectedCategory5Repository.findById(1L)
                .map(SelectedCategory5::getCategoryId)
                .orElse(null);
    }

    // Fetch the name of the selected category
    public String getSelectedCategoryName() {
        return selectedCategory5Repository.findById(1L)
                .map(sc -> categoryRepository.findById(sc.getCategoryId())
                        .map(Category::getName)
                        .orElse("Category not found"))
                .orElse("No category selected");
    }

    // Fetch the image of the selected category
    public byte[] getSelectedCategoryImage() {
        return selectedCategory5Repository.findById(1L)
                .map(sc -> categoryRepository.findById(sc.getCategoryId())
                        .map(Category::getImage)
                        .orElse(null))
                .orElse(null);
    }

    // Update the selected category
    public void updateSelectedCategory(Long categoryId) {
        SelectedCategory5 selectedCategory = selectedCategory5Repository.findById(1L)
                .orElse(new SelectedCategory5());
        selectedCategory.setCategoryId(categoryId);
        selectedCategory5Repository.save(selectedCategory);
    }
}
