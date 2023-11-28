package com.ecommerce.ecommerce.repository;

import com.ecommerce.ecommerce.model.Product;
import com.ecommerce.ecommerce.payload.response.ProductPageDTOProjection;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends PagingAndSortingRepository<Product, Integer> {
//    @Query("SELECT p.*, b.name AS brand, c.name AS category FROM Product p " +
//            "JOIN Brand b ON p.brand_id = b.id " +
//            "JOIN Category c ON p.category_id = c.id " +
//            "WHERE p.id = :productId")
//    Optional<ProductResponseDTO> findProductDTOByProductId(@Param("productId") int productId);
//
    @Query(value = "SELECT p.id, c.name AS category, b.name AS brand, p.name, p.description, p.price, p.stock_quantity AS stockQuantity, pi.system_filename as productImage\n" +
            "FROM products p\n" +
            "JOIN categories c ON p.category_id = c.id\n" +
            "JOIN brands b ON p.brand_id = b.id\n" +
            "LEFT JOIN (\n" +
            "   SELECT pi.id, pi.product_id, pi.system_filename\n" +
            "   FROM product_images pi\n" +
            "   WHERE id in (\n" +
            "       SELECT min(id)\n" +
            "       FROM product_images\n" +
            "       WHERE display_order = 1\n" +
            "       GROUP BY product_id)\n" +
            ") pi ON p.id = pi.product_id\n" +
            "WHERE (:brandName = '' OR b.name = :brandName) " +
            "AND (:categoryName = '' OR c.name = :categoryName) " +
            "ORDER BY p.id;", nativeQuery = true)
    List<ProductPageDTOProjection> getProductsAndMainImage(@Param("brandName") String brandName, @Param("categoryName") String categoryName);
}