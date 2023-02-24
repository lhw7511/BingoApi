package com.project.BingoApi.jpa.service;

import com.project.BingoApi.jpa.dto.CategoryDto;
import com.project.BingoApi.jpa.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class CategoryService {

    private final CategoryRepository categoryRepository;

    @Cacheable("categoryList")
    public List<CategoryDto> getCategoryList(){
        return categoryRepository.findAll().stream().map(c -> new CategoryDto(c)).collect(Collectors.toList());
    }

}
