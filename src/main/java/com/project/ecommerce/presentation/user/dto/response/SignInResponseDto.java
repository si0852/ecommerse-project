package com.project.ecommerce.presentation.user.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SignInResponseDto {

    private String token;
}
