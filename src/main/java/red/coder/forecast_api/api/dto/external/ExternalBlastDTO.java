package red.coder.forecast_api.api.dto.external;

import java.math.BigDecimal;

public record ExternalBlastDTO(
    String location,
    String blastCode,
    Integer designHoles,
    Integer realHoles,
    BigDecimal totalDesignDrilledMeters,
    BigDecimal totalRealDrilledMeters,
    BigDecimal designEmulsion,
    BigDecimal realEmulsion,
    Integer p337,
    Integer ikon15m
) {
}