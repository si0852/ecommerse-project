package com.project.ecommerce.domain.user.service.impl;

import com.project.ecommerce.common.config.security.JwtProvider;
import com.project.ecommerce.common.exception.BusinessException;
import com.project.ecommerce.domain.dto.user.request.SignInRequestDto;
import com.project.ecommerce.domain.dto.user.request.SignUpRequestDto;
import com.project.ecommerce.domain.dto.user.response.SignInResponseDto;
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
    private final JwtProvider jwtProvider;

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

    @Override
    public SignInResponseDto SignIn(SignInRequestDto dto) {

        Users users = userRepository.findByUserId(dto.getUserId()).orElseThrow(() -> BusinessException.notFound("유저가 존재하지 않습니다."));

        if (!encoder.matches(dto.getPassword(), users.getPassword())) {
            throw BusinessException.badRequest("비밀번호가 일치하지 않습니다.");
        }

        String accessToken = jwtProvider.createAccessToken(users.getUserId(), users.getRoles());

        return SignInResponseDto.builder().token(accessToken).build();
    }
}
