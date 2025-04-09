package com.cgpi.cgpi.controller;

import com.cgpi.cgpi.services.SelectedSubCategory2Service;
import com.cgpi.cgpi.services.SubcategoryService;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/selected-subcategory2")
public class SelectedSubCategory2Controller {

    @Autowired
    private SelectedSubCategory2Service selectedSubCategory2Service;

    @Autowired
    private SubcategoryService subcategoryService; // Inject SubcategoryService

    // Endpoint to retrieve the selected subcategory name
    @GetMapping
    public ResponseEntity<Map<String, Object>> getSelectedSubCategory() {
        Map<String, Object> response = new HashMap<>();
        response.put("subcategoryId", selectedSubCategory2Service.getSelectedSubCategoryId());
        response.put("subcategoryName", selectedSubCategory2Service.getSelectedSubCategoryName());
        response.put("subcategoryImage", selectedSubCategory2Service.getSelectedSubCategoryImage());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/details-with-subcategories")
    public ResponseEntity<Map<String, Object>> getSelectedSubCategoryWithSubcategoriesAndProducts() {
        Map<String, Object> response = new HashMap<>();
        Long subcategoryId = selectedSubCategory2Service.getSelectedSubCategoryId();
        response.put("subcategoryId", subcategoryId);
        response.put("subcategoryName", selectedSubCategory2Service.getSelectedSubCategoryName());
        response.put("subcategoryImage", selectedSubCategory2Service.getSelectedSubCategoryImage());
//        response.put("subcategories", subcategoryService.getSubcategoriesByCategoryId(subcategoryId));
        response.put("products", subcategoryService.getProductsBySubcategoryId(subcategoryId)); // Fetch products for subcategory
        return ResponseEntity.ok(response);
    }

    // Endpoint to update the selected subcategory
    @PostMapping("/update")
    public String updateSelectedSubCategory(@RequestParam(required = false) Long subcategoryId) {
        selectedSubCategory2Service.updateSelectedSubCategory(subcategoryId);  // Update the selected subcategory
        return "Selected subcategory updated successfully!";
    }
}
