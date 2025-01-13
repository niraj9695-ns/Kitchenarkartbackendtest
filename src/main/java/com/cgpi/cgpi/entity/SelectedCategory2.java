package com.cgpi.cgpi.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "selected_subcategory")  // Defines the table name in the database
public class SelectedCategory2 {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // Auto-incrementing primary key
    private Long id;

    @Column(name = "subcategory_id", nullable = false)  // Stores the selected category ID
    private Long subcategoryId;

    // Getter for 'id'
    public Long getId() {
        return id;
    }

    // Setter for 'id'
    public void setId(Long id) {
        this.id = id;
    }

    // Getter for 'categoryId'
    public Long getSubcategoryId() {
        return subcategoryId;
    }

    // Setter for 'categoryId'
    public void setSubcategoryId(Long subcategoryId) {
        this.subcategoryId = subcategoryId;
    }
}
