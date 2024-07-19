package com.posts.PostService.payload;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDto {

    private int categoryId;
  private String categoryTitle;

   private String categoryDescription;
}
