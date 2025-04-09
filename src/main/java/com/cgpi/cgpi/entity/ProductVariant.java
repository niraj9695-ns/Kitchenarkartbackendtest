//package com.cgpi.cgpi.entity;
//
//import jakarta.persistence.*;
//import java.math.BigDecimal;
//import java.util.ArrayList;
//import java.util.List;
//
//import com.fasterxml.jackson.annotation.JsonIgnore;
//
//@Entity
//@Table(name = "product_variants")
//public class ProductVariant {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    @ManyToOne
//    @JoinColumn(name = "product_id")
//    @JsonIgnore
//    private Product product;
//
//    private Boolean inStock;
//    private Integer quantity;
//    private String description;
//
//    @ElementCollection
//    @CollectionTable(name = "product_variant_key_features", joinColumns = @JoinColumn(name = "product_variant_id"))
//    @Column(name = "feature", columnDefinition = "TEXT") // Ensures sufficient storage for long text
//    private List<String> keyFeatures = new ArrayList<>();
//
//    private String otherDimensions;
//    private String productWeight;
//    private String power; // Power in Watts, nullable if non-electric product
//    private String material;
//    private String capacity;
//
//    private String color; // Variant color
//    @Lob
//    @Column(columnDefinition = "LONGTEXT")
//    private String dimensions; // Dimensions for the variant
//    private BigDecimal price; // Price for the variant
//    private Integer stock; // Stock for the variant
//    private String productCode;
//    private Integer lowStockThreshold; 
//    private BigDecimal taxAmount; 
//
//    @ElementCollection
//  
//    @Lob // To store images as LongBlob
//    private List<byte[]> images = new ArrayList<>(); // Field for multiple images
//
//    // Getters and Setters
//    public Long getId() {
//        return id;
//    }
//
//    public void setId(Long id) {
//        this.id = id;
//    }
//
//    public Product getProduct() {
//        return product;
//    }
//
//    public void setProduct(Product product) {
//        this.product = product;
//    }
//
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
//    public List<String> getKeyFeatures() {
//        return keyFeatures;
//    }
//
//    public void setKeyFeatures(List<String> keyFeatures) {
//        this.keyFeatures = keyFeatures;
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
//    public  String getPower() {
//        return power;
//    }
//
//    public void setPower(String power) {
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
//    public String getCapacity() {
//        return capacity;
//    }
//
//    public void setCapacity(String capacity) {
//        this.capacity = capacity;
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
//    public String getDimensions() {
//        return dimensions;
//    }
//
//    public void setDimensions(String dimensions) {
//        this.dimensions = dimensions;
//    }
//
//    public BigDecimal getPrice() {
//        return price;
//    }
//
//    public void setPrice(BigDecimal price) {
//        this.price = price;
//    }
//
//    public Integer getStock() {
//        return stock;
//    }
//
//    public void setStock(Integer stock) {
//        this.stock = stock;
//    }
//
//    public List<byte[]> getImages() {
//        return images;
//    }
//
//    public void setImages(List<byte[]> images) {
//        this.images = images;
//    }
//    public String getProductCode() {
//        return productCode;
//    }
//
//    public void setProductCode(String productCode) {
//        this.productCode = productCode;
//    }
//
//    public BigDecimal getTaxAmount() {
//        if (product != null && product.getTaxPercentage() != null) {
//            return price.multiply(product.getTaxPercentage().divide(BigDecimal.valueOf(100))); // Calculate tax
//        }
//        return BigDecimal.ZERO; // Default to zero if no tax is set
//    }
//
//	public void setTaxAmount(BigDecimal taxAmount) {
//		// TODO Auto-generated method stub
//		
//	}
// 
//}

package com.cgpi.cgpi.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;


@Entity
@Table(name = "product_variants")
public class ProductVariant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

