package com.cgpi.cgpi.services;

import com.cgpi.cgpi.entity.SelectedSubCategory3;
import com.cgpi.cgpi.entity.Subcategory;
import com.cgpi.cgpi.repository.SelectedSubCategory3Repository;
import com.cgpi.cgpi.repository.SubcategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SelectedSubCategory3Service {

    @Autowired
    private SelectedSubCategory3Repository selectedSubCategory3Repository;

    @Autowired
    private SubcategoryRepository subcategoryRepository;

    // Fetch the name of the selected subcategory
    public String getSelectedSubCategoryName() {
        SelectedSubCategory3 selectedSubCategory = selectedSubCategory3Repository.findById(1L).orElse(null);

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
        SelectedSubCategory3 selectedSubCategory = selectedSubCategory3Repository.findById(1L).orElse(null);

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
        SelectedSubCategory3 existingSelectedSubCategory = selectedSubCategory3Repository.findById(1L).orElse(null);

        if (existingSelectedSubCategory != null) {
            existingSelectedSubCategory.setCategoryId(subcategoryId);
            selectedSubCategory3Repository.save(existingSelectedSubCategory);
        } else {
            SelectedSubCategory3 selectedSubCategory = new SelectedSubCategory3();
            selectedSubCategory.setCategoryId(subcategoryId);
            selectedSubCategory3Repository.save(selectedSubCategory);
        }
    }

    // Fetch the ID of the selected subcategory
    public Long getSelectedSubCategoryId() {
        SelectedSubCategory3 selectedSubCategory = selectedSubCategory3Repository.findById(1L).orElse(null);

        if (selectedSubCategory == null) {
            return null;
        }

        return selectedSubCategory.getCategoryId();
    }
}
