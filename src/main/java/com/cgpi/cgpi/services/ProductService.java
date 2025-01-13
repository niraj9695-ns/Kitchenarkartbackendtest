package com.cgpi.cgpi.services;

import com.cgpi.cgpi.entity.Product;
import com.cgpi.cgpi.entity.ProductVariant;
import com.cgpi.cgpi.entity.Category;
import com.cgpi.cgpi.entity.Subcategory;
import com.cgpi.cgpi.entity.Subsubcategory;
import com.cgpi.cgpi.repository.CategoryRepository;
import com.cgpi.cgpi.repository.ProductRepository;
import com.cgpi.cgpi.repository.ProductVariantRepository;
import com.cgpi.cgpi.repository.SubcategoryRepository;
import com.cgpi.cgpi.repository.SubsubcategoryRepository;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductVariantRepository productVariantRepository; // Add this line
    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private SubcategoryRepository subcategoryRepository;

    @Autowired
    private SubsubcategoryRepository subsubcategoryRepository;

    public void importProductsFromExcel(MultipartFile file) throws IOException {
        List<Product> products = new ArrayList<>();

        try (InputStream is = file.getInputStream();
             XSSFWorkbook workbook = new XSSFWorkbook(is)) {

            Sheet sheet = workbook.getSheetAt(0);
            for (int i = 1; i <= ((XSSFSheet) sheet).getLastRowNum(); i++) { // Skip header row
                Row row = ((XSSFSheet) sheet).getRow(i);
                Product product = new Product();

                // Map Excel columns to Product fields
                product.setName(getStringCellValue(row.getCell(0)));
//                product.setProductCode(getStringCellValue(row.getCell(1)));
//                product.setPrice(getBigDecimalValue(row.getCell(2)));
                product.setTaxAmount(getBigDecimalValue(row.getCell(2)));
                product.setHsnCode(getStringCellValue(row.getCell(3)));

                // Set the subcategory and subsubcategory based on IDs from Excel
                Long subcategoryId = getLongValue(row.getCell(4));
                if (subcategoryId != null) {
                    Optional<Subcategory> subcategoryOpt = subcategoryRepository.findById(subcategoryId);
                    subcategoryOpt.ifPresent(product::setSubcategory);
                }

                Long subsubcategoryId = getLongValue(row.getCell(5));
                if (subsubcategoryId != null) {
                    Optional<Subsubcategory> subsubcategoryOpt = subsubcategoryRepository.findById(subsubcategoryId);
                    subsubcategoryOpt.ifPresent(product::setSubsubcategory);
                }

                // Add the product to the list
                products.add(product);
            }
        }

        productRepository.saveAll(products); // Bulk save products
    }

    // Helper methods for handling cell values
    private String getStringCellValue(Cell cell) {
        return (cell != null && cell.getCellType() == CellType.STRING) ? cell.getStringCellValue() : null;
    }

    private BigDecimal getBigDecimalValue(Cell cell) {
        return (cell != null && cell.getCellType() == CellType.NUMERIC) ? BigDecimal.valueOf(cell.getNumericCellValue()) : BigDecimal.ZERO;
    }

    private boolean getBooleanCellValue(Cell cell) {
        return (cell != null && cell.getCellType() == CellType.BOOLEAN) && cell.getBooleanCellValue();
    }

    private int getIntValue(Cell cell) {
        return (cell != null && cell.getCellType() == CellType.NUMERIC) ? (int) cell.getNumericCellValue() : 0;
    }

    private Long getLongValue(Cell cell) {
        return (cell != null && cell.getCellType() == CellType.NUMERIC) ? (long) cell.getNumericCellValue() : null;
    }

    // Add a new product
    public Product addProduct(Product product) {
    	 if (product.getCategory() != null) {
             Optional<Category> category = categoryRepository.findById(product.getCategory().getId());
             category.ifPresent(product::setCategory);
         }
        if (product.getSubcategory() != null) {
            Optional<Subcategory> subcategory = subcategoryRepository.findById(product.getSubcategory().getId());
            subcategory.ifPresent(product::setSubcategory);
        }

        if (product.getSubsubcategory() != null) {
            Optional<Subsubcategory> subsubcategory = subsubcategoryRepository.findById(product.getSubsubcategory().getId());
            subsubcategory.ifPresent(product::setSubsubcategory);
        }

        return productRepository.save(product);
    }

    // Update an existing product
    public Product updateProduct(Long id, Product product) {
        Optional<Product> existingProductOpt = productRepository.findById(id);
        if (existingProductOpt.isPresent()) {
            Product existingProduct = existingProductOpt.get();
            existingProduct.setName(product.getName());
//            existingProduct.setProductCode(product.getProductCode());

            existingProduct.setTaxAmount(product.getTaxAmount());
            existingProduct.setHsnCode(product.getHsnCode());

            if (product.getCategory() != null) {
                Optional<Category> category = categoryRepository.findById(product.getCategory().getId());
                category.ifPresent(existingProduct::setCategory);
            }

            if (product.getSubcategory() != null) {
                Optional<Subcategory> subcategory = subcategoryRepository.findById(product.getSubcategory().getId());
                subcategory.ifPresent(existingProduct::setSubcategory);
            }

            if (product.getSubsubcategory() != null) {
                Optional<Subsubcategory> subsubcategory = subsubcategoryRepository.findById(product.getSubsubcategory().getId());
                subsubcategory.ifPresent(existingProduct::setSubsubcategory);
            }

            return productRepository.save(existingProduct);
        } else {
            throw new RuntimeException("Product not found with id: " + id);
        }
    }

    public void deleteProduct(Long id) {
        if (!productRepository.existsById(id)) {
            throw new RuntimeException("Product not found with id: " + id);
        }
        productRepository.deleteById(id);
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }


 // Retrieve a Category by its ID
    public Optional<Category> getCategoryById(Long id) {
        return categoryRepository.findById(id);
    }

    // Retrieve all Categories
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    public Optional<Subcategory> getSubcategoryById(Long id) {
        return subcategoryRepository.findById(id);
    }

    public Optional<Subsubcategory> getSubsubcategoryById(Long id) {
        return subsubcategoryRepository.findById(id);
    }

    public List<Subcategory> getAllSubcategories() {
        return subcategoryRepository.findAll();
    }

    public List<Subsubcategory> getAllSubsubcategories() {
        return subsubcategoryRepository.findAll();
    }

    public Optional<Product> getProductById(Long id) {
        return productRepository.findById(id); // This assumes you're using Spring Data JPA
    }

    public List<Product> findProducts(Long categoryId,Long subcategoryId, Long subsubcategoryId) {
        // Check if all parameters are null; if so, return all products
        if (subcategoryId == null && subsubcategoryId == null) {
            return productRepository.findAll();
        }

        // Implement filtering logic based on provided parameters
        if (subsubcategoryId != null) {
            return productRepository.findBySubsubcategoryId(subsubcategoryId);
        } else if (subcategoryId != null) {
            return productRepository.findBySubcategoryId(subcategoryId);
        }

        return new ArrayList<>(); // Return an empty list if no criteria matched
    }

    // New methods for managing product variants
    public ProductVariant addVariantToProduct(Long productId, ProductVariant variant) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + productId));
        variant.setProduct(product);
        return productVariantRepository.save(variant);
    }

    public List<ProductVariant> getVariantsByProductId(Long productId) {
        return productVariantRepository.findByProductId(productId);
    }

    public void deleteVariant(Long variantId) {
        if (!productVariantRepository.existsById(variantId)) {
            throw new RuntimeException("ProductVariant not found with id: " + variantId);
        }
        productVariantRepository.deleteById(variantId);
    }
    public List<Product> getNewArrivals() {
        return productRepository.findByIsNewArrival(true);  // Assuming you're using Spring Data JPA
    }
    public List<Product> searchProducts(String query) {
        return productRepository.findByNameContainingIgnoreCaseOrCategory_NameContainingIgnoreCase(query, query);
    }
    
    public List<Product> getProductsByCategoryId(Long categoryId) {
        return productRepository.findByCategoryId(categoryId);
    }


}
