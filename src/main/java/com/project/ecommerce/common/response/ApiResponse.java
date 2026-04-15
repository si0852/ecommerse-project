package com.project.ecommerce.common.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse<T> {

    private int status;
    private String message;
    private T data;

    public static <T> ApiResponse<T> ok(T data) {
        return ApiResponse.<T>builder()
                .status(200)
                .message("success")
                .data(data)
                .build();
    }

    public static ApiResponse<Void> ok() {
        return ApiResponse.<Void>builder()
                .status(200)
                .message("success")
                .build();
    }

    public static <T> ApiResponse<T> created(T data) {
        return ApiResponse.<T>builder()
                .status(201)
                .message("created")
                .data(data)
                .build();
    }

    public static ApiResponse<Void> error(int status, String message) {
        return ApiResponse.<Void>builder()
                .status(status)
                .message(message)
                .build();
    }
}
