package red.coder.forecast_api.api.dto.external;

public record ExternalBlastSyncResultDTO(
    int fetched,
    int created,
    int updated,
    int unchanged
) {
}
