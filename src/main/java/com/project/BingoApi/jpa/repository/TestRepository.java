package com.project.BingoApi.jpa.repository;

import com.project.BingoApi.jpa.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TestRepository extends JpaRepository<Member,Long> {
}
