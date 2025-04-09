package com.cgpi.cgpi.controller;

import com.cgpi.cgpi.services.SelectedCategory5Service;
import com.cgpi.cgpi.services.SubcategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/selected-category5")
public class SelectedCategory5Controller {

    @Autowired
    private SelectedCategory5Service selectedCategory5Service;

    @Autowired
    private SubcategoryService subcategoryService;

    // Retrieve the selected category details
    @GetMapping
    public ResponseEntity<Map<String, Object>> getSelectedCategory() {
        Map<String, Object> response = new HashMap<>();
        response.put("categoryId", selectedCategory5Service.getSelectedCategoryId());
        response.put("categoryName", selectedCategory5Service.getSelectedCategoryName());
        response.put("categoryImage", selectedCategory5Service.getSelectedCategoryImage());
        return ResponseEntity.ok(response);
    }

    // Retrieve the selected category along with its subcategories
    @GetMapping("/details-with-subcategories")
    public ResponseEntity<Map<String, Object>> getSelectedCategoryWithSubcategories() {
        Map<String, Object> response = new HashMap<>();
        Long categoryId = selectedCategory5Service.getSelectedCategoryId();
        response.put("categoryId", categoryId);
        response.put("categoryName", selectedCategory5Service.getSelectedCategoryName());
        response.put("categoryImage", selectedCategory5Service.getSelectedCategoryImage());
        response.put("subcategories", subcategoryService.getSubcategoriesByCategoryId(categoryId));
        return ResponseEntity.ok(response);
    }

    // Update the selected category
    @PostMapping("/update")
    public String updateSelectedCategory(@RequestParam(required = false) Long categoryId){
        selectedCategory5Service.updateSelectedCategory(categoryId);
        return "Selected category updated successfully!";
    }
}
