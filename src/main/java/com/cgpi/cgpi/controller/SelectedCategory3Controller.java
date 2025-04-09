package com.cgpi.cgpi.controller;

import com.cgpi.cgpi.services.SelectedCategory3Service;
import com.cgpi.cgpi.services.SubcategoryService;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/selected-category3")
public class SelectedCategory3Controller {

    @Autowired
    private SelectedCategory3Service selectedCategory3Service;

    @Autowired
    private SubcategoryService subcategoryService;

    @GetMapping
    public ResponseEntity<Map<String, Object>> getSelectedCategory() {
        Map<String, Object> response = new HashMap<>();
        response.put("categoryId", selectedCategory3Service.getSelectedCategoryId());
        response.put("categoryName", selectedCategory3Service.getSelectedCategoryName());
        response.put("categoryImage", selectedCategory3Service.getSelectedCategoryImage());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/details-with-subcategories")
    public ResponseEntity<Map<String, Object>> getSelectedCategoryWithSubcategories() {
        Map<String, Object> response = new HashMap<>();
        Long categoryId = selectedCategory3Service.getSelectedCategoryId();
        response.put("categoryId", categoryId);
        response.put("categoryName", selectedCategory3Service.getSelectedCategoryName());
        response.put("categoryImage", selectedCategory3Service.getSelectedCategoryImage());
        response.put("subcategories", subcategoryService.getSubcategoriesByCategoryId(categoryId));
        return ResponseEntity.ok(response);
    }

    @PostMapping("/update")
    public String updateSelectedCategory(@RequestParam(required = false) Long categoryId){
        selectedCategory3Service.updateSelectedCategory(categoryId);
        return "Selected category updated successfully!";
    }
}
