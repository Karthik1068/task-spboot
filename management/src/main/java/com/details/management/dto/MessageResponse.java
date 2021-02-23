package com.details.management.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.sun.istack.NotNull;
import lombok.Data;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class MessageResponse {
    public MessageResponse(String message) {
        this.message = message;
    }

    public MessageResponse(String message, String errorCode, String errorDetails) {
        this.message = message;
        this.errorCode = errorCode;
        this.errorDetails = errorDetails;
    }

    @NotNull
    private String message;
    private String errorCode;
    private String errorDetails;
}
