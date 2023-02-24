package com.project.BingoApi.jpa.dto;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.project.BingoApi.jpa.domain.Category;
import com.project.BingoApi.jpa.domain.Restaurant;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.OneToMany;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL) // Null 값인 필드 제외
public class CategoryDto{

    private  String categoryKey;

    private  String categoryName;


    private List<Restaurant> restaurants;


    public CategoryDto(Category category){
        categoryKey = category.getCategoryKey();
        categoryName = category.getCategoryName();
    }
}
