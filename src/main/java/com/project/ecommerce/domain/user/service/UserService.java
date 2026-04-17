package com.project.ecommerce.domain.user.service;

import com.project.ecommerce.domain.dto.user.SignUpRequestDto;

public interface UserService {

    void SignUp(SignUpRequestDto dto);
}
