package com.ecommerce.ecommerce.payload.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BrandRequest {
    @NotBlank(message = "Brand name is required")
    private String name;
}
