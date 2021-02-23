package com.details.management.controller;

import com.details.management.dto.MessageResponse;
import com.details.management.exception.DataResourceNotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.springframework.web.util.WebUtils;

import java.util.UUID;

@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    private static final String COLON = "  :  ";

    @ExceptionHandler(DataResourceNotFoundException.class)
    public ResponseEntity<MessageResponse> eventMessageExceptionHandler(final DataResourceNotFoundException ex) {
        logError(ex);
        return new ResponseEntity<>(new MessageResponse(ex.getMessage()), HttpStatus.NOT_FOUND);
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(
            final Exception ex, @Nullable final Object body, final HttpHeaders headers, final HttpStatus status, final WebRequest request) {
        if (HttpStatus.INTERNAL_SERVER_ERROR.equals(status)) {
            request.setAttribute(WebUtils.ERROR_EXCEPTION_ATTRIBUTE, ex, RequestAttributes.SCOPE_REQUEST);
        }
        final String errorRef = logError(ex);
        return new ResponseEntity<>(new MessageResponse(status.getReasonPhrase() + COLON + errorRef), headers, status);
    }

    private String logError(final Exception ex) {
        final String errorRef = "Error Reference: " + UUID.randomUUID();
        logger.error(errorRef, ex);
        return errorRef;
    }

}
