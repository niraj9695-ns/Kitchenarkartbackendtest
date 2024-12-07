package com.cgpi.cgpi.services;

import com.cgpi.cgpi.entity.Category;
import com.cgpi.cgpi.entity.SelectedCategory;
import com.cgpi.cgpi.repository.CategoryRepository;
import com.cgpi.cgpi.repository.SelectedCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SelectedCategoryService {

    @Autowired
    private SelectedCategoryRepository selectedCategoryRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    // Fetch the name of the selected category
    public String getSelectedCategoryName() {
        SelectedCategory selectedCategory = selectedCategoryRepository.findById(1L).orElse(null);

        if (selectedCategory == null) {
            return "No category selected";
        }

        Long selectedCategoryId = selectedCategory.getCategoryId();

        return categoryRepository.findById(selectedCategoryId)
                .map(Category::getName)
                .orElse("Selected category not found");
    }

    // Fetch the image of the selected category
    public byte[] getSelectedCategoryImage() {
        SelectedCategory selectedCategory = selectedCategoryRepository.findById(1L).orElse(null);

        if (selectedCategory == null) {
            return null; // Return null if no category is selected
        }

        Long selectedCategoryId = selectedCategory.getCategoryId();

        return categoryRepository.findById(selectedCategoryId)
                .map(Category::getImage) // Retrieve the image field
                .orElse(null); // Return null if the category is not found
    }

    // Update the selected category ID (Only updates the existing one)
    public void updateSelectedCategory(Long categoryId) {
        SelectedCategory existingSelectedCategory = selectedCategoryRepository.findById(1L).orElse(null);

        if (existingSelectedCategory != null) {
            existingSelectedCategory.setCategoryId(categoryId);
            selectedCategoryRepository.save(existingSelectedCategory);
        } else {
            SelectedCategory selectedCategory = new SelectedCategory();
            selectedCategory.setCategoryId(categoryId);
            selectedCategoryRepository.save(selectedCategory);
        }
    }

    // Fetch the ID of the selected category
    public Long getSelectedCategoryId() {
        SelectedCategory selectedCategory = selectedCategoryRepository.findById(1L).orElse(null);

        if (selectedCategory == null) {
            return null;
        }

        return selectedCategory.getCategoryId();
    }
}
