package com.ecommerce.ecommerce.payload.response;

import java.math.BigDecimal;

public interface ProductPageDTOProjection {
    Long getId();
    String getCategory();
    String getBrand();
    String getName();
    String getDescription();
    BigDecimal getPrice();
    int getStockQuantity();
    String getProductImage();
}