package com.project.ecommerce.domain.user.service.impl;

import com.project.ecommerce.common.exception.BusinessException;
import com.project.ecommerce.domain.dto.user.SignUpRequestDto;
import com.project.ecommerce.domain.user.entity.Users;
import com.project.ecommerce.domain.user.repository.UserRepository;
import com.project.ecommerce.domain.user.service.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final PasswordEncoder encoder;
    private final UserRepository userRepository;

    @Transactional
    @Override
    public void SignUp(SignUpRequestDto dto) {

        if (userRepository.existsByUserId(dto.getUserId())) {
            throw BusinessException.alreadyExists("이미 존재하는 아이디입니다.");
        }

        String password = encoder.encode(dto.getPassword());

        Users user = Users.builder().userId(dto.getUserId()).password(password)
                .userName(dto.getUserName())
                .userEmail(dto.getUserEmail())
                .roles(Collections.singletonList("ROLE_USER"))
                .build();

        userRepository.save(user);
    }
}
