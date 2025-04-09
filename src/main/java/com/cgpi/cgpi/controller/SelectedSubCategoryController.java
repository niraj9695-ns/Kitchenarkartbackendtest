//package com.cgpi.cgpi.controller;
//
//import com.cgpi.cgpi.services.SelectedCategory2Service;
//import com.cgpi.cgpi.services.SubcategoryService;
//
//import java.util.HashMap;
//import java.util.Map;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//@RestController
//@RequestMapping("/api/selected-category2")
//public class SelectedCategory2Controller {
//
//    @Autowired
//    private SelectedCategory2Service selectedCategory2Service;
//
//    @Autowired
//    private SubcategoryService subcategoryService; // Inject SubcategoryService
//
//    // Endpoint to retrieve the selected category name
//    @GetMapping
//    public ResponseEntity<Map<String, Object>> getSelectedCategory() {
//        Map<String, Object> response = new HashMap<>();
//        response.put("subcategoryId", selectedCategory2Service.getSelectedCategoryId());
//        response.put("subcategoryName", selectedCategory2Service.getSelectedCategoryName());
//        response.put("subcategoryImage", selectedCategory2Service.getSelectedCategoryImage());
//        return ResponseEntity.ok(response);
//    }
//
//    @GetMapping("/details-with-subcategories")
//    public ResponseEntity<Map<String, Object>> getSelectedCategoryWithSubcategoriesAndProducts() {
//        Map<String, Object> response = new HashMap<>();
//        Long subcategoryId = selectedCategory2Service.getSelectedCategoryId();
//        response.put("subcategoryId", subcategoryId);
//        response.put("subcategoryName", selectedCategory2Service.getSelectedCategoryName());
//        response.put("subcategoryImage", selectedCategory2Service.getSelectedCategoryImage());
//        response.put("subcategories", subcategoryService.getSubcategoriesByCategoryId(subcategoryId));
//        response.put("products", subcategoryService.getProductsBySubcategoryId(subcategoryId)); // Fetch products for subcategory
//        return ResponseEntity.ok(response);
//    }
//
//    // Endpoint to update the selected category
//    @PostMapping("/update")
//    public String updateSelectedCategory(@RequestParam Long subcategoryId) {
//        selectedCategory2Service.updateSelectedCategory(subcategoryId);  // Update the selected category
//        return "Selected category updated successfully!";
//    }
//}

package com.cgpi.cgpi.controller;

import com.cgpi.cgpi.services.SelectedSubCategoryService;
import com.cgpi.cgpi.services.SubcategoryService;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/selected-subcategory")
public class SelectedSubCategoryController {

    @Autowired
    private SelectedSubCategoryService selectedSubCategoryService;

    @Autowired
    private SubcategoryService subcategoryService; // Inject SubcategoryService

    // Endpoint to retrieve the selected subcategory name
    @GetMapping
    public ResponseEntity<Map<String, Object>> getSelectedSubCategory() {
        Map<String, Object> response = new HashMap<>();
        response.put("subcategoryId", selectedSubCategoryService.getSelectedSubCategoryId());
        response.put("subcategoryName", selectedSubCategoryService.getSelectedSubCategoryName());
        response.put("subcategoryImage", selectedSubCategoryService.getSelectedSubCategoryImage());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/details-with-subcategories")
    public ResponseEntity<Map<String, Object>> getSelectedSubCategoryWithSubcategoriesAndProducts() {
        Map<String, Object> response = new HashMap<>();
        Long subcategoryId = selectedSubCategoryService.getSelectedSubCategoryId();
        response.put("subcategoryId", subcategoryId);
        response.put("subcategoryName", selectedSubCategoryService.getSelectedSubCategoryName());
        response.put("subcategoryImage", selectedSubCategoryService.getSelectedSubCategoryImage());
        response.put("subcategories", subcategoryService.getSubcategoriesByCategoryId(subcategoryId));
        response.put("products", subcategoryService.getProductsBySubcategoryId(subcategoryId)); // Fetch products for subcategory
        return ResponseEntity.ok(response);
    }

    // Endpoint to update the selected subcategory
    @PostMapping("/update")
    public String updateSelectedSubCategory(@RequestParam(required = false) Long subcategoryId) {
        selectedSubCategoryService.updateSelectedSubCategory(subcategoryId);  // Update the selected subcategory
        return "Selected subcategory updated successfully!";
    }
}

