package com.project.ecommerce.domain.user.service;

import com.project.ecommerce.presentation.user.dto.request.SignInRequestDto;
import com.project.ecommerce.presentation.user.dto.request.SignUpRequestDto;
import com.project.ecommerce.presentation.user.dto.response.SignInResponseDto;

public interface UserService {

    void SignUp(SignUpRequestDto dto);

    SignInResponseDto SignIn(SignInRequestDto dto);
}
