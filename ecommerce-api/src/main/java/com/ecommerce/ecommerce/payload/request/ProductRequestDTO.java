package com.ecommerce.ecommerce.payload.request;

import com.ecommerce.ecommerce.payload.ProductDTO;

import java.math.BigDecimal;

public class ProductRequestDTO extends ProductDTO {
    public ProductRequestDTO(String category, String brand, String name, String description, BigDecimal price, Integer stockQuantity) {
        super(category, brand, name, description, price, stockQuantity);
    }
}
