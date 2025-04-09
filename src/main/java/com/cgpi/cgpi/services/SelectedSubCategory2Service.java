package com.cgpi.cgpi.services;

import com.cgpi.cgpi.entity.SelectedSubCategory2;
import com.cgpi.cgpi.entity.Subcategory;
import com.cgpi.cgpi.repository.SelectedSubCategory2Repository;
import com.cgpi.cgpi.repository.SubcategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SelectedSubCategory2Service {

    @Autowired
    private SelectedSubCategory2Repository selectedSubCategory2Repository;

    @Autowired
    private SubcategoryRepository subcategoryRepository;

    // Fetch the name of the selected subcategory
    public String getSelectedSubCategoryName() {
        SelectedSubCategory2 selectedSubCategory = selectedSubCategory2Repository.findById(1L).orElse(null);

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
        SelectedSubCategory2 selectedSubCategory = selectedSubCategory2Repository.findById(1L).orElse(null);

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
        SelectedSubCategory2 existingSelectedSubCategory = selectedSubCategory2Repository.findById(1L).orElse(null);

        if (existingSelectedSubCategory != null) {
            existingSelectedSubCategory.setCategoryId(subcategoryId);
            selectedSubCategory2Repository.save(existingSelectedSubCategory);
        } else {
            SelectedSubCategory2 selectedSubCategory = new SelectedSubCategory2();
            selectedSubCategory.setCategoryId(subcategoryId);
            selectedSubCategory2Repository.save(selectedSubCategory);
        }
    }

    // Fetch the ID of the selected subcategory
    public Long getSelectedSubCategoryId() {
        SelectedSubCategory2 selectedSubCategory = selectedSubCategory2Repository.findById(1L).orElse(null);

        if (selectedSubCategory == null) {
            return null;
        }

        return selectedSubCategory.getCategoryId();
    }
}
