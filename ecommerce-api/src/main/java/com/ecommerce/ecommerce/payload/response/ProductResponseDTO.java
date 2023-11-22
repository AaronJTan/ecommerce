package com.ecommerce.ecommerce.payload.response;

import com.ecommerce.ecommerce.payload.ProductDTO;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.List;

@NoArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class ProductResponseDTO extends ProductDTO {
    private int id;
    private List<String> productImages;

    public ProductResponseDTO(int id, String category, String brand, String name, String description, BigDecimal price, Integer stockQuantity, List<String> productImages) {
        super(category, brand, name, description, price, stockQuantity);
        this.id = id;
        this.productImages = productImages;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<String> getProductImages() {
        return productImages;
    }

    public void setProductImages(List<String> productImages) {
        this.productImages = productImages;
    }
}
