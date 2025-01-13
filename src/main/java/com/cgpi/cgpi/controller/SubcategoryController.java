package com.cgpi.cgpi.controller;

import com.cgpi.cgpi.entity.Subcategory;

import com.cgpi.cgpi.services.CategoryService;
import com.cgpi.cgpi.services.SelectedCategoryService;
import com.cgpi.cgpi.services.SubcategoryService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/subcategories")
@CrossOrigin(origins = "http://localhost:3000")
public class SubcategoryController {
	 @Autowired
	    private SelectedCategoryService selectedCategoryService;
    private final SubcategoryService subcategoryService;
    private final CategoryService categoryService;

    public SubcategoryController(SubcategoryService subcategoryService, CategoryService categoryService) {
        this.subcategoryService = subcategoryService;
        this.categoryService = categoryService;
    }

    @GetMapping
    public List<Subcategory> getAllSubcategories() {
        return subcategoryService.getAllSubcategories();
    }

    @PostMapping(consumes = {"multipart/form-data"})
    public ResponseEntity<Subcategory> addSubcategory(
            @RequestParam("name") String name,
            @RequestParam("image") MultipartFile image,
            @RequestParam("categoryId") Long categoryId) throws IOException {

        // Create a new Subcategory
        Subcategory subcategory = new Subcategory();
        subcategory.setName(name);
        subcategory.setImage(image.getBytes());
        subcategory.setCategoryId(categoryId); // Set the category ID

        // Save the subcategory
        Subcategory savedSubcategory = subcategoryService.saveSubcategory(subcategory);
        return new ResponseEntity<>(savedSubcategory, HttpStatus.CREATED); // Return created status
    }
    @PutMapping(value = "/{id}", consumes = {"multipart/form-data"})
    public ResponseEntity<Subcategory> updateSubcategory(
            @PathVariable Long id,
            @RequestParam("name") String name,
            @RequestParam(value = "image", required = false) MultipartFile image,
            @RequestParam("categoryId") Long categoryId) throws IOException {

        // Find the existing subcategory
        Subcategory existingSubcategory = subcategoryService.getSubcategoryById(id);
        if (existingSubcategory == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        // Update the subcategory details
        existingSubcategory.setName(name);
        if (image != null && !image.isEmpty()) {
            existingSubcategory.setImage(image.getBytes()); // Update the image if provided
        }
        existingSubcategory.setCategoryId(categoryId);

        // Save the updated subcategory
        Subcategory updatedSubcategory = subcategoryService.saveSubcategory(existingSubcategory);
        return new ResponseEntity<>(updatedSubcategory, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSubcategory(@PathVariable Long id) {
        subcategoryService.deleteSubcategory(id);
        return ResponseEntity.noContent().build(); // Return no content status
    }
    @GetMapping("/category/{id}")
    public ResponseEntity<List<Subcategory>> getSubcategoriesByCategoryId(@PathVariable Long id) {
        List<Subcategory> subcategories = subcategoryService.getSubcategoriesByCategoryId(id);
        return new ResponseEntity<>(subcategories, HttpStatus.OK);
    }
    @GetMapping("/selected")
    public ResponseEntity<List<Subcategory>> getSubcategoriesForSelectedCategory() {
        Long selectedCategoryId = selectedCategoryService.getSelectedCategoryId();
        List<Subcategory> subcategories = subcategoryService.getSubcategoriesByCategoryId(selectedCategoryId);
        return new ResponseEntity<>(subcategories, HttpStatus.OK);
    }

}
