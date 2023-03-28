package com.project.BingoApi.jpa.repository;

import com.project.BingoApi.jpa.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long>,UserRepositoryCustom{

    User findByEmail(String email);
}
