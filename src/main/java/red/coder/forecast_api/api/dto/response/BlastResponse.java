package red.coder.forecast_api.api.dto.response;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.OffsetDateTime;

import red.coder.forecast_api.domain.enums.BlastStatus;

public record BlastResponse(
    String location,
    String subLocation,
    LocalDate date,
    String period,
    BlastStatus status,
    String blastCode,
    BigDecimal blastArea,
    BigDecimal seamArea,
    BigDecimal sterileArea,
    Integer designHoles,
    Integer realHoles,
    BigDecimal designBurden,
    BigDecimal designSpacing,
    BigDecimal designAreaPerHole,
    BigDecimal realAreaPerHole,
    BigDecimal holeAreaDifference,
    BigDecimal blastAreaDifference,
    BigDecimal designAverageLength,
    BigDecimal realAverageLength,
    BigDecimal averageLengthDifference,
    BigDecimal totalDesignDrilledMeters,
    BigDecimal totalRealDrilledMeters,
    BigDecimal drilledMetersDifference,
    BigDecimal designBlastVolumeWithoutSeams,
    BigDecimal realBlastVolumeWithoutSeams,
    BigDecimal designEmulsion,
    Integer p337,
    Integer ikon15m,
    BigDecimal realEmulsion,
    BigDecimal omcAverageLength,
    BigDecimal aycOmcDifference,
    BigDecimal omcSterileCubicMeters,
    BigDecimal omcCoalCubicMeters,
    BigDecimal omcTotalCubicMeters,
    BigDecimal omcSterileChargeFactor,
    BigDecimal omcAycTotalVolumeDifference,
    BigDecimal opitBlastChargeFactor,
    BigDecimal realAycChargeFactor,
    OffsetDateTime lastSyncedAt,
    OffsetDateTime closedAt,
    String notes,
    OffsetDateTime createdAt,
    OffsetDateTime updatedAt
) {
}