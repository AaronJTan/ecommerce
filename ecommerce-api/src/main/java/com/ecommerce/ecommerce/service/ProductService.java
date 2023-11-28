package com.ecommerce.ecommerce.service;

import com.ecommerce.ecommerce.payload.request.ProductRequestDTO;
import com.ecommerce.ecommerce.payload.response.ProductResponseDTO;
import com.ecommerce.ecommerce.pojo.FileResource;
import com.ecommerce.ecommerce.payload.response.ProductPageDTOProjection;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ProductService {
    ProductResponseDTO createProduct(ProductRequestDTO productDTO);
    void uploadProductImages(List<MultipartFile> files, int productId);
    FileResource getImage(String filename);
    ProductResponseDTO getProductResponseDTOById(int productId);
    List<ProductPageDTOProjection> getProducts(String brandName, String categoryName);
    ProductResponseDTO updateProduct(int productId, ProductRequestDTO productDTO);
    void deleteProduct(int productId) throws IOException;
    void deleteProductImage(String filename) throws IOException;
}