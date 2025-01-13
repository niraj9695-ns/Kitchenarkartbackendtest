package com.cgpi.cgpi.services;

import com.cgpi.cgpi.entity.Category;
import com.cgpi.cgpi.entity.SelectedCategory2;
import com.cgpi.cgpi.entity.Subcategory;
import com.cgpi.cgpi.repository.SubcategoryRepository;
import com.cgpi.cgpi.repository.SelectedCategory2Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SelectedCategory2Service {

    @Autowired
    private SelectedCategory2Repository selectedCategory2Repository;

    @Autowired
    private SubcategoryRepository subcategoryRepository;

    // Fetch the name of the selected category
    public String getSelectedCategoryName() {
        SelectedCategory2 selectedCategory = selectedCategory2Repository.findById(1L).orElse(null);

        if (selectedCategory == null) {
            return "No category selected";
        }

        Long selectedSubcategoryId = selectedCategory.getSubcategoryId();

        return subcategoryRepository.findById(selectedSubcategoryId)
                .map(Subcategory::getName)
                .orElse("Selected category not found");
    }

    // Fetch the image of the selected category
    public byte[] getSelectedCategoryImage() {
        SelectedCategory2 selectedCategory = selectedCategory2Repository.findById(1L).orElse(null);

        if (selectedCategory == null) {
            return null; // Return null if no category is selected
        }

        Long selectedCategoryId = selectedCategory.getSubcategoryId();

        return subcategoryRepository.findById(selectedCategoryId)
                .map(Subcategory::getImage) // Retrieve the image field
                .orElse(null); // Return null if the category is not found
    }

    // Update the selected category ID (Only updates the existing one)
    public void updateSelectedCategory(Long subcategoryId) {
        SelectedCategory2 existingSelectedCategory = selectedCategory2Repository.findById(1L).orElse(null);

        if (existingSelectedCategory != null) {
            existingSelectedCategory.setSubcategoryId(subcategoryId);
            selectedCategory2Repository.save(existingSelectedCategory);
        } else {
            SelectedCategory2 selectedCategory = new SelectedCategory2();
            selectedCategory.setSubcategoryId(subcategoryId);
            selectedCategory2Repository.save(selectedCategory);
        }
    }

    // Fetch the ID of the selected category
    public Long getSelectedCategoryId() {
        SelectedCategory2 selectedCategory = selectedCategory2Repository.findById(1L).orElse(null);

        if (selectedCategory == null) {
            return null;
        }

        return selectedCategory.getSubcategoryId();
    }
}
