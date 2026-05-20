package red.coder.forecast_api.api.controller;

import java.util.Arrays;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
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

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ApiErrorResponse> handleConstraintViolationException(ConstraintViolationException exception) {
        String detail = exception.getConstraintViolations().stream()
            .map(this::formatConstraintViolation)
            .collect(Collectors.joining("; "));
        ApiErrorResponse errorResponse = new ApiErrorResponse(
            "Invalid request parameter.",
            HttpStatus.BAD_REQUEST.value(),
            detail);

        return ResponseEntity.badRequest().body(errorResponse);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ApiErrorResponse> handleMethodArgumentTypeMismatchException(
            MethodArgumentTypeMismatchException exception) {
        ApiErrorResponse errorResponse = new ApiErrorResponse(
            "Invalid request parameter.",
            HttpStatus.BAD_REQUEST.value(),
            formatTypeMismatch(exception));

        return ResponseEntity.badRequest().body(errorResponse);
    }

    private String formatConstraintViolation(ConstraintViolation<?> violation) {
        String parameter = violation.getPropertyPath().toString();
        int lastDotIndex = parameter.lastIndexOf('.');
        if (lastDotIndex >= 0) {
            parameter = parameter.substring(lastDotIndex + 1);
        }

        return "Parameter '" + parameter + "' " + violation.getMessage() + ".";
    }

    private String formatTypeMismatch(MethodArgumentTypeMismatchException exception) {
        Class<?> requiredType = exception.getRequiredType();
        if (requiredType != null && requiredType.isEnum()) {
            String allowedValues = Arrays.stream(requiredType.getEnumConstants())
                .map(Object::toString)
                .collect(Collectors.joining(", "));

            return "Parameter '" + exception.getName() + "' must be one of: " + allowedValues + ".";
        }

        return "Parameter '" + exception.getName() + "' has invalid value '" + exception.getValue() + "'.";
    }
}
