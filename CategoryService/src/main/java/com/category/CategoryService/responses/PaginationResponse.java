package com.category.CategoryService.responses;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class PaginationResponse <T>{
    private int currentPage;
    private int pageSize;
    private int totalRecords;
    private int totalPages;
    private boolean hasNextPage;
    private List<T> data;
}
