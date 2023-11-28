package com.ecommerce.ecommerce.repository;

import com.ecommerce.ecommerce.model.Brand;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Transactional
@Repository
public interface BrandRepository extends CrudRepository<Brand, Integer> {
    List<Brand> findAll();

    @Modifying
    @Query("UPDATE Brand b SET b.name = :name WHERE b.id = :id")
    int updateBrandNameById(@Param("id") int id, @Param("name") String name);

    @Modifying
    @Query("DELETE FROM Brand b WHERE b.id = :id")
    int deleteById(@Param("id") int id);

    Optional<Brand> findByName(String name);
}
