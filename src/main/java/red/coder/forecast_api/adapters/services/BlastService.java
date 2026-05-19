package red.coder.forecast_api.adapters.services;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.AllArgsConstructor;
import red.coder.forecast_api.adapters.repositories.internal.BlastRepository;
import red.coder.forecast_api.api.dto.external.ExternalBlastSyncResultDTO;
import red.coder.forecast_api.domain.entity.Blast;

@Service
@AllArgsConstructor
public class BlastService {

    private final ExternalBlastService externalBlastService;
    private final BlastRepository blastRepository;

    @Transactional
    public ExternalBlastSyncResultDTO syncExternalBlasts() {
        List<Blast> externalBlasts = externalBlastService.readAll();
        List<Blast> blastsToSave = new ArrayList<>();
        int created = 0;
        int updated = 0;
        int unchanged = 0;
        OffsetDateTime syncedAt = OffsetDateTime.now();

        for (Blast externalBlast : externalBlasts) {
            Blast localBlast = blastRepository.findByBlastCode(externalBlast.getBlastCode()).orElse(null);

            if (localBlast == null) {
                externalBlast.setLastSyncedAt(syncedAt);
                blastsToSave.add(externalBlast);
                created++;
                continue;
            }

            if (hasChanges(localBlast, externalBlast)) {
                updateExternalFields(localBlast, externalBlast, syncedAt);
                blastsToSave.add(localBlast);
                updated++;
                continue;
            }

            unchanged++;
        }

        if (!blastsToSave.isEmpty()) {
            blastRepository.saveAll(blastsToSave);
        }

        return new ExternalBlastSyncResultDTO(externalBlasts.size(), created, updated, unchanged);
    }

    private boolean hasChanges(Blast localBlast, Blast externalBlast) {
        return !sameText(localBlast.getLocation(), externalBlast.getLocation())
            || !sameNumber(localBlast.getDesignHoles(), externalBlast.getDesignHoles())
            || !sameNumber(localBlast.getRealHoles(), externalBlast.getRealHoles())
            || !sameDecimal(localBlast.getTotalDesignDrilledMeters(), externalBlast.getTotalDesignDrilledMeters())
            || !sameDecimal(localBlast.getTotalRealDrilledMeters(), externalBlast.getTotalRealDrilledMeters())
            || !sameDecimal(localBlast.getDesignEmulsion(), externalBlast.getDesignEmulsion())
            || !sameDecimal(localBlast.getRealEmulsion(), externalBlast.getRealEmulsion())
            || !sameNumber(localBlast.getP337(), externalBlast.getP337())
            || !sameNumber(localBlast.getIkon15m(), externalBlast.getIkon15m());
    }

    private void updateExternalFields(Blast localBlast, Blast externalBlast, OffsetDateTime syncedAt) {
        localBlast.setLocation(externalBlast.getLocation());
        localBlast.setDesignHoles(externalBlast.getDesignHoles());
        localBlast.setRealHoles(externalBlast.getRealHoles());
        localBlast.setTotalDesignDrilledMeters(externalBlast.getTotalDesignDrilledMeters());
        localBlast.setTotalRealDrilledMeters(externalBlast.getTotalRealDrilledMeters());
        localBlast.setDesignEmulsion(externalBlast.getDesignEmulsion());
        localBlast.setRealEmulsion(externalBlast.getRealEmulsion());
        localBlast.setP337(externalBlast.getP337());
        localBlast.setIkon15m(externalBlast.getIkon15m());
        localBlast.setLastSyncedAt(syncedAt);
    }

    private boolean sameText(String left, String right) {
        return left == null ? right == null : left.equals(right);
    }

    private boolean sameNumber(Integer left, Integer right) {
        return left == null ? right == null : left.equals(right);
    }

    private boolean sameDecimal(BigDecimal left, BigDecimal right) {
        if (left == null || right == null) {
            return left == null && right == null;
        }
        return left.compareTo(right) == 0;
    }
}
