package com.apiapp.controllers;

import com.apiapp.dtos.category.CategoryCreateDto;
import com.apiapp.dtos.category.CategoryDto;
import com.apiapp.dtos.category.CategoryUpdateDto;
import com.apiapp.payloads.ApiPayload;
import com.apiapp.payloads.ApiResponse;
import com.apiapp.payloads.results.DataResult;
import com.apiapp.services.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/category")
public class CategoryController {
    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public ResponseEntity<ApiPayload<List<CategoryDto>>> getAll() {
        ApiPayload<List<CategoryDto>> categories = categoryService.getAllCategories();
        return new ResponseEntity<>(categories, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ApiResponse> create(@RequestBody CategoryCreateDto categoryCreateDto) {
        ApiResponse response = categoryService.createCategory(categoryCreateDto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/update/{id}")
    public ResponseEntity<DataResult<CategoryUpdateDto>> getUpdateCategory(@PathVariable Long id) {
        DataResult<CategoryUpdateDto> getCategory = categoryService.getUpdateCategory(id);
        return new ResponseEntity<DataResult<CategoryUpdateDto>>(getCategory, HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse> updateCategory(@RequestBody CategoryUpdateDto categoryUpdateDto, @PathVariable Long id) {
        ApiResponse response = categoryService.updateCategory(id, categoryUpdateDto);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/remove/{id}")
    public ResponseEntity<ApiResponse> deleteCategory(@PathVariable Long id) {
        ApiResponse response = categoryService.deleteCategory(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
