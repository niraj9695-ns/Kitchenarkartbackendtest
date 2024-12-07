package com.cgpi.cgpi.controller;

import com.cgpi.cgpi.services.SelectedCategoryService;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/selected-category")
public class SelectedCategoryController {

    @Autowired
    private SelectedCategoryService selectedCategoryService;

    // Endpoint to retrieve the selected category name
    @GetMapping
    public ResponseEntity<Map<String, Object>> getSelectedCategory() {
        Map<String, Object> response = new HashMap<>();
        response.put("categoryId", selectedCategoryService.getSelectedCategoryId());
        response.put("categoryName", selectedCategoryService.getSelectedCategoryName());
        response.put("categoryImage", selectedCategoryService.getSelectedCategoryImage());
        return ResponseEntity.ok(response);
    }


    // Endpoint to update the selected category
    @PostMapping("/update")
    public String updateSelectedCategory(@RequestParam Long categoryId) {
        selectedCategoryService.updateSelectedCategory(categoryId);  // Update the selected category
        return "Selected category updated successfully!";
    }
}
