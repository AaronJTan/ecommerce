package com.ecommerce.ecommerce.repository;

import com.ecommerce.ecommerce.model.Category;
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
public interface CategoryRepository extends CrudRepository<Category, Integer> {
    List<Category> findAll();

    @Modifying
    @Query("UPDATE Category c SET c.name = :name WHERE c.id = :id")
    int updateCategoryNameById(@Param("id") int id, @Param("name") String name);

    @Modifying
    @Query("DELETE FROM Category c WHERE c.id = :id")
    int deleteById(@Param("id") int id);

     Optional<Category> findByName(String name);
}
