package com.category.CategoryService.controllers;

import com.category.CategoryService.payload.CategoryDto;
import com.category.CategoryService.responses.DeleteApiResponse;
import com.category.CategoryService.responses.PaginationResponse;
import com.category.CategoryService.services.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/category")
public class CategoryController {


    @Autowired
    private CategoryService categoryService;

    @PostMapping
    public ResponseEntity<CategoryDto> addCategory(@Valid @RequestBody CategoryDto categoryDto) {
        return  new ResponseEntity<>(categoryService.addCategory(categoryDto), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<PaginationResponse> getCategories(
            @RequestParam(name = "pageNumber",defaultValue = "0")int pageNumber,
            @RequestParam(name ="pageSize",defaultValue = "10" )int pageSize,
            @RequestParam(name ="sortBy" ,defaultValue = "categoryId")String sortBy,
            @RequestParam(name ="sortOrder" ,defaultValue = "asc")String sortOrder
    ) {
        return new ResponseEntity<>(categoryService.getAllCategories(pageNumber,pageSize,sortBy,sortOrder), HttpStatus.OK);

    }

    @GetMapping(path = "/{categoryId}")
    public ResponseEntity<CategoryDto> getCategory(@PathVariable long categoryId) {
        return new ResponseEntity<>(categoryService.getCategoryById(categoryId), HttpStatus.OK);
    }

    @PutMapping(path = "/{categoryId}")
    public ResponseEntity<CategoryDto> updateCategory(@Valid @PathVariable long categoryId, @RequestBody CategoryDto categoryDto) {
        return new ResponseEntity<>(categoryService.updateCategory(categoryDto, categoryId), HttpStatus.OK);
    }

    @DeleteMapping(path = "/{categoryId}")
    public ResponseEntity<DeleteApiResponse> deleteCategory(@PathVariable long categoryId) {
        categoryService.deleteCategory(categoryId);
        return  new ResponseEntity<>(new DeleteApiResponse("Category Deleted Successfully",true), HttpStatus.OK);
    }


}
