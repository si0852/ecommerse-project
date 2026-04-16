package com.project.ecommerce.domain.user.repository;

import com.project.ecommerce.domain.user.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<Users, Long> {

    Users getByUserId(String userId);
}
