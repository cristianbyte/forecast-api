package red.coder.forecast_api.domain.services;

import java.math.BigDecimal;
import java.math.RoundingMode;

import red.coder.forecast_api.domain.entity.Blast;

public class BlastCalculator {
    private static final int SCALE = 3;
    private static final RoundingMode RM = RoundingMode.HALF_UP;
 
    public void calculate(Blast blast) {
        calculateSterileArea(blast);
        calculateDesignAreaPerHole(blast);
        calculateRealAreaPerHole(blast);
        calculateHoleAreaDifference(blast);
        calculateBlastAreaDifference(blast);
        calculateRealAverageLength(blast);
        calculateAverageLengthDifference(blast);
        calculateTotalDesignDrilledMeters(blast);
        calculateDrilledMetersDifference(blast);
        calculateDesignBlastVolumeWithoutSeams(blast);
        calculateRealBlastVolumeWithoutSeams(blast);
        calculateOmcSterileCubicMeters(blast);
        calculateAycOmcDifference(blast);
        calculateOmcSterileChargeFactor(blast);
        calculateOmcAycTotalVolumeDifference(blast);
        calculateOpitBlastChargeFactor(blast);
        calculateRealAycChargeFactor(blast);
    }
 
    // ─────────────────────────────────────────────
    // TOPOGRAFÍA
    // ─────────────────────────────────────────────
 
    // sterileArea = blastArea - seamArea
    private void calculateSterileArea(Blast blast) {
        if (notNull(blast.getBlastArea(), blast.getSeamArea())) {
            blast.setSterileArea(
                blast.getBlastArea().subtract(blast.getSeamArea())
            );
        }
    }
 
    // ─────────────────────────────────────────────
    // POZOS / DISEÑO / REAL
    // ─────────────────────────────────────────────
 
    // designAreaPerHole = designBurden * designSpacing
    private void calculateDesignAreaPerHole(Blast blast) {
        if (notNullNotZero(blast.getDesignBurden(), blast.getDesignSpacing())) {
            blast.setDesignAreaPerHole(
                blast.getDesignBurden().multiply(blast.getDesignSpacing())
                     .setScale(SCALE, RM)
            );
        }
    }
 
    // realAreaPerHole = sterileArea / realHoles
    private void calculateRealAreaPerHole(Blast blast) {
        if (notNullNotZero(blast.getSterileArea()) && notNullNotZero(blast.getRealHoles())) {
            blast.setRealAreaPerHole(
                blast.getSterileArea()
                     .divide(BigDecimal.valueOf(blast.getRealHoles()), SCALE, RM)
            );
        }
    }
 
    // holeAreaDifference = designAreaPerHole - realAreaPerHole
    private void calculateHoleAreaDifference(Blast blast) {
        if (notNull(blast.getDesignAreaPerHole(), blast.getRealAreaPerHole())) {
            blast.setHoleAreaDifference(
                blast.getDesignAreaPerHole().subtract(blast.getRealAreaPerHole())
            );
        }
    }
 
    // blastAreaDifference = blastArea - sterileArea
    private void calculateBlastAreaDifference(Blast blast) {
        if (notNull(blast.getBlastArea(), blast.getSterileArea())) {
            blast.setBlastAreaDifference(
                blast.getBlastArea().subtract(blast.getSterileArea())
            );
        }
    }
 
    // ─────────────────────────────────────────────
    // LONGITUDES / METROS
    // ─────────────────────────────────────────────
 
    // realAverageLength = totalRealDrilledMeters / realHoles
    private void calculateRealAverageLength(Blast blast) {
        if (notNullNotZero(blast.getTotalRealDrilledMeters()) && notNullNotZero(blast.getRealHoles())) {
            blast.setRealAverageLength(
                blast.getTotalRealDrilledMeters()
                     .divide(BigDecimal.valueOf(blast.getRealHoles()), SCALE, RM)
            );
        }
    }
 
    // averageLengthDifference = designAverageLength - realAverageLength
    private void calculateAverageLengthDifference(Blast blast) {
        if (notNull(blast.getDesignAverageLength(), blast.getRealAverageLength())) {
            blast.setAverageLengthDifference(
                blast.getDesignAverageLength().subtract(blast.getRealAverageLength())
            );
        }
    }
 
    // totalDesignDrilledMeters = designHoles * designAverageLength
    private void calculateTotalDesignDrilledMeters(Blast blast) {
        if (notNullNotZero(blast.getDesignAverageLength()) && notNullNotZero(blast.getDesignHoles())) {
            blast.setTotalDesignDrilledMeters(
                blast.getDesignAverageLength()
                     .multiply(BigDecimal.valueOf(blast.getDesignHoles()))
                     .setScale(SCALE, RM)
            );
        }
    }
 
