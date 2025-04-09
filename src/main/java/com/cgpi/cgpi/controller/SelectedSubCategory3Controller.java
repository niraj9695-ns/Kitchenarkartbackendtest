package com.cgpi.cgpi.controller;

import com.cgpi.cgpi.services.SelectedSubCategory3Service;
import com.cgpi.cgpi.services.SubcategoryService;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/selected-subcategory3")
public class SelectedSubCategory3Controller {

    @Autowired
    private SelectedSubCategory3Service selectedSubCategory3Service;

    @Autowired
    private SubcategoryService subcategoryService; // Inject SubcategoryService

    // Endpoint to retrieve the selected subcategory name
    @GetMapping
    public ResponseEntity<Map<String, Object>> getSelectedSubCategory() {
        Map<String, Object> response = new HashMap<>();
        response.put("subcategoryId", selectedSubCategory3Service.getSelectedSubCategoryId());
        response.put("subcategoryName", selectedSubCategory3Service.getSelectedSubCategoryName());
        response.put("subcategoryImage", selectedSubCategory3Service.getSelectedSubCategoryImage());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/details-with-subcategories")
    public ResponseEntity<Map<String, Object>> getSelectedSubCategoryWithSubcategoriesAndProducts() {
        Map<String, Object> response = new HashMap<>();
        Long subcategoryId = selectedSubCategory3Service.getSelectedSubCategoryId();
        response.put("subcategoryId", subcategoryId);
        response.put("subcategoryName", selectedSubCategory3Service.getSelectedSubCategoryName());
        response.put("subcategoryImage", selectedSubCategory3Service.getSelectedSubCategoryImage());
//        response.put("subcategories", subcategoryService.getSubcategoriesByCategoryId(subcategoryId));
        response.put("products", subcategoryService.getProductsBySubcategoryId(subcategoryId)); // Fetch products for subcategory
        return ResponseEntity.ok(response);
    }

    // Endpoint to update the selected subcategory
    @PostMapping("/update")
    public String updateSelectedSubCategory(@RequestParam(required = false) Long subcategoryId) {
        selectedSubCategory3Service.updateSelectedSubCategory(subcategoryId);  // Update the selected subcategory
        return "Selected subcategory updated successfully!";
    }
}
