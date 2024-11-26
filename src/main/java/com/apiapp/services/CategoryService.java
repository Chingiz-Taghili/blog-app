package com.apiapp.services;

import com.apiapp.dtos.category.CategoryCreateDto;
import com.apiapp.dtos.category.CategoryDto;
import com.apiapp.dtos.category.CategoryUpdateDto;
import com.apiapp.models.Category;
import com.apiapp.payloads.ApiPayload;
import com.apiapp.payloads.ApiResponse;
import com.apiapp.payloads.results.DataResult;

import java.util.List;

public interface CategoryService {
    ApiPayload<List<CategoryDto>> getAllCategories();

    Category getById(Long id);

    ApiResponse createCategory(CategoryCreateDto categoryCreateDto);

    DataResult<CategoryUpdateDto> getUpdateCategory(Long id);

    ApiResponse updateCategory(Long id, CategoryUpdateDto categoryUpdateDto);

    ApiResponse deleteCategory(Long id);
}