//    @ManyToOne
//    @JoinColumn(name = "product_id")
//    @JsonIgnore
//    private Product product;

    @ManyToOne(fetch = FetchType.LAZY) // Lazy fetch to avoid unnecessary joins
	@JoinColumn(name = "product_id", nullable = false)
	@JsonBackReference // Prevents circular reference issues
	private Product product;

	@Column(name = "product_id", insertable = false, updatable = false)
	@JsonProperty("productId") // Ensures productId is returned in JSON response
	private Long productId;

    @Lob
    @Column(name = "description", columnDefinition = "LONGTEXT")
    private String description;
    
    
    
    @ElementCollection
    @CollectionTable(name = "product_variant_key_features", joinColumns = @JoinColumn(name = "product_variant_id"))
    @Column(name = "feature", columnDefinition = "TEXT")
    
    private List<String> keyFeatures = new ArrayList<>();
    private String otherDimensions;
    private String productWeight;
    private String productGrossWeight;
    public String getProductGrossWeight() {
		return productGrossWeight;
	}
	public void setProductGrossWeight(String productGrossWeight) {
		this.productGrossWeight = productGrossWeight;
	}

	private String power; 
    private String material;
    private String capacity;

    private String color; 
    @Lob
    @Column(columnDefinition = "LONGTEXT")
    private String dimensions; 
    @Lob
    @Column(columnDefinition = "LONGTEXT")
    private String includedComponents;
   
    private BigDecimal price; 
    private Integer stock; 
    private String productCode;
    
    private BigDecimal taxAmount; // Newly added
    private BigDecimal priceExcludingTax; // Newly added


    @ElementCollection
    @Lob
    @Column(columnDefinition = "LONGBLOB")
    private List<byte[]> images = new ArrayList<>(); 

 


    public BigDecimal getPrice() { return price; }
    public void setPrice(BigDecimal price) { 
        this.price = price; 
        calculateTaxValues();
    }
    private void calculateTaxValues() {
        if (product != null && product.getTaxPercentage() != null && price != null) {
            BigDecimal taxRate = product.getTaxPercentage().divide(BigDecimal.valueOf(100)); 
            this.taxAmount = price.multiply(taxRate);
            this.priceExcludingTax = price.subtract(this.taxAmount);
        } else {
            this.taxAmount = BigDecimal.ZERO;
            this.priceExcludingTax = price;
        }
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Product getProduct() { return product; }
//    public void setProduct(Product product) { this.product = product; }
    public void setProduct(Product product) { 
        this.product = product; 
        calculateTaxValues(); // Ensure tax calculation runs when the product is assigned
    }


    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public List<String> getKeyFeatures() { return keyFeatures; }
    public void setKeyFeatures(List<String> keyFeatures) { this.keyFeatures = keyFeatures; }

    public String getOtherDimensions() { return otherDimensions; }
    public void setOtherDimensions(String otherDimensions) { this.otherDimensions = otherDimensions; }

    public String getProductWeight() { return productWeight; }
    public void setProductWeight(String productWeight) { this.productWeight = productWeight; }

    public String getPower() { return power; }
    public void setPower(String power) { this.power = power; }

    public String getMaterial() { return material; }
    public void setMaterial(String material) { this.material = material; }

    public String getCapacity() { return capacity; }
    public void setCapacity(String capacity) { this.capacity = capacity; }

    public String getColor() { return color; }
    public void setColor(String color) { this.color = color; }

    public String getDimensions() { return dimensions; }
    public void setDimensions(String dimensions) { this.dimensions = dimensions; }



    public Integer getStock() { return stock; }
    public void setStock(Integer stock) { this.stock = stock; }

    public List<byte[]> getImages() { return images; }
    public void setImages(List<byte[]> images) { this.images = images; }

    public String getProductCode() { return productCode; }
    public void setProductCode(String productCode) { this.productCode = productCode; }

    public BigDecimal getTaxAmount() { return taxAmount; }
    public BigDecimal getPriceExcludingTax() { return priceExcludingTax; }

    public String getIncludedComponents() {
		return includedComponents;
	}
	public void setIncludedComponents(String includedComponents) {
		this.includedComponents = includedComponents;
	}
    
    @JsonProperty("productName") // Ensures it appears in JSON responses
    public String getProductName() {
        return (product != null) ? product.getName() : null;
    }

}
