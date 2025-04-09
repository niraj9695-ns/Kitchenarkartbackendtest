//package com.cgpi.cgpi.services;
//
//import com.cgpi.cgpi.entity.Category;
//import com.cgpi.cgpi.entity.SelectedCategory2;
////import com.cgpi.cgpi.entity.SelectedSubCategory;
//import com.cgpi.cgpi.entity.Subcategory;
//import com.cgpi.cgpi.repository.SubcategoryRepository;
//import com.cgpi.cgpi.repository.SelectedCategory2Repository;
////import com.cgpi.cgpi.repository.SelectedCategoryRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//@Service
//public class SelectedCategory2Service {
//
//    @Autowired
//    private SelectedCategory2Repository selectedCategory2Repository;
//
//    @Autowired
//    private SubcategoryRepository subcategoryRepository;
//
//    // Fetch the name of the selected category
//    public String getSelectedCategoryName() {
//    	SelectedCategory2 selectedCategory = selectedCategory2Repository.findById(1L).orElse(null);
//
//        if (selectedCategory == null) {
//            return "No category selected";
//        }
//
//        Long selectedSubcategoryId = selectedCategory.getSubcategoryId();
//
//        return subcategoryRepository.findById(selectedSubcategoryId)
//                .map(Subcategory::getName)
//                .orElse("Selected category not found");
//    }
//
//    // Fetch the image of the selected category
//    public byte[] getSelectedCategoryImage() {
//        SelectedCategory2 selectedCategory = selectedCategory2Repository.findById(1L).orElse(null);
//
//        if (selectedCategory == null) {
//            return null; // Return null if no category is selected
//        }
//
//        Long selectedCategoryId = selectedCategory.getSubcategoryId();
//
//        return subcategoryRepository.findById(selectedCategoryId)
//                .map(Subcategory::getImage) // Retrieve the image field
//                .orElse(null); // Return null if the category is not found
//    }
//
//    // Update the selected category ID (Only updates the existing one)
//    public void updateSelectedCategory(Long subcategoryId) {
//        SelectedCategory2 existingSelectedCategory = selectedCategory2Repository.findById(1L).orElse(null);
//
//        if (existingSelectedCategory != null) {
//            existingSelectedCategory.setSubcategoryId(subcategoryId);
//            selectedCategory2Repository.save(existingSelectedCategory);
//        } else {
//            SelectedCategory2 selectedCategory = new SelectedCategory2();
//            selectedCategory.setSubcategoryId(subcategoryId);
//            selectedCategory2Repository.save(selectedCategory);
//        }
//    }
//
//    // Fetch the ID of the selected category
//    public Long getSelectedCategoryId() {
//        SelectedCategory2 selectedCategory = selectedCategory2Repository.findById(1L).orElse(null);
//
//        if (selectedCategory == null) {
//            return null;
//        }
//
//        return selectedCategory.getSubcategoryId();
//    }
//}
package com.cgpi.cgpi.services;

import com.cgpi.cgpi.entity.SelectedSubCategory;
import com.cgpi.cgpi.entity.Subcategory;
import com.cgpi.cgpi.repository.SelectedSubCategoryRepository;
import com.cgpi.cgpi.repository.SubcategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SelectedSubCategoryService {

    @Autowired
    private SelectedSubCategoryRepository selectedSubCategoryRepository;

    @Autowired
    private SubcategoryRepository subcategoryRepository;

    // Fetch the name of the selected subcategory
    public String getSelectedSubCategoryName() {
        SelectedSubCategory selectedSubCategory = selectedSubCategoryRepository.findById(1L).orElse(null);

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
        SelectedSubCategory selectedSubCategory = selectedSubCategoryRepository.findById(1L).orElse(null);

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
        SelectedSubCategory existingSelectedSubCategory = selectedSubCategoryRepository.findById(1L).orElse(null);

        if (existingSelectedSubCategory != null) {
            existingSelectedSubCategory.setCategoryId(subcategoryId);
            selectedSubCategoryRepository.save(existingSelectedSubCategory);
        } else {
            SelectedSubCategory selectedSubCategory = new SelectedSubCategory();
            selectedSubCategory.setCategoryId(subcategoryId);
            selectedSubCategoryRepository.save(selectedSubCategory);
        }
    }

    // Fetch the ID of the selected subcategory
    public Long getSelectedSubCategoryId() {
        SelectedSubCategory selectedSubCategory = selectedSubCategoryRepository.findById(1L).orElse(null);

        if (selectedSubCategory == null) {
            return null;
        }

        return selectedSubCategory.getCategoryId();
    }
}
