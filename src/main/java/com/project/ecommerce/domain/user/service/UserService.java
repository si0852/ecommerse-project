package com.project.ecommerce.domain.user.service;

import com.project.ecommerce.domain.dto.user.request.SignInRequestDto;
import com.project.ecommerce.domain.dto.user.request.SignUpRequestDto;
import com.project.ecommerce.domain.dto.user.response.SignInResponseDto;

public interface UserService {

    void SignUp(SignUpRequestDto dto);

    SignInResponseDto SignIn(SignInRequestDto dto);
}
