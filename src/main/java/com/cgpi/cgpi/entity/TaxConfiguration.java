package com.cgpi.cgpi.entity;



import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "tax_configuration")
public class TaxConfiguration {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "tax_percentage")
    private BigDecimal taxPercentage; // Global tax percentage

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getTaxPercentage() {
        return taxPercentage;
    }

    public void setTaxPercentage(BigDecimal taxPercentage) {
        this.taxPercentage = taxPercentage;
    }
}

