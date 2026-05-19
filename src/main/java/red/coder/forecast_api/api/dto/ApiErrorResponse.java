package red.coder.forecast_api.api.dto;

public record ApiErrorResponse(
    String message,
    int status,
    String detail
) {
}
