package com.cgpi.cgpi.controller;

import com.cgpi.cgpi.entity.Subsubcategory;
import com.cgpi.cgpi.services.SubsubcategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/subsubcategories")
@CrossOrigin(origins = "http://localhost:3000")
public class SubsubcategoryController {

    @Autowired
    private SubsubcategoryService subsubcategoryService;

    @GetMapping
    public ResponseEntity<List<Subsubcategory>> getAllSubsubcategories() {
        List<Subsubcategory> subsubcategories = subsubcategoryService.findAll();
        return ResponseEntity.ok(subsubcategories); // Always return the list (even if it's empty)
    }

    // Get subsubcategory by ID
    @GetMapping("/{id}")
    public ResponseEntity<Subsubcategory> getSubsubcategoryById(@PathVariable Long id) {
        Subsubcategory subsubcategory = subsubcategoryService.findById(id);
        if (subsubcategory != null) {
            return ResponseEntity.ok(subsubcategory);
        }
        return ResponseEntity.notFound().build(); // Return 404 Not Found if not found
    }

    @PostMapping
    public ResponseEntity<Subsubcategory> createSubsubcategory(
            @RequestParam("name") String name,
            @RequestParam("subcategoryId") Long subcategoryId,
            @RequestParam(value = "image", required = false) MultipartFile image) throws IOException {
        
        Subsubcategory subsubcategory = new Subsubcategory();
        subsubcategory.setName(name);
        subsubcategory.setSubcategoryId(subcategoryId);

        // Handle file upload
        if (image != null && !image.isEmpty()) {
            subsubcategory.setImage(image.getBytes()); // Convert MultipartFile to byte array
        }

        Subsubcategory createdSubsubcategory = subsubcategoryService.create(subsubcategory);
        return ResponseEntity.ok(createdSubsubcategory);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Subsubcategory> updateSubsubcategory(
            @PathVariable Long id,
            @RequestParam("name") String name,
            @RequestParam("subcategoryId") Long subcategoryId,
            @RequestParam(value = "image", required = false) MultipartFile image) throws IOException {

        Subsubcategory existingSubsubcategory = subsubcategoryService.findById(id);
        if (existingSubsubcategory == null) {
            return ResponseEntity.notFound().build();
        }

        existingSubsubcategory.setName(name);
        existingSubsubcategory.setSubcategoryId(subcategoryId);

        // Update the image only if a new one is provided
        if (image != null && !image.isEmpty()) {
            existingSubsubcategory.setImage(image.getBytes());
        }

        Subsubcategory updatedSubsubcategory = subsubcategoryService.update(id, existingSubsubcategory);
        return ResponseEntity.ok(updatedSubsubcategory);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSubsubcategory(@PathVariable Long id) {
        subsubcategoryService.delete(id);
        return ResponseEntity.noContent().build();
    }
    @GetMapping("/subcategory/{subcategoryId}")
    public ResponseEntity<List<Subsubcategory>> getSubsubcategoriesBySubcategoryId(@PathVariable Long subcategoryId) {
        List<Subsubcategory> subsubcategories = subsubcategoryService.findBySubcategoryId(subcategoryId);
        if (!subsubcategories.isEmpty()) {
            return ResponseEntity.ok(subsubcategories);
        }
        return ResponseEntity.notFound().build(); // Return 404 if no subsubcategories found
    }

}
