package red.coder.forecast_api.domain.enums;

public enum BlastStatus {
    /** Cargado, pendiente de validación */
    DRAFT,
    /** Validado por el responsable. Listo para cobro. */
    CONFIRMED,
    /** Incluido en un período de cobro mensual */
    BILLED
}
