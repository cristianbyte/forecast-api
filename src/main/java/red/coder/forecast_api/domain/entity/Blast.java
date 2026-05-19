package red.coder.forecast_api.domain.entity;

import lombok.NoArgsConstructor;
import lombok.Setter;
import red.coder.forecast_api.domain.enums.BlastStatus;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "blasts")
@AllArgsConstructor
public class Blast {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(nullable = false, length = 20)
    private String location;

    @Column(name = "sub_location", length = 50)
    private String subLocation;

    private LocalDate date;

    // Período de cobro. Formato: YYYY-MM.
    @Column(length = 7)
    private String period;

    /**
     * DRAFT     → cargado, pendiente de validación
     * CONFIRMED → validado por el responsable
     * BILLED    → incluido en un cobro mensual
     */
    @Column(nullable = false, length = 20)
    private BlastStatus status = BlastStatus.DRAFT;

     // EXTERNO
    @Column(name = "blast_code", nullable = false, length = 50, unique = true)
    private String blastCode;

    // =========================
    // TOPOGRAFÍA
    // =========================

    // Area de voladura - USUARIO
    @Column(name = "blast_area", precision = 12, scale = 3)
    private BigDecimal blastArea;

    // Area de mantos - USUARIO
    @Column(name = "seam_area", precision = 12, scale = 3)
    private BigDecimal seamArea;

    // Area esteril - CALCULADO = blastArea - seamArea
    @Column(name = "sterile_area", precision = 12, scale = 3)
    private BigDecimal sterileArea;

    // =========================
    // POZOS / DISEÑO / REAL
    // =========================

    // EXTERNO
    @Column(name = "design_holes")
    private Integer designHoles;

    // EXTERNO
    @Column(name = "real_holes")
    private Integer realHoles;

    // USUARIO
    @Column(name = "design_burden", precision = 8, scale = 3)
    private BigDecimal designBurden;

    // USUARIO
    @Column(name = "design_spacing", precision = 8, scale = 3)
    private BigDecimal designSpacing;

    //  CALCULADO = designBurden * designSpacing
    @Column(name = "design_area_per_hole", precision = 8, scale = 3)
    private BigDecimal designAreaPerHole;

    // CALCULADO = sterileArea / realHoles
    @Column(name = "real_area_per_hole", precision = 8, scale = 3)
    private BigDecimal realAreaPerHole;

    // CALCULADO = designAreaPerHole - realAreaPerHole
    @Column(name = "hole_area_difference", precision = 14, scale = 3)
    private BigDecimal holeAreaDifference;

    // CALCULADO = blastArea - sterileArea
    @Column(name = "blast_area_difference", precision = 14, scale = 3)
    private BigDecimal blastAreaDifference;

    // =========================
    // LONGITUDES / METROS
    // =========================

    // USUARIO
    @Column(name = "design_average_length", precision = 8, scale = 3)
    private BigDecimal designAverageLength;

    // CALCULADO  = realDrilledMeters / realHoles
    @Column(name = "real_average_length", precision = 8, scale = 3)
    private BigDecimal realAverageLength;

    // CALCULADO = designAverageLength - realAverageLength
    @Column(name = "average_length_difference", precision = 8, scale = 3)
    private BigDecimal averageLengthDifference;

    // CALCULADO = designHoles * designAverageLength
    @Column(name = "design_drilled_meters", precision = 12, scale = 3)
    private BigDecimal totalDesignDrilledMeters;

    // EXTERNO
    @Column(name = "real_drilled_meters", precision = 12, scale = 3)
    private BigDecimal totalRealDrilledMeters;

    // CALCULADO = designDrilledMeters - realDrilledMeters
    @Column(name = "drilled_meters_difference", precision = 12, scale = 3)
    private BigDecimal drilledMetersDifference;

    // =========================
    // VOLÚMENES A&C
    // =========================

    // CALCULADO = sterileArea * designAverageLength
    @Column(name = "design_blast_volume_without_seams", precision = 12, scale = 3)
    private BigDecimal designBlastVolumeWithoutSeams;

    // CALCULADO = sterileArea * realAverageLength
    @Column(name = "real_blast_volume_without_seams", precision = 12, scale = 3)
    private BigDecimal realBlastVolumeWithoutSeams;

