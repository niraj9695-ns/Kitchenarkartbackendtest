
package com.cgpi.cgpi.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.CascadeType;
import jakarta.persistence.ElementCollection;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

//    private String productCode;

//    private BigDecimal price;

    private BigDecimal taxAmount;

    private String hsnCode;
    private boolean isNewArrival;
//    private Integer lowStockThreshold; 

//    private Boolean inStock;
//
//    private Integer quantity;
//
//    private String description;
//
//    @ElementCollection
//    @Lob // To store images as LongBlob
//    private List<byte[]> images = new ArrayList<>(); // Field for multiple images
//
//    @ElementCollection
//    private List<String> keyFeatures;
//
//    private String dimensions; // L x B x H
//
//    private String otherDimensions;
//
//    private String productWeight;
//
//    private Integer power; // Power in Watts, nullable if non-electric product
//
//    private String material;
//
//    private String color;
//
//    private String capacity; // e.g., liters, milliliters
//
    // Relationships
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @ManyToOne
    @JoinColumn(name = "subcategory_id")
    private Subcategory subcategory;
    
    @ManyToOne
    @JoinColumn(name = "subsubcategory_id")
    private Subsubcategory subsubcategory;

    // Add a one-to-many relationship to ProductVariant
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<ProductVariant> variants = new ArrayList<>();


    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

//    public String getProductCode() {
//        return productCode;
//    }
//
//    public void setProductCode(String productCode) {
//        this.productCode = productCode;
//    }

//    public BigDecimal getPrice() {
//        return price;
//    }
//
//    public void setPrice(BigDecimal price) {
//        this.price = price;
//    }

    public BigDecimal getTaxAmount() {
        return taxAmount;
    }

    public void setTaxAmount(BigDecimal taxAmount) {
        this.taxAmount = taxAmount;
    }

    public String getHsnCode() {
        return hsnCode;
    }

    public void setHsnCode(String hsnCode) {
        this.hsnCode = hsnCode;
    }

//    public Boolean getInStock() {
//        return inStock;
//    }
//
//    public void setInStock(Boolean inStock) {
//        this.inStock = inStock;
//    }
//
//    public Integer getQuantity() {
//        return quantity;
//    }
//
//    public void setQuantity(Integer quantity) {
//        this.quantity = quantity;
//    }
//
//    public String getDescription() {
//        return description;
//    }
//
//    public void setDescription(String description) {
//        this.description = description;
//    }
//
//    public List<byte[]> getImages() {
//        return images;
//    }
//
//    public void setImages(List<byte[]> images) {
//        this.images = images;
//    }
//
//    public List<String> getKeyFeatures() {
//        return keyFeatures;
//    }
//
//    public void setKeyFeatures(List<String> keyFeatures) {
//        this.keyFeatures = keyFeatures;
//    }
//
//    public String getDimensions() {
//        return dimensions;
//    }
//
//    public void setDimensions(String dimensions) {
//        this.dimensions = dimensions;
//    }
//
//    public String getOtherDimensions() {
//        return otherDimensions;
//    }
//
//    public void setOtherDimensions(String otherDimensions) {
//        this.otherDimensions = otherDimensions;
//    }
//
//    public String getProductWeight() {
//        return productWeight;
//    }
//
//    public void setProductWeight(String productWeight) {
//        this.productWeight = productWeight;
//    }
//
//    public Integer getPower() {
//        return power;
//    }
//
//    public void setPower(Integer power) {
//        this.power = power;
//    }
//
//    public String getMaterial() {
//        return material;
//    }
//
//    public void setMaterial(String material) {
//        this.material = material;
//    }
//
//    public String getColor() {
//        return color;
//    }
//
//    public void setColor(String color) {
//        this.color = color;
//    }
//
//    public String getCapacity() {
//        return capacity;
//    }
//
//    public void setCapacity(String capacity) {
//        this.capacity = capacity;
//    }
    public boolean isNewArrival() {
        return isNewArrival;
    }

    public void setNewArrival(boolean newArrival) {
        isNewArrival = newArrival;
    }
    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Subcategory getSubcategory() {
        return subcategory;
    }

    public void setSubcategory(Subcategory subcategory) {
        this.subcategory = subcategory;
    }

    public Subsubcategory getSubsubcategory() {
        return subsubcategory;
    }

    public void setSubsubcategory(Subsubcategory subsubcategory) {
        this.subsubcategory = subsubcategory;
    }
    public List<ProductVariant> getVariants() {
        return variants;
    }

    public void setVariants(List<ProductVariant> variants) {
        this.variants = variants;
    }
//    public Integer getLowStockThreshold() {
//        return lowStockThreshold;
//    }
//
//    public void setLowStockThreshold(Integer lowStockThreshold) {
//        this.lowStockThreshold = lowStockThreshold;
//    }
}
