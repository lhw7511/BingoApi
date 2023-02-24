package com.project.BingoApi.jpa.repository;

import com.project.BingoApi.jpa.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category,String> {
}