    @Column(name = "design_emulsion", precision = 12, scale = 3)
    private BigDecimal designEmulsion;

    // =========================
    // MATERIALES DISEÑO
    // =========================

    // EXTERNO
    @Column(name = "p337")
    private Integer p337;

    // EXTERNO
    @Column(name = "ikon_15m")
    private Integer ikon15m;

    // EXTERNO
    @Column(name = "real_emulsion", precision = 12, scale = 3)
    private BigDecimal realEmulsion;

    // =========================
    // OMC
    // =========================

    // USUARIO
    @Column(name = "omc_average_length", precision = 8, scale = 3)
    private BigDecimal omcAverageLength;

    // CALCULADO = realAverageLength - omcAverageLength
    @Column(name = "ayc_omc_difference", precision = 8, scale = 3)
    private BigDecimal aycOmcDifference;

    // CALCULADO = omc_total_cubic_meters - omc_coal_cubic_meters
    @Column(name = "omc_sterile_cubic_meters", precision = 12, scale = 3)
    private BigDecimal omcSterileCubicMeters;

    // USUARIO
    @Column(name = "omc_coal_cubic_meters", precision = 12, scale = 3)
    private BigDecimal omcCoalCubicMeters;

    // USUARIO
    @Column(name = "omc_total_cubic_meters", precision = 12, scale = 3)
    private BigDecimal omcTotalCubicMeters;

    // CALCULADO = emulsionReal / omcSterileCubicMeters
    @Column(name = "omc_sterile_charge_factor", precision = 12, scale = 3)
    private BigDecimal omcSterileChargeFactor;

    // CALCULADO = omcTotalCubicMeters - realBlastVolumeWithoutSeams
    @Column(name = "omc_ayc_total_volume_difference", precision = 12, scale = 3)
    private BigDecimal omcAycTotalVolumeDifference;

    // =========================
    // FACTORES DE CARGA
    // =========================

    // CALCULADO = emusionDesign / designBlastVolumeWithoutSeams
    @Column(name = "opit_blast_charge_factor", precision = 12, scale = 3)
    private BigDecimal opitBlastChargeFactor;

    // CALCULADO = realEmulsion / realBlastVolumeWithoutSeams
    @Column(name = "real_ayc_charge_factor", precision = 12, scale = 3)
    private BigDecimal realAycChargeFactor;

    // =========================
    // CONTROL LOCAL
    // =========================

    @Column(name = "last_synced_at")
    private OffsetDateTime lastSyncedAt;

    @Column(name = "closed_at")
    private OffsetDateTime closedAt;

    public Blast(String location, String blastCode, Integer designHoles, Integer realHoles, BigDecimal totalRealDrilledMeters, BigDecimal totalDesignDrilledMeters, BigDecimal designEmulsion, BigDecimal realEmulsion, Integer p337, Integer ikon15m
    ) {
        this.location = location;
        this.blastCode = blastCode;
        this.status = BlastStatus.DRAFT;
        this.designHoles = designHoles;
        this.realHoles = realHoles;
        this.totalDesignDrilledMeters = totalDesignDrilledMeters;
        this.totalRealDrilledMeters = totalRealDrilledMeters;
        this.designEmulsion = designEmulsion;
        this.realEmulsion = realEmulsion;
        this.p337 = p337;
        this.ikon15m = ikon15m;
    }

    @PrePersist
    private void prePersist() {
        this.period = toBillingPeriod(this.date);
        this.createdAt = OffsetDateTime.now();
        this.updatedAt = OffsetDateTime.now();
    }
    
    @PreUpdate
    private void preUpdate() {
        this.updatedAt = OffsetDateTime.now();
    }

    @Column(columnDefinition = "TEXT")
    private String notes;

    @Column(name = "created_at", nullable = false, updatable = false)
    private OffsetDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private OffsetDateTime updatedAt;

    // =========================  
    //  MÉTODOS AUXILIARES
    // =========================

    private static final DateTimeFormatter BILLING_PERIOD_FORMATTER =
        DateTimeFormatter.ofPattern("yyyy-MM");

    private String toBillingPeriod(LocalDate date) {
        if (date == null) { return null; }
        return date.format(BILLING_PERIOD_FORMATTER);
    }
}
