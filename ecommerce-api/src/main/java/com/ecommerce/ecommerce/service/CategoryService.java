package com.ecommerce.ecommerce.service;

import com.ecommerce.ecommerce.model.Category;

import java.util.List;

public interface CategoryService {
    Category createCategory(String categoryName);
    List<Category> getAllCategories();
    void updateCategory(int id, String categoryName);
    void deleteCategory(int categoryId);
    Category findCategoryByName(String name);
}
