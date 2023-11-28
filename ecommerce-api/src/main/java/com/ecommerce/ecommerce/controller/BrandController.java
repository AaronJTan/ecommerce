package com.ecommerce.ecommerce.controller;

import com.ecommerce.ecommerce.model.Brand;
import com.ecommerce.ecommerce.payload.request.BrandRequest;
import com.ecommerce.ecommerce.payload.response.ApiResponse;
import com.ecommerce.ecommerce.payload.response.ResponseEntityBuilder;
import com.ecommerce.ecommerce.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/brands")
public class BrandController {
    private final BrandService brandService;

    @Autowired
    public BrandController(BrandService brandService) {
        this.brandService = brandService;
    }

    @PostMapping()
    public ResponseEntity<ApiResponse> createBrand(@Valid @RequestBody BrandRequest brandRequest) {
        Brand brand = brandService.createBrand(brandRequest.getName());

        return new ResponseEntityBuilder()
                .setStatus(HttpStatus.CREATED)
                .setData(brand)
                .build();
    }

    @GetMapping()
    public ResponseEntity<ApiResponse> getBrands() {
        List<Brand> brands = brandService.getAllBrands();

        return new ResponseEntityBuilder()
                .setStatus(HttpStatus.OK)
                .setData(brands)
                .build();
    }

    @PatchMapping("/{brandId}")
    public ResponseEntity<ApiResponse> updateBrand(@PathVariable("brandId") int brandId, @RequestBody BrandRequest brandRequest) {
        brandService.updateBrand(brandId, brandRequest.getName());

        return new ResponseEntityBuilder()
                .setStatus(HttpStatus.OK)
                .build();
    }

    @DeleteMapping("/{brandId}")
    public ResponseEntity<ApiResponse> deleteBrand(@PathVariable("brandId") int brandId) {
        brandService.deleteBrand(brandId);

        return new ResponseEntityBuilder()
                .setStatus(HttpStatus.OK)
                .build();
    }

}
