package com.cgpi.cgpi.controller;

import com.cgpi.cgpi.entity.ProductVariant;

import com.cgpi.cgpi.services.ProductVariantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/products/{productId}/variants")
@CrossOrigin(origins = "http://localhost:3000")
public class ProductVariantController {

    @Autowired
    private ProductVariantService productVariantService;

    // Add a new variant
    @PostMapping
    public ResponseEntity<ProductVariant> addVariant(
            @PathVariable Long productId,
            @RequestParam String productCode,
            @RequestParam String color,
            @RequestParam String dimensions,
            @RequestParam BigDecimal price,
            @RequestParam int stock,
            @RequestParam(required = false) String includedComponents,
            @RequestParam(required = false) String productGrossWeight,
            @RequestParam(required = false) String description,
            @RequestParam(required = false) List<String> keyFeatures,
            @RequestParam(required = false) String otherDimensions,
            @RequestParam(required = false) String productWeight,
            @RequestParam(required = false) String power,
            @RequestParam(required = false) String material,
            @RequestParam(required = false) String capacity,
            @RequestParam(required = false) List<MultipartFile> images) {

        ProductVariant variant = new ProductVariant();
        variant.setProductCode(productCode);
        variant.setColor(color);
        variant.setDimensions(dimensions);
        variant.setPrice(price);
        variant.setStock(stock);
        variant.setIncludedComponents(includedComponents);
        variant.setProductGrossWeight(productGrossWeight);
        variant.setDescription(description);
        variant.setKeyFeatures(keyFeatures);
        variant.setOtherDimensions(otherDimensions);
        variant.setProductWeight(productWeight);
        variant.setPower(power);
        variant.setMaterial(material);
        variant.setCapacity(capacity);

        if (images != null) {
            // Convert MultipartFile to byte[]
            List<byte[]> imageBytes = images.stream()
                    .map(file -> {
                        try {
                            return file.getBytes(); // Convert image to byte array
                        } catch (Exception e) {
                            e.printStackTrace(); // Handle error as needed
                            return null;
                        }
                    })
                    .filter(image -> image != null) // Filter out null values
                    .collect(Collectors.toList());
            variant.setImages(imageBytes);
        }
        ProductVariant createdVariant = productVariantService.addVariant(productId, variant);
        return ResponseEntity.ok(createdVariant);
    }

    @PutMapping("/{variantId}")
    public ResponseEntity<ProductVariant> updateVariant(
            @PathVariable Long productId, 
            @PathVariable Long variantId,
            @RequestParam(required = false) String productCode,
            @RequestParam(required = false) String color,
            @RequestParam(required = false) String dimensions,
            @RequestParam(required = false) BigDecimal price,
            @RequestParam(required = false) Integer stock,
            @RequestParam(required = false) String includedComponents,
            @RequestParam(required = false) String productGrossWeight,
            @RequestParam(required = false) String description,
            @RequestParam(required = false) List<String> keyFeatures,
            @RequestParam(required = false) String otherDimensions,
            @RequestParam(required = false) String productWeight,
            @RequestParam(required = false) String power,
            @RequestParam(required = false) String material,
            @RequestParam(required = false) String capacity,
            @RequestParam(required = false) List<MultipartFile> newImages, 
            @RequestParam(required = false) String deleteImageIndices) {  // Keep String type for manual handling

        // Handle deleteImageIndices manually to avoid NumberFormatException
        List<Integer> deleteImageIndicesList = new ArrayList<>();
        if (deleteImageIndices != null && !deleteImageIndices.isEmpty()) {
            // Process the string into a list of integers
            try {
                String[] indices = deleteImageIndices.replace("[", "").replace("]", "").split(",");
                for (String index : indices) {
                    deleteImageIndicesList.add(Integer.parseInt(index.trim()));
                }
            } catch (NumberFormatException e) {
                // Handle malformed input gracefully (log or return error response)
                return ResponseEntity.badRequest().body(null);
            }
        }

        // Create ProductVariant object and set properties if provided
        ProductVariant variant = new ProductVariant();
        if (productCode != null) variant.setProductCode(productCode);
        if (color != null) variant.setColor(color);
        if (dimensions != null) variant.setDimensions(dimensions);
        if (price != null) variant.setPrice(price);
        if (stock != null) variant.setStock(stock);
        if (includedComponents != null) variant.setIncludedComponents(includedComponents);
        if (productGrossWeight != null) variant.setProductGrossWeight(productGrossWeight);
        if (description != null) variant.setDescription(description);
        if (keyFeatures != null) variant.setKeyFeatures(keyFeatures);
        if (otherDimensions != null) variant.setOtherDimensions(otherDimensions);
        if (productWeight != null) variant.setProductWeight(productWeight);
        if (power != null) variant.setPower(power);
        if (material != null) variant.setMaterial(material);
        if (capacity != null) variant.setCapacity(capacity);

        // Pass the deleteImageIndices and newImages to the service
        ProductVariant updatedVariant = productVariantService.updateVariant(
                productId, variantId, variant, newImages, deleteImageIndicesList);

        return ResponseEntity.ok(updatedVariant);
    }


    // Delete a variant
    @DeleteMapping("/{variantId}")
    public ResponseEntity<Void> deleteVariant(@PathVariable Long productId, @PathVariable Long variantId) {
        productVariantService.deleteVariant(productId, variantId);
        return ResponseEntity.noContent().build();
    }

    // Get all variants for a specific product
    @GetMapping
    public ResponseEntity<List<ProductVariant>> getAllVariants(@PathVariable Long productId) {
        List<ProductVariant> variants = productVariantService.getVariantsByProductId(productId);
        return ResponseEntity.ok(variants);
    }
    
    
}
