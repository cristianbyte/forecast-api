package red.coder.forecast_api.api.dto.response;

public record ApiErrorResponse(
    String message,
    int status,
    String detail
) {
}
