package com.cgpi.cgpi.services;

import com.cgpi.cgpi.entity.Product;
import com.cgpi.cgpi.entity.ProductVariant;
import com.cgpi.cgpi.repository.ProductRepository;
import com.cgpi.cgpi.repository.ProductVariantRepository;
import com.cgpi.cgpi.exception.ResourceNotFoundException; // Custom exception
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductVariantService {

    @Autowired
    private ProductVariantRepository productVariantRepository;

    @Autowired
    private ProductRepository productRepository;
    
    
    
    
    

    // Add a new variant to a product
    public ProductVariant addVariant(Long productId, ProductVariant variant) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + productId));
        variant.setProduct(product);
        return productVariantRepository.save(variant);
    }

    // Retrieve all variants for a specific product
    public List<ProductVariant> getVariantsByProductId(Long productId) {
        return productVariantRepository.findByProductId(productId);
    }

    public ProductVariant updateVariant(Long productId, Long variantId, ProductVariant variant, 
            List<MultipartFile> newImages, List<Integer> deleteImageIndices) {

ProductVariant existingVariant = productVariantRepository.findById(variantId)
.orElseThrow(() -> new ResourceNotFoundException("ProductVariant not found with id: " + variantId));

// Optionally verify the productId to ensure you're updating the right variant for the correct product
if (!existingVariant.getProduct().getId().equals(productId)) {
throw new ResourceNotFoundException("Variant does not belong to the specified product.");
}

// Update other fields of the variant
existingVariant.setProductCode(variant.getProductCode());
existingVariant.setColor(variant.getColor());
existingVariant.setDimensions(variant.getDimensions());
existingVariant.setPrice(variant.getPrice());
existingVariant.setStock(variant.getStock());
existingVariant.setIncludedComponents(variant.getIncludedComponents());
existingVariant.setProductGrossWeight(variant.getProductGrossWeight());
existingVariant.setDescription(variant.getDescription());
existingVariant.setKeyFeatures(variant.getKeyFeatures());
existingVariant.setOtherDimensions(variant.getOtherDimensions());
existingVariant.setProductWeight(variant.getProductWeight());
existingVariant.setPower(variant.getPower());
existingVariant.setMaterial(variant.getMaterial());
existingVariant.setCapacity(variant.getCapacity());

// Handle image deletions
if (deleteImageIndices != null && !deleteImageIndices.isEmpty()) {
List<byte[]> images = existingVariant.getImages();
for (int index : deleteImageIndices) {
if (index >= 0 && index < images.size()) {
images.remove(index);
}
}
existingVariant.setImages(images);
}

// Handle adding new images
if (newImages != null && !newImages.isEmpty()) {
List<byte[]> images = existingVariant.getImages();
List<byte[]> newImageBytes = newImages.stream()
.map(file -> {
try {
return file.getBytes();
} catch (Exception e) {
e.printStackTrace();
return null;
}
})
.filter(Objects::nonNull)
.collect(Collectors.toList());

images.addAll(newImageBytes);
existingVariant.setImages(images);
}

// Save and return the updated product variant
return productVariantRepository.save(existingVariant);
}


    // Delete a variant by ID for a specific product
    public void deleteVariant(Long productId, Long variantId) {
        ProductVariant existingVariant = productVariantRepository.findById(variantId)
                .orElseThrow(() -> new ResourceNotFoundException("ProductVariant not found with id: " + variantId));

        // Optionally verify the productId to ensure you're deleting the correct variant for the product
        if (!existingVariant.getProduct().getId().equals(productId)) {
            throw new ResourceNotFoundException("Variant does not belong to the specified product.");
        }

        productVariantRepository.deleteById(variantId);
    }

    // Get a specific variant by ID and ensure it belongs to the specified product
    public Optional<ProductVariant> getVariantById(Long productId, Long variantId) {
        ProductVariant variant = productVariantRepository.findById(variantId)
                .orElseThrow(() -> new ResourceNotFoundException("ProductVariant not found with id: " + variantId));

        // Check if the variant belongs to the given product
        if (!variant.getProduct().getId().equals(productId)) {
            throw new ResourceNotFoundException("Variant does not belong to the specified product.");
        }

        return Optional.of(variant);
    }

    public ProductVariant updateStock(Long variantId, Integer newStock) {
        ProductVariant variant = productVariantRepository.findById(variantId)
                .orElseThrow(() -> new RuntimeException("Variant not found"));

        // Set the new stock value
        variant.setStock(newStock);

        return productVariantRepository.save(variant);
    }

    
}
