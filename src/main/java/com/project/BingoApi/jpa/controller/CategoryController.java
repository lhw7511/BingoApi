package com.project.BingoApi.jpa.controller;


import com.project.BingoApi.jpa.dto.CategoryDto;
import com.project.BingoApi.jpa.service.CategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping("getCategoryList")
    public List<CategoryDto> getCategoryList(){
        long start = System.currentTimeMillis(); // 수행시간 측정
        List<CategoryDto> categoryList = categoryService.getCategoryList();
        long end = System.currentTimeMillis();
        log.info("카테고리 캐시 수행시간 : "+ (end - start)); // 수행시간 logging
        return categoryList;
    }
}
