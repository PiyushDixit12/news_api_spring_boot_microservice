package com.category.CategoryService.services;

import com.category.CategoryService.payload.CategoryDto;
import com.category.CategoryService.responses.PaginationResponse;



public interface CategoryService {

     PaginationResponse<CategoryDto> getAllCategories(int pageNumber, int pageSize, String sortBy, String sortOrder);
     CategoryDto getCategoryById(long id);
     CategoryDto addCategory(CategoryDto categoryDto);
     CategoryDto updateCategory(CategoryDto category,long categoryId);
     void deleteCategory(long categoryId);

}
