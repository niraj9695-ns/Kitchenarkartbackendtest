package com.cgpi.cgpi.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "selected_sub_category3")  // Defines a different table name in the database
public class SelectedSubCategory3 {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // Auto-incrementing primary key
    private Long id;
    
    @Column(name = "category_id", nullable = true)  // Stores the selected category ID
    private Long categoryId;
    

    // Getter for 'id'
    public Long getId() {
        return id;
    }

    // Setter for 'id'
    public void setId(Long id) {
        this.id = id;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    // Setter for 'categoryId'
    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }
}
