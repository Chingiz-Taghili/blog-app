package com.apiapp.dtos.article;

import com.apiapp.dtos.category.CategoryDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArticleDetailDto {
    private Long id;
    private String title;
    private String description;
    private LocalDate createdDate;
    private LocalDate updateDate;
    private CategoryDto category;
}
