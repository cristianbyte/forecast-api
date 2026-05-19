package red.coder.forecast_api.api.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import red.coder.forecast_api.adapters.exceptions.SupabaseRequestException;
import red.coder.forecast_api.api.dto.response.ApiErrorResponse;

@RestControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(SupabaseRequestException.class)
    public ResponseEntity<ApiErrorResponse> handleSupabaseRequestException(SupabaseRequestException exception) {
        ApiErrorResponse errorResponse = new ApiErrorResponse(
            exception.getMessage(),
            HttpStatus.BAD_GATEWAY.value(),
            exception.getResponseBody());

        return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(errorResponse);
    }
}
