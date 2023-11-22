package com.ecommerce.ecommerce.service;

import com.ecommerce.ecommerce.model.Brand;

import java.util.List;

public interface BrandService {
    Brand createBrand(String brandName);
    List<Brand> getAllBrands();
    void updateBrand(int id, String brandName);
    void deleteBrand(int brandId);
    Brand findBrandByName(String name);
}
