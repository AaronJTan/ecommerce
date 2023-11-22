package com.ecommerce.ecommerce.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public abstract class ProductDTO {

    @NotBlank(message = "Category name is required")
    private String category;

    @NotBlank(message = "Brand name is required")
    private String brand;

    @NotBlank(message = "Product name is required")
    private String name;

    @NotBlank(message = "Product description is required")
    private String description;

    @NotNull(message = "Price > $0 is required")
    @DecimalMin(value = "0.0", inclusive = true, message = "Price > $0 is required")
    private BigDecimal price;

    @NotNull(message = "Stock quantity > 0 is required")
    @Min(value=0, message="Stock quantity > 0 is required")
    private Integer stockQuantity;
}
