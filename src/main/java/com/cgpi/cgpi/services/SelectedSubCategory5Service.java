package com.cgpi.cgpi.services;

import com.cgpi.cgpi.entity.SelectedSubCategory5;
import com.cgpi.cgpi.entity.Subcategory;
import com.cgpi.cgpi.repository.SelectedSubCategory5Repository;
import com.cgpi.cgpi.repository.SubcategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SelectedSubCategory5Service {

    @Autowired
    private SelectedSubCategory5Repository selectedSubCategory5Repository;

    @Autowired
    private SubcategoryRepository subcategoryRepository;

    // Fetch the name of the selected subcategory
    public String getSelectedSubCategoryName() {
        SelectedSubCategory5 selectedSubCategory = selectedSubCategory5Repository.findById(1L).orElse(null);

        if (selectedSubCategory == null) {
            return "No subcategory selected";
        }

        Long selectedSubcategoryId = selectedSubCategory.getCategoryId();

        return subcategoryRepository.findById(selectedSubcategoryId)
                .map(Subcategory::getName)
                .orElse("Selected subcategory not found");
    }

    // Fetch the image of the selected subcategory
    public byte[] getSelectedSubCategoryImage() {
        SelectedSubCategory5 selectedSubCategory = selectedSubCategory5Repository.findById(1L).orElse(null);

        if (selectedSubCategory == null) {
            return null; // Return null if no subcategory is selected
        }

        Long selectedSubcategoryId = selectedSubCategory.getCategoryId();

        return subcategoryRepository.findById(selectedSubcategoryId)
                .map(Subcategory::getImage)
                .orElse(null); // Return null if the subcategory is not found
    }

    // Update the selected subcategory ID (Only updates the existing one)
    public void updateSelectedSubCategory(Long subcategoryId) {
        SelectedSubCategory5 existingSelectedSubCategory = selectedSubCategory5Repository.findById(1L).orElse(null);

        if (existingSelectedSubCategory != null) {
            existingSelectedSubCategory.setCategoryId(subcategoryId);
            selectedSubCategory5Repository.save(existingSelectedSubCategory);
        } else {
            SelectedSubCategory5 selectedSubCategory = new SelectedSubCategory5();
            selectedSubCategory.setCategoryId(subcategoryId);
            selectedSubCategory5Repository.save(selectedSubCategory);
        }
    }

    // Fetch the ID of the selected subcategory
    public Long getSelectedSubCategoryId() {
        SelectedSubCategory5 selectedSubCategory = selectedSubCategory5Repository.findById(1L).orElse(null);

        if (selectedSubCategory == null) {
            return null;
        }

        return selectedSubCategory.getCategoryId();
    }
}
