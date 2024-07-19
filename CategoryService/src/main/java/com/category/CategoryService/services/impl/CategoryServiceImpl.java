package com.category.CategoryService.services.impl;

import com.category.CategoryService.entities.Category;
import com.category.CategoryService.exception.ResourceNotFoundException;
import com.category.CategoryService.payload.CategoryDto;
import com.category.CategoryService.repositories.CategoryRepo;
import com.category.CategoryService.responses.PaginationResponse;
import com.category.CategoryService.services.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepo categoryRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public PaginationResponse<CategoryDto> getAllCategories(int pageNumber, int pageSize, String sortBy, String sortOrder) {
        Pageable pageable = null;
        if (sortBy.equalsIgnoreCase("asc")) {
            pageable = PageRequest.of(pageNumber, pageSize, Sort.by(sortBy).ascending());
        } else {
            pageable = PageRequest.of(pageNumber, pageSize, Sort.by(sortBy).descending());
        }
        Page<Category> findCategory = categoryRepo.findAll(pageable);
        PaginationResponse<CategoryDto> categories = new PaginationResponse<>(
                findCategory.getNumber(),
                findCategory.getSize(),
                findCategory.getNumberOfElements(),
                findCategory.getTotalPages(),
                findCategory.hasNext(),
                findCategory.stream().map((user -> modelMapper.map(user, CategoryDto.class))).collect(Collectors.toList())
        );

        return categories;
    }

    @Override
    public CategoryDto getCategoryById(long id) {
        Optional<Category> category = Optional.ofNullable(categoryRepo.findById((int) id).orElseThrow(() -> new ResourceNotFoundException("Category", "Id", ""+id)));
            return modelMapper.map(category.get(), CategoryDto.class);
    }

    @Override
    public CategoryDto addCategory(CategoryDto categoryDto) {
        return modelMapper.map(categoryRepo.save(modelMapper.map(categoryDto, Category.class)), CategoryDto.class);
    }

    @Override
    public CategoryDto updateCategory(CategoryDto category, long categoryId) {
        Optional<Category> categoryOptional = Optional.ofNullable(categoryRepo.findById((int) categoryId).orElseThrow(() -> new ResourceNotFoundException("Category", "Id", ""+categoryId)));
        if (categoryOptional.isPresent()) {
            Category categoryToUpdate = categoryOptional.get();
            if (category.getCategoryTitle() != null && category.getCategoryTitle().trim() != "") {
                categoryToUpdate.setCategoryTitle(category.getCategoryTitle());
            }
            if (category.getCategoryDescription() != null && category.getCategoryDescription().trim() != "") {
                categoryToUpdate.setCategoryDescription(category.getCategoryDescription());
            }
            return modelMapper.map(categoryRepo.save(categoryToUpdate), CategoryDto.class);
        }
        return null;
    }

    @Override
    public void deleteCategory(long categoryId) {
        Optional<Category> category = Optional.ofNullable(categoryRepo.findById((int) categoryId).orElseThrow(() -> new ResourceNotFoundException("Category", "Id", ""+categoryId)));
        if (category.isPresent()) {
            categoryRepo.deleteById((int) category.get().getCategoryId());
        }
    }
}
