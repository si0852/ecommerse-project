package com.project.ecommerce.presentation.user.dto.request;

import lombok.Data;

@Data
public class SignInRequestDto {

    private String userId;
    private String password;
}
