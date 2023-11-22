package com.ecommerce.ecommerce.controller;

import com.ecommerce.ecommerce.model.Category;
import com.ecommerce.ecommerce.payload.request.CategoryRequest;
import com.ecommerce.ecommerce.payload.response.ApiResponse;
import com.ecommerce.ecommerce.payload.response.ResponseEntityBuilder;
import com.ecommerce.ecommerce.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {
    private final CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping()
    public ResponseEntity<ApiResponse> createCategory(@Valid @RequestBody CategoryRequest categoryRequest) {
        Category category = categoryService.createCategory(categoryRequest.getName());

        return new ResponseEntityBuilder()
                .setStatus(HttpStatus.CREATED)
                .setData(category)
                .build();
    }

    @GetMapping()
    public ResponseEntity<ApiResponse> getCategories() {
        List<Category> categories = categoryService.getAllCategories();

        return new ResponseEntityBuilder()
                .setStatus(HttpStatus.OK)
                .setData(categories)
                .build();
    }

    @PatchMapping("/{categoryId}")
    public ResponseEntity<ApiResponse> updateCategory(@PathVariable("categoryId") int categoryId, @RequestBody CategoryRequest categoryRequest) {
        categoryService.updateCategory(categoryId, categoryRequest.getName());

        return new ResponseEntityBuilder()
                .setStatus(HttpStatus.OK)
                .build();
    }

    @DeleteMapping("/{categoryId}")
    public ResponseEntity<ApiResponse> deleteCategory(@PathVariable("categoryId") int categoryId) {
        categoryService.deleteCategory(categoryId);

        return new ResponseEntityBuilder()
                .setStatus(HttpStatus.OK)
                .build();
    }

}
