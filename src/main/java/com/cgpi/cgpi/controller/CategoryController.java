package com.cgpi.cgpi.controller;

import com.cgpi.cgpi.entity.Category;
import com.cgpi.cgpi.services.CategoryService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/categories")
@CrossOrigin(origins = "http://localhost:3000")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public List<Category> getAllCategories() {
        return categoryService.getAllCategories();
        
    }
    
    @GetMapping("/top")
    public List<Category> getTopCategories() {
        return categoryService.getTopCategories(); // This will be handled in the service layer
    }

    @PostMapping(consumes = {"multipart/form-data"})
    public Category addCategory(
            @RequestParam("name") String name,
            @RequestParam("image") MultipartFile image,
            @RequestParam("topCategory") boolean topCategory) throws IOException {
        
        byte[] imageData = image.getBytes();
        Category category = new Category();
        category.setName(name);
        category.setImage(imageData);
        category.setTopCategory(topCategory);

        return categoryService.saveCategory(category);
    }
    @PutMapping("/{id}")
    public Category updateCategory(
            @PathVariable Long id,
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "image", required = false) MultipartFile image,
            @RequestParam(value = "topCategory", required = false) Boolean topCategory) throws IOException {

        byte[] imageData = (image != null) ? image.getBytes() : null;

        return categoryService.updateCategory(id, name, imageData, topCategory);
    }


    @DeleteMapping("/{id}")
    public void deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategory(id);
    }
}    