    // drilledMetersDifference = totalDesignDrilledMeters - totalRealDrilledMeters
    private void calculateDrilledMetersDifference(Blast blast) {
        if (notNull(blast.getTotalDesignDrilledMeters(), blast.getTotalRealDrilledMeters())) {
            blast.setDrilledMetersDifference(
                blast.getTotalDesignDrilledMeters().subtract(blast.getTotalRealDrilledMeters())
            );
        }
    }
 
    // ─────────────────────────────────────────────
    // VOLÚMENES A&C
    // ─────────────────────────────────────────────
 
    // designBlastVolumeWithoutSeams = sterileArea * designAverageLength
    private void calculateDesignBlastVolumeWithoutSeams(Blast blast) {
        if (notNullNotZero(blast.getSterileArea(), blast.getDesignAverageLength())) {
            blast.setDesignBlastVolumeWithoutSeams(
                blast.getSterileArea()
                     .multiply(blast.getDesignAverageLength())
                     .setScale(SCALE, RM)
            );
        }
    }
 
    // realBlastVolumeWithoutSeams = sterileArea * realAverageLength
    private void calculateRealBlastVolumeWithoutSeams(Blast blast) {
        if (notNullNotZero(blast.getSterileArea(), blast.getRealAverageLength())) {
            blast.setRealBlastVolumeWithoutSeams(
                blast.getSterileArea()
                     .multiply(blast.getRealAverageLength())
                     .setScale(SCALE, RM)
            );
        }
    }
 
    // ─────────────────────────────────────────────
    // OMC
    // ─────────────────────────────────────────────
 
    // omcSterileCubicMeters = omcTotalCubicMeters - omcCoalCubicMeters
    private void calculateOmcSterileCubicMeters(Blast blast) {
        if (notNull(blast.getOmcTotalCubicMeters(), blast.getOmcCoalCubicMeters())) {
            blast.setOmcSterileCubicMeters(
                blast.getOmcTotalCubicMeters().subtract(blast.getOmcCoalCubicMeters())
            );
        }
    }
 
    // aycOmcDifference = realAverageLength - omcAverageLength
    private void calculateAycOmcDifference(Blast blast) {
        if (notNull(blast.getRealAverageLength(), blast.getOmcAverageLength())) {
            blast.setAycOmcDifference(
                blast.getRealAverageLength().subtract(blast.getOmcAverageLength())
            );
        }
    }
 
    // omcSterileChargeFactor = realEmulsion / omcSterileCubicMeters
    private void calculateOmcSterileChargeFactor(Blast blast) {
        if (notNullNotZero(blast.getRealEmulsion(), blast.getOmcSterileCubicMeters())) {
            blast.setOmcSterileChargeFactor(
                blast.getRealEmulsion()
                     .divide(blast.getOmcSterileCubicMeters(), SCALE, RM)
            );
        }
    }
 
    // omcAycTotalVolumeDifference = omcTotalCubicMeters - realBlastVolumeWithoutSeams
    private void calculateOmcAycTotalVolumeDifference(Blast blast) {
        if (notNull(blast.getOmcTotalCubicMeters(), blast.getRealBlastVolumeWithoutSeams())) {
            blast.setOmcAycTotalVolumeDifference(
                blast.getOmcTotalCubicMeters().subtract(blast.getRealBlastVolumeWithoutSeams())
            );
        }
    }
 
    // ─────────────────────────────────────────────
    // FACTORES DE CARGA
    // ─────────────────────────────────────────────
 
    // opitBlastChargeFactor = designEmulsion / designBlastVolumeWithoutSeams
    private void calculateOpitBlastChargeFactor(Blast blast) {
        if (notNullNotZero(blast.getDesignEmulsion(), blast.getDesignBlastVolumeWithoutSeams())) {
            blast.setOpitBlastChargeFactor(
                blast.getDesignEmulsion()
                     .divide(blast.getDesignBlastVolumeWithoutSeams(), SCALE, RM)
            );
        }
    }
 
    // realAycChargeFactor = realEmulsion / realBlastVolumeWithoutSeams
    private void calculateRealAycChargeFactor(Blast blast) {
        if (notNullNotZero(blast.getRealEmulsion(), blast.getRealBlastVolumeWithoutSeams())) {
            blast.setRealAycChargeFactor(
                blast.getRealEmulsion()
                     .divide(blast.getRealBlastVolumeWithoutSeams(), SCALE, RM)
            );
        }
    }
 
    // ─────────────────────────────────────────────
    // GUARDS
    // ─────────────────────────────────────────────
 
    private boolean notNullNotZero(BigDecimal... values) {
        for (BigDecimal v : values) {
            if (v == null || v.compareTo(BigDecimal.ZERO) <= 0) return false;
        }
        return true;
    }
 
    private boolean notNullNotZero(Integer value) {
        return value != null && value > 0;
    }
 
    private boolean notNull(BigDecimal... values) {
        for (BigDecimal v : values) {
            if (v == null) return false;
        }
        return true;
    }

}
