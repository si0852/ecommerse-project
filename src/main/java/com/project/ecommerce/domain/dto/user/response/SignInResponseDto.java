package com.project.ecommerce.domain.dto.user.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SignInResponseDto {

    private String token;
}
