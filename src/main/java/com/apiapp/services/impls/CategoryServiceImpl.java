package com.apiapp.services.impls;

import com.apiapp.dtos.category.CategoryCreateDto;
import com.apiapp.dtos.category.CategoryDto;
import com.apiapp.dtos.category.CategoryUpdateDto;
import com.apiapp.exceptions.ResourceNotFoundException;
import com.apiapp.models.Category;
import com.apiapp.payloads.ApiPayload;
import com.apiapp.payloads.ApiResponse;
import com.apiapp.payloads.results.DataResult;
import com.apiapp.payloads.results.error.ErrorDataResult;
import com.apiapp.payloads.results.success.SuccessDataResult;
import com.apiapp.repositories.CategoryRepository;
import com.apiapp.services.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;

    public CategoryServiceImpl(CategoryRepository categoryRepository, ModelMapper modelMapper) {
        this.categoryRepository = categoryRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public ApiPayload<List<CategoryDto>> getAllCategories() {
        List<Category> findCategories = categoryRepository.findAll();
        List<CategoryDto> categories = findCategories.stream().map(
                category -> modelMapper.map(category, CategoryDto.class)).toList();
        return new ApiPayload<>(true, categories);
    }

    @Override
    public Category getById(Long id) {
        Category findCategory = categoryRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Category", "id", id));
        return findCategory;
    }

    @Override
    public ApiResponse createCategory(CategoryCreateDto categoryCreateDto) {
        try {
            Category findCategory = categoryRepository.findByName(categoryCreateDto.getName());
            if (findCategory != null) {
                return new ApiResponse("Category already exist", false);
            }
            Category category = modelMapper.map(categoryCreateDto, Category.class);
            categoryRepository.save(category);
            return new ApiResponse("Category created successfully", true);
        } catch (Exception e) {
            return new ApiResponse(e.getMessage(), false);
        }
    }

    @Override
    public DataResult<CategoryUpdateDto> getUpdateCategory(Long id) {
        try {
            Category findCategory = categoryRepository.findById(id).orElseThrow(() ->
                    new ResourceNotFoundException("Category", "id", id));
            CategoryUpdateDto updateCategory = modelMapper.map(findCategory, CategoryUpdateDto.class);
            return new SuccessDataResult<>(updateCategory);
        } catch (Exception e) {
            return new ErrorDataResult<>(e.getMessage());
        }
    }

    @Override
    public ApiResponse updateCategory(Long id, CategoryUpdateDto categoryUpdateDto) {
        Category findCategory = categoryRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Category", "id", id));
        Category sameCategory = categoryRepository.findByName(categoryUpdateDto.getName());
        if (sameCategory != null) {
            return new ApiResponse("Category already exist", false);
        }
        findCategory.setName(categoryUpdateDto.getName());
        categoryRepository.save(findCategory);
        return new ApiResponse("Category updated successfully", true);
    }

    @Override
    public ApiResponse deleteCategory(Long id) {
        try {
            Category findCategory = categoryRepository.findById(id).orElseThrow(() ->
                    new ResourceNotFoundException("Category", "id", id));
            categoryRepository.delete(findCategory);
            return new ApiResponse("Category deleted successfully", true);
        } catch (Exception e) {
            return new ApiResponse(e.getMessage(), false);
        }
    }
}
