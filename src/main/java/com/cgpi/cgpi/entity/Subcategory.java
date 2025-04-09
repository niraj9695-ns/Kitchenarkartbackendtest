package com.cgpi.cgpi.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Subcategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Lob
    @Column(columnDefinition = "LONGBLOB")
    private byte[] image;

    @Column(name = "category_id", nullable = false)
    private Long categoryId;

    // Avoid infinite recursion by using JsonBackReference for Category relation
    @ManyToOne
    @JoinColumn(name = "category_id", insertable = false, updatable = false)
    @JsonBackReference
    private Category category;

    // Ensure subsubcategories list is initialized to avoid null pointer exceptions
    @OneToMany(mappedBy = "subcategory", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference // Use this to serialize subsubcategories
    private List<Subsubcategory> subsubcategories = new ArrayList<>();

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

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public List<Subsubcategory> getSubsubcategories() {
        return subsubcategories;
    }

    public void setSubsubcategories(List<Subsubcategory> subsubcategories) {
        this.subsubcategories = subsubcategories;
    }
}
