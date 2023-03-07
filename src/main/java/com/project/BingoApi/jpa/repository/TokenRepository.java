package com.project.BingoApi.jpa.repository;

import com.project.BingoApi.jpa.domain.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TokenRepository extends JpaRepository<RefreshToken,Long> {

    RefreshToken findByUserId(Long id);
}
