package com.project.ecommerce.domain.user.repository;

import com.project.ecommerce.domain.user.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<Users, Long> {

    Users getByUserId(String userId);

    Optional<Users> findByUserId(String userId);

    boolean existsByUserId(String userId);
}
