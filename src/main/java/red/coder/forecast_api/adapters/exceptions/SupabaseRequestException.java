package red.coder.forecast_api.adapters.exceptions;

public class SupabaseRequestException extends RuntimeException {

    private final int statusCode;
    private final String responseBody;

    public SupabaseRequestException(String message, int statusCode, String responseBody) {
        super(message);
        this.statusCode = statusCode;
        this.responseBody = responseBody;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public String getResponseBody() {
        return responseBody;
    }
}
