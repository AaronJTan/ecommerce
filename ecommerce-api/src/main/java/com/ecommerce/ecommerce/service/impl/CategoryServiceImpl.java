package com.ecommerce.ecommerce.service.impl;

import com.ecommerce.ecommerce.exception.AlreadyExistsException;
import com.ecommerce.ecommerce.exception.ConstraintViolationException;
import com.ecommerce.ecommerce.exception.DoesNotExistException;
import com.ecommerce.ecommerce.model.Category;
import com.ecommerce.ecommerce.repository.CategoryRepository;
import com.ecommerce.ecommerce.service.CategoryService;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public Category createCategory(String categoryName) {
        if (categoryRepository.findByName(categoryName).isPresent()) {
            throw new AlreadyExistsException("Category already exists");
        }

        return categoryRepository.save(new Category(categoryName));
    }

    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    public void updateCategory(int id, String categoryName) {
        Optional<Category> category = categoryRepository.findByName(categoryName);

        if (category.isPresent() && category.get().getId() != id) {
            throw new AlreadyExistsException("Category already exists");
        }

        int rowsUpdated = categoryRepository.updateCategoryNameById(id, categoryName);

        if (rowsUpdated == 0) {
            throw new DoesNotExistException("Category does not exist");
        }
    }

    public void deleteCategory(int categoryId) {
        try {
            int rowsDeleted = categoryRepository.deleteById(categoryId);

            if (rowsDeleted == 0) {
                throw new DoesNotExistException("Category does not exist");
            }
        } catch (DataIntegrityViolationException e) {
            throw new ConstraintViolationException("Can't delete category. There are product(s) under this category.");
        }
    }

    public Category findCategoryByName(String name) {
        return categoryRepository
                .findByName(name)
                .orElseThrow(() -> new DoesNotExistException("Category does not exist"));
    }
}
