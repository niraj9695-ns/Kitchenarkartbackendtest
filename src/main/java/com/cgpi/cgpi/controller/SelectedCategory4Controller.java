package com.cgpi.cgpi.controller;

import com.cgpi.cgpi.services.SelectedCategory4Service;
import com.cgpi.cgpi.services.SubcategoryService;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/selected-category4")
public class SelectedCategory4Controller {

    @Autowired
    private SelectedCategory4Service selectedCategory4Service;

    @Autowired
    private SubcategoryService subcategoryService;

    // Retrieve the selected category name
    @GetMapping
    public ResponseEntity<Map<String, Object>> getSelectedCategory() {
        Map<String, Object> response = new HashMap<>();
        response.put("categoryId", selectedCategory4Service.getSelectedCategoryId());
        response.put("categoryName", selectedCategory4Service.getSelectedCategoryName());
        response.put("categoryImage", selectedCategory4Service.getSelectedCategoryImage());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/details-with-subcategories")
    public ResponseEntity<Map<String, Object>> getSelectedCategoryWithSubcategories() {
        Map<String, Object> response = new HashMap<>();
        Long categoryId = selectedCategory4Service.getSelectedCategoryId();
        response.put("categoryId", categoryId);
        response.put("categoryName", selectedCategory4Service.getSelectedCategoryName());
        response.put("categoryImage", selectedCategory4Service.getSelectedCategoryImage());
        response.put("subcategories", subcategoryService.getSubcategoriesByCategoryId(categoryId));
        return ResponseEntity.ok(response);
    }

    // Update the selected category
    @PostMapping("/update")
    public String updateSelectedCategory(@RequestParam(required = false) Long categoryId) {
        selectedCategory4Service.updateSelectedCategory(categoryId);
        return "Selected category updated successfully!";
    }
}
