package com.cgpi.cgpi.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Column;
import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
public class Subsubcategory {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Lob
    private byte[] image; // Optional, for storing an image for the subsubcategory

    // Long field for subcategory ID
    @Column(name = "subcategory_id", nullable = false) // Ensure subcategory_id cannot be null
    private Long subcategoryId;

    // Prevent direct insert/update of subcategory_id
    @ManyToOne
    @JoinColumn(name = "subcategory_id", insertable = false, updatable = false)
    @JsonBackReference // Prevent recursion during serialization
    private Subcategory subcategory;

    // Getters and setters
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

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public Long getSubcategoryId() {
        return subcategoryId; // Use Long for subcategoryId
    }

    public void setSubcategoryId(Long subcategoryId) {
        this.subcategoryId = subcategoryId; // Set subcategoryId directly
    }

    public Subcategory getSubcategory() {
        return subcategory; // Relationship to Subcategory entity
    }

    public void setSubcategory(Subcategory subcategory) {
        this.subcategory = subcategory;
    }
}
