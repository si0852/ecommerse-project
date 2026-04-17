package com.project.ecommerce.domain.user.controller;

import com.project.ecommerce.common.response.ApiResponse;
import com.project.ecommerce.domain.dto.user.request.SignInRequestDto;
import com.project.ecommerce.domain.dto.user.request.SignUpRequestDto;
import com.project.ecommerce.domain.dto.user.response.SignInResponseDto;
import com.project.ecommerce.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth/api/v1")
@RequiredArgsConstructor
public class UsersController {

    private final UserService userService;

    @PostMapping("/sign-up")
    public ResponseEntity<ApiResponse<Void>> SignUp(@RequestBody SignUpRequestDto dto) {
        userService.SignUp(dto);
        return ResponseEntity.status(201).body(ApiResponse.created("회원가입이 완료되었습니다"));
    }

    @PostMapping("/sign-in")
    public ResponseEntity<ApiResponse<SignInResponseDto>> SignIn(@RequestBody SignInRequestDto dto) {
        return ResponseEntity.status(200).body(ApiResponse.ok(userService.SignIn(dto)));
    }
}
