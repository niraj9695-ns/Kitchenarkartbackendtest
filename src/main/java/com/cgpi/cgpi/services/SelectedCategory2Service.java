package com.cgpi.cgpi.services;

import com.cgpi.cgpi.entity.Category;
import com.cgpi.cgpi.entity.SelectedCategory2;
import com.cgpi.cgpi.repository.CategoryRepository;
import com.cgpi.cgpi.repository.SelectedCategory2Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SelectedCategory2Service {

    @Autowired
    private SelectedCategory2Repository selectedCategory2Repository;

    @Autowired
    private CategoryRepository categoryRepository;

    // Fetch the name of the selected category
    public String getSelectedCategoryName() {
        SelectedCategory2 selectedCategory2 = selectedCategory2Repository.findById(1L).orElse(null);

        if (selectedCategory2 == null) {
            return "No category selected";
        }

        Long selectedCategoryId = selectedCategory2.getCategoryId();

        return categoryRepository.findById(selectedCategoryId)
                .map(Category::getName)
                .orElse("Selected category not found");
    }

    // Fetch the image of the selected category
    public byte[] getSelectedCategoryImage() {
        SelectedCategory2 selectedCategory2 = selectedCategory2Repository.findById(1L).orElse(null);

        if (selectedCategory2 == null) {
            return null; // Return null if no category is selected
        }

        Long selectedCategoryId = selectedCategory2.getCategoryId();

        return categoryRepository.findById(selectedCategoryId)
                .map(Category::getImage) // Retrieve the image field
                .orElse(null); // Return null if the category is not found
    }

    // Update the selected category ID (Only updates the existing one)
    public void updateSelectedCategory(Long categoryId) {
        SelectedCategory2 existingSelectedCategory2 = selectedCategory2Repository.findById(1L).orElse(null);

        if (existingSelectedCategory2 != null) {
            existingSelectedCategory2.setCategoryId(categoryId);
            selectedCategory2Repository.save(existingSelectedCategory2);
        } else {
            SelectedCategory2 selectedCategory2 = new SelectedCategory2();
            selectedCategory2.setCategoryId(categoryId);
            selectedCategory2Repository.save(selectedCategory2);
        }
    }
    

    // Fetch the ID of the selected category
    public Long getSelectedCategoryId() {
        SelectedCategory2 selectedCategory2 = selectedCategory2Repository.findById(1L).orElse(null);

        if (selectedCategory2 == null) {
            return null;
        }

        return selectedCategory2.getCategoryId();
    }
}
