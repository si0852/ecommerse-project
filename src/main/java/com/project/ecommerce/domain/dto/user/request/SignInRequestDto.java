package com.project.ecommerce.domain.dto.user.request;

import lombok.Data;

@Data
public class SignInRequestDto {

    private String userId;
    private String password;
}
