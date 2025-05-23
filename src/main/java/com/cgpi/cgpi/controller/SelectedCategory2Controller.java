package com.cgpi.cgpi.controller;

import com.cgpi.cgpi.services.SelectedCategory2Service;
import com.cgpi.cgpi.services.SubcategoryService;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/selected-category2")
public class SelectedCategory2Controller {

    @Autowired
    private SelectedCategory2Service selectedCategory2Service;

    @Autowired
    private SubcategoryService subcategoryService; // Inject SubcategoryService

    // Endpoint to retrieve the selected category name
    @GetMapping
    public ResponseEntity<Map<String, Object>> getSelectedCategory() {
        Map<String, Object> response = new HashMap<>();
        response.put("categoryId", selectedCategory2Service.getSelectedCategoryId());
        response.put("categoryName", selectedCategory2Service.getSelectedCategoryName());
        response.put("categoryImage", selectedCategory2Service.getSelectedCategoryImage());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/details-with-subcategories")
    public ResponseEntity<Map<String, Object>> getSelectedCategoryWithSubcategories() {
        Map<String, Object> response = new HashMap<>();
        Long categoryId = selectedCategory2Service.getSelectedCategoryId();
        response.put("categoryId", categoryId);
        response.put("categoryName", selectedCategory2Service.getSelectedCategoryName());
        response.put("categoryImage", selectedCategory2Service.getSelectedCategoryImage());
        response.put("subcategories", subcategoryService.getSubcategoriesByCategoryId(categoryId));
        return ResponseEntity.ok(response);
    }

    // Endpoint to update the selected category
    @PostMapping("/update")
    public String updateSelectedCategory(@RequestParam(required = false) Long categoryId) {
        selectedCategory2Service.updateSelectedCategory(categoryId);  // Update the selected category
        return "Selected category updated successfully!";
    }
}
