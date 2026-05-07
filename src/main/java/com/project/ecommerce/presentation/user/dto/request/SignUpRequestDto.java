package com.project.ecommerce.presentation.user.dto.request;

import lombok.Data;

@Data
public class SignUpRequestDto {

    private String userId;
    private String password;
    private String userName;
    private String userEmail;
}
