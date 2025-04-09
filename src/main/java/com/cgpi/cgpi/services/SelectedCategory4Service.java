package com.cgpi.cgpi.services;

import com.cgpi.cgpi.entity.Category;
import com.cgpi.cgpi.entity.SelectedCategory4;
import com.cgpi.cgpi.repository.CategoryRepository;
import com.cgpi.cgpi.repository.SelectedCategory4Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SelectedCategory4Service {

    @Autowired
    private SelectedCategory4Repository selectedCategory4Repository;

    @Autowired
    private CategoryRepository categoryRepository;

    // Fetch the name of the selected category
    public String getSelectedCategoryName() {
        SelectedCategory4 selectedCategory = selectedCategory4Repository.findById(1L).orElse(null);

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
        SelectedCategory4 selectedCategory = selectedCategory4Repository.findById(1L).orElse(null);

        if (selectedCategory == null) {
            return null; // Return null if no category is selected
        }

        Long selectedCategoryId = selectedCategory.getCategoryId();

        return categoryRepository.findById(selectedCategoryId)
                .map(Category::getImage) // Retrieve the image field
                .orElse(null); // Return null if the category is not found
    }

    // Update the selected category ID
    public void updateSelectedCategory(Long categoryId) {
        SelectedCategory4 existingSelectedCategory = selectedCategory4Repository.findById(1L).orElse(null);

        if (existingSelectedCategory != null) {
            existingSelectedCategory.setCategoryId(categoryId);
            selectedCategory4Repository.save(existingSelectedCategory);
        } else {
            SelectedCategory4 selectedCategory = new SelectedCategory4();
            selectedCategory.setCategoryId(categoryId);
            selectedCategory4Repository.save(selectedCategory);
        }
    }

    // Fetch the ID of the selected category
    public Long getSelectedCategoryId() {
        SelectedCategory4 selectedCategory = selectedCategory4Repository.findById(1L).orElse(null);

        if (selectedCategory == null) {
            return null;
        }

        return selectedCategory.getCategoryId();
    }
}
