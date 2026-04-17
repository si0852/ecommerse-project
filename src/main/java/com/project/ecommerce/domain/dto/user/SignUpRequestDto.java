package com.project.ecommerce.domain.dto.user;

import lombok.Data;

@Data
public class SignUpRequestDto {

    private String userId;
    private String password;
    private String userName;
    private String userEmail;
}
