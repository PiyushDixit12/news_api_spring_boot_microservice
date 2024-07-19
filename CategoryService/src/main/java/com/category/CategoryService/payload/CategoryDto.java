package com.category.CategoryService.payload;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDto {

    private int categoryId;

    @NotNull(message = "please provide a category title")
    @Size(min = 3,max = 50,message = "title must be min 3 and max 50 char")
    private String categoryTitle;

    @NotNull(message = "please provide a category description")
    @Size(min = 4,max = 130,message = "Description must be min 4 and max 130 char")
    private String categoryDescription;
}
