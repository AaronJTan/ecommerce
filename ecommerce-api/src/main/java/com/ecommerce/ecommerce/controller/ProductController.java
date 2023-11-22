package com.ecommerce.ecommerce.controller;

import com.ecommerce.ecommerce.payload.request.ProductRequestDTO;
import com.ecommerce.ecommerce.payload.response.ApiResponse;
import com.ecommerce.ecommerce.payload.response.ProductResponseDTO;
import com.ecommerce.ecommerce.payload.response.ResponseEntityBuilder;
import com.ecommerce.ecommerce.pojo.FileResource;
import com.ecommerce.ecommerce.payload.response.ProductPageDTOProjection;
import com.ecommerce.ecommerce.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {
    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping()
    public ResponseEntity<ApiResponse> createProduct(@Valid @RequestBody ProductRequestDTO productRequest) {
        ProductResponseDTO product = productService.createProduct(productRequest);

        return new ResponseEntityBuilder()
                .setStatus(HttpStatus.CREATED)
                .setData(product)
                .build();
    }

    @PostMapping("/{productId}/image/upload")
    public ResponseEntity<ApiResponse> uploadImages(@RequestParam(name = "file[]") List<MultipartFile> files, @PathVariable("productId") int productId) {
        productService.uploadProductImages(files, productId);

        return new ResponseEntityBuilder()
                .setStatus(HttpStatus.OK)
                .build();
    }

    @GetMapping("/image/{filename}")
    public ResponseEntity<Resource> viewImage(@PathVariable("filename") String filename) {
        FileResource file = productService.getImage(filename);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "inline; filename=\"" + file.getOriginalFilename() + "\"")
                .contentType(file.getMimetype())
                .body(file.getResource());
    }

    @GetMapping("/{productId}")
    public ResponseEntity<ApiResponse> getProductById(@PathVariable("productId") int productId) {
        ProductResponseDTO product = productService.getProductResponseDTOById(productId);

        return new ResponseEntityBuilder()
                .setStatus(HttpStatus.OK)
                .setData(product)
                .build();
    }

    @GetMapping()
    public ResponseEntity<ApiResponse> getProducts(@RequestParam(name = "brand", required = false, defaultValue = "") String brand,
                                                   @RequestParam(name = "category", required = false, defaultValue = "") String category) {
        List<ProductPageDTOProjection> products = productService.getProducts(brand, category);

        return new ResponseEntityBuilder()
                .setStatus(HttpStatus.OK)
                .setData(products)
                .build();
    }

    @PutMapping("/{productId}")
    public ResponseEntity<ApiResponse> updateProduct(@PathVariable("productId") int productId, @Valid @RequestBody ProductRequestDTO productRequest) {
        ProductResponseDTO product = productService.updateProduct(productId, productRequest);

        return new ResponseEntityBuilder()
                .setStatus(HttpStatus.OK)
                .setData(product)
                .build();
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<ApiResponse> deleteProduct(@PathVariable("productId") int productId) throws IOException {
        productService.deleteProduct(productId);

        return new ResponseEntityBuilder()
                .setStatus(HttpStatus.OK)
                .build();
    }

    @DeleteMapping("/image/{filename}")
    public ResponseEntity<ApiResponse> deleteProductImage(@PathVariable("filename") String filename) throws IOException {
        productService.deleteProductImage(filename);

        return new ResponseEntityBuilder()
                .setStatus(HttpStatus.OK)
                .build();
    }
}
