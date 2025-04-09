
package com.cgpi.cgpi.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;

import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import jakarta.persistence.CascadeType;


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



    private BigDecimal taxAmount;

    private String hsnCode;
    private boolean isNewArrival;
    private boolean isMostSelling;
    
    private BigDecimal taxPercentage; // Store tax percentage at the product level
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
    public boolean isMostSelling() {
        return isMostSelling;
    }

    public void setMostSelling(boolean mostSelling) {
    	isMostSelling = mostSelling;
    }
    
    public BigDecimal getTaxPercentage() {
        return taxPercentage;
    }

    public void setTaxPercentage(BigDecimal taxPercentage) {
        this.taxPercentage = taxPercentage;
        updateVariantTaxes();
    }

    private void updateVariantTaxes() {
        if (variants != null) {
            for (ProductVariant variant : variants) {
                variant.setProduct(this); // This will trigger tax calculation in ProductVariant
            }
        }
    }


}
