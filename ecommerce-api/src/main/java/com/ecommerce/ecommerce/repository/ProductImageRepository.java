package com.ecommerce.ecommerce.repository;

import com.ecommerce.ecommerce.model.ProductImage;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Transactional
@Repository
public interface ProductImageRepository extends PagingAndSortingRepository<ProductImage, Integer> {
    @Query("SELECT originalFilename FROM ProductImage pi WHERE pi.systemFilename = :systemFilename")
    Optional<String> findOriginalFilenameBySystemFilename(@Param("systemFilename") String systemFilename);

    Optional<ProductImage> findBySystemFilename(String systemFilename);

    @Query("SELECT pi.systemFilename FROM ProductImage pi WHERE pi.product.id = :productId")
    List<String> findSystemFilenamesByProductId(@Param("productId") int productId);

    @Modifying
    @Query("DELETE FROM ProductImage pi WHERE pi.systemFilename = :systemFilename")
    int deleteBySystemFilename(@Param("systemFilename") String systemFilename);
}
