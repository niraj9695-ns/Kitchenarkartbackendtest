package com.cgpi.cgpi.controller;

import com.cgpi.cgpi.entity.Product;
import com.cgpi.cgpi.entity.ProductVariant;
import com.cgpi.cgpi.services.ProductService;
import com.cgpi.cgpi.services.SelectedCategoryService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/products")
@CrossOrigin(origins = "http://localhost:3000")
public class ProductController {

    @Autowired
    private ProductService productService;
    
    @Autowired
    private SelectedCategoryService selectedCategoryService;

    // Endpoint to upload products from Excel
    @PostMapping("/upload")
    public ResponseEntity<String> uploadProducts(@RequestParam("file") MultipartFile file) {
        try {
            productService.importProductsFromExcel(file);
            return ResponseEntity.ok("Products uploaded successfully");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    // Endpoint to add a single product
    @PostMapping
    public ResponseEntity<Product> addProduct(
            @RequestParam("name") String name,
            @RequestParam("productCode") String productCode,
            @RequestParam("taxAmount") BigDecimal taxAmount,
            @RequestParam("hsnCode") String hsnCode,
            @RequestParam(value = "categoryId", required = false) Long categoryId, // Allow null
            @RequestParam(value = "subcategoryId", required = false) Long subcategoryId, // Allow null
            @RequestParam(value = "subsubcategoryId", required = false) Long subsubcategoryId, // Allow null
            @RequestParam(value = "images", required = false) List<MultipartFile> images,
            @RequestParam(value = "isNewArrival", required = false) boolean isNewArrival) {
        try {
            Product product = new Product();
            product.setName(name);
            product.setProductCode(productCode);
            product.setTaxAmount(taxAmount);
            product.setHsnCode(hsnCode);
            product.setNewArrival(isNewArrival); // Set whether it's a new arrival

            // Set category if present
            if (categoryId != null) {
                productService.getCategoryById(categoryId)
                        .ifPresentOrElse(product::setCategory, 
                                () -> {
                                    throw new RuntimeException("Category not found for ID: " + categoryId);
                                });
            }

            // Set subcategory if present
            if (subcategoryId != null) {
                productService.getSubcategoryById(subcategoryId)
                        .ifPresentOrElse(product::setSubcategory, 
                                () -> {
                                    throw new RuntimeException("Subcategory not found for ID: " + subcategoryId);
                                });
            }

            // Set subsubcategory if present
            if (subsubcategoryId != null) {
                productService.getSubsubcategoryById(subsubcategoryId)
                        .ifPresentOrElse(product::setSubsubcategory, 
                                () -> {
                                    throw new RuntimeException("Subsubcategory not found for ID: " + subsubcategoryId);
                                });
            }

            // Save the product
            Product savedProduct = productService.addProduct(product);

            return ResponseEntity.ok(savedProduct);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(null); // Return appropriate error message
        }
    }


    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(
            @PathVariable Long id,
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "productCode", required = false) String productCode,
            @RequestParam(value = "taxAmount", required = false) BigDecimal taxAmount,
            @RequestParam(value = "hsnCode", required = false) String hsnCode,
            @RequestParam(value = "categoryId", required = false) Long categoryId, // New parameter for category
            @RequestParam(value = "subcategoryId", required = false) Long subcategoryId,
            @RequestParam(value = "subsubcategoryId", required = false) Long subsubcategoryId,
            @RequestParam(value = "isNewArrival", required = false) Boolean isNewArrival, // New parameter
            @RequestParam(value = "imagesToDelete", required = false) List<Integer> imagesToDelete,
            @RequestParam(value = "images", required = false) List<MultipartFile> images) {
        try {
            Optional<Product> existingProductOpt = productService.getProductById(id);
            if (!existingProductOpt.isPresent()) {
                return ResponseEntity.notFound().build();
            }
            Product existingProduct = existingProductOpt.get();

            // Update existing product fields only if the new value is provided
            if (name != null) existingProduct.setName(name);
            if (productCode != null) existingProduct.setProductCode(productCode);
            if (taxAmount != null) existingProduct.setTaxAmount(taxAmount);
            if (hsnCode != null) existingProduct.setHsnCode(hsnCode);

            // Handle Category
            if (categoryId != null) {
                productService.getCategoryById(categoryId)
                        .ifPresent(existingProduct::setCategory);
            }

            // Handle Subcategory
            if (subcategoryId != null) {
                productService.getSubcategoryById(subcategoryId)
                        .ifPresent(existingProduct::setSubcategory);
            }

            // Handle Subsubcategory
            if (subsubcategoryId != null) {
                productService.getSubsubcategoryById(subsubcategoryId)
                        .ifPresent(existingProduct::setSubsubcategory);
            } else {
                // Clear the subsubcategory association if subsubcategoryId is null
                existingProduct.setSubsubcategory(null);
            }

            // Handle isNewArrival field
            if (isNewArrival != null) {
                existingProduct.setNewArrival(isNewArrival);
            }

//           
//            // Update the product in the database
            Product updatedProduct = productService.updateProduct(id, existingProduct);

            return ResponseEntity.ok(updatedProduct);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }

    // Endpoint to delete a product by its ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        try {
            productService.deleteProduct(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    
    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts() {
        List<Product> products = productService.getAllProducts();
        return ResponseEntity.ok(products);
    }

   
    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id) {
        try {
            Optional<Product> productOpt = productService.getProductById(id);
            if (productOpt.isPresent()) {
                return ResponseEntity.ok(productOpt.get());
            } else {
                return ResponseEntity.notFound().build();  // Return 404 if the product is not found
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }

    // Endpoint to get products by category, subcategory, or subsubcategory
    @GetMapping("/filter")
    public ResponseEntity<List<Product>> getProductsByFilter(
    		@RequestParam(value = "categoryId", required = false) Long categoryId,
            @RequestParam(value = "subcategoryId", required = false) Long subcategoryId,
            @RequestParam(value = "subsubcategoryId", required = false) Long subsubcategoryId) {
        List<Product> products = productService.findProducts(categoryId,subcategoryId, subsubcategoryId);
        return ResponseEntity.ok(products);
    }
    @GetMapping("/new-arrivals")
    public ResponseEntity<List<Product>> getNewArrivals() {
        List<Product> newArrivals = productService.getNewArrivals();
        return ResponseEntity.ok(newArrivals);
    }
    
    @GetMapping("/search")
    public ResponseEntity<List<Product>> searchProducts(@RequestParam("query") String query) {
        List<Product> products = productService.searchProducts(query);
        return ResponseEntity.ok(products);
    }
    @GetMapping("/selected-category")
    public List<Product> getProductsBySelectedCategory() {
        Long selectedCategoryId = selectedCategoryService.getSelectedCategoryId();  // Get the stored category ID
        if (selectedCategoryId == null) {
            return Collections.emptyList();  // Return an empty list if no category is selected
        }
        return productService.getProductsByCategoryId(selectedCategoryId);  // Fetch and return products for the selected category
    }
    
    
}
