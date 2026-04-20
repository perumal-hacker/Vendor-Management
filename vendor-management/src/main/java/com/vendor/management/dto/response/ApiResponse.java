package com.vendor.management.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.time.LocalDateTime;

/**
 * Generic API response wrapper.
 *
 * Standardizes all API responses by including status, message,
 * response data, and timestamp. This ensures consistency across
 * all endpoints.
 *
 * @param <T> type of the response data
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse<T> {

    /**
     * Indicates whether the request was successful.
     */
    private boolean success;

    /**
     * Message describing the result of the API call.
     */
    private String message;

    /**
     * Response payload containing actual data.
     */
    private T data;

    /**
     * Timestamp when the response was generated.
     */
    private LocalDateTime timestamp;

    /**
     * Creates a success response with default message.
     *
     * @param data response payload
     * @param <T>  type of the response data
     * @return ApiResponse with success status and default message
     */
    public static <T> ApiResponse<T> success(T data) {
        return ApiResponse.<T>builder()
                .success(true)
                .message("Operation successful")
                .data(data)
                .timestamp(LocalDateTime.now())
                .build();
    }

    /**
     * Creates a success response with a custom message.
     *
     * @param message custom success message
     * @param data    response payload
     * @param <T>     type of the response data
     * @return ApiResponse with success status and custom message
     */
    public static <T> ApiResponse<T> success(String message, T data) {
        return ApiResponse.<T>builder()
                .success(true)
                .message(message)
                .data(data)
                .timestamp(LocalDateTime.now())
                .build();
    }

    /**
     * Creates an error response.
     *
     * @param message error message describing the failure
     * @param <T>     type of the response data
     * @return ApiResponse with failure status and error message
     */
    public static <T> ApiResponse<T> error(String message) {
        return ApiResponse.<T>builder()
                .success(false)
                .message(message)
                .timestamp(LocalDateTime.now())
                .build();
    }
}