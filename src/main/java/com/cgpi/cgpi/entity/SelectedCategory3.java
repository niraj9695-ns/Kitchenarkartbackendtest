package com.cgpi.cgpi.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "selected_category3")  // Defines the table name in the database
public class SelectedCategory3 {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // Auto-incrementing primary key
    private Long id;

    @Column(name = "category_id", nullable = true)  // Stores the selected category ID
    private Long categoryId;

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }
}
