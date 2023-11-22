package com.ecommerce.ecommerce.payload.response;

import com.ecommerce.ecommerce.payload.ProductDTO;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.math.BigDecimal;

@ToString
@NoArgsConstructor
public class ProductPageDTO extends ProductDTO {
    private int id;
    private String productImage;

    public ProductPageDTO(int id, String category, String brand, String name, String description, BigDecimal price, Integer stockQuantity, String productImage) {
        super(category, brand, name, description, price, stockQuantity);
        this.id = id;
        this.productImage = productImage;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }
}
