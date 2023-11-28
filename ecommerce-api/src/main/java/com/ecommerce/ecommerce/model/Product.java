package com.ecommerce.ecommerce.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "products")
@Data
@NoArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;
    @ManyToOne
    @JoinColumn(name = "brand_id", nullable = false)
    private Brand brand;
    private String name;
    private String description;
    private BigDecimal price;
    private int stockQuantity;

    @OneToMany(mappedBy = "product")
    private List<ProductImage> images = new ArrayList<>();

    public Product(Category category, Brand brand, String name, String description, BigDecimal price, int stockQuantity) {
        this.category = category;
        this.brand = brand;
        this.name = name;
        this.description = description;
        this.price = price;
        this.stockQuantity = stockQuantity;
    }
}
