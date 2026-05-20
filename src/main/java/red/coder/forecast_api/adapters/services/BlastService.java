package red.coder.forecast_api.adapters.services;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.AllArgsConstructor;
import red.coder.forecast_api.adapters.abstract_services.IBlastService;
import red.coder.forecast_api.adapters.mappers.ExternalBlastMapper;
import red.coder.forecast_api.adapters.repositories.external.ExternalBlastRepository;
import red.coder.forecast_api.adapters.repositories.internal.BlastRepository;
import red.coder.forecast_api.api.dto.external.ExternalBlastSyncResultDTO;
import red.coder.forecast_api.api.dto.response.BlastResponse;
import red.coder.forecast_api.domain.entity.Blast;

@Service
@AllArgsConstructor
public class BlastService implements IBlastService {

    private final ExternalBlastRepository externalBlastRepository;
    private final ExternalBlastMapper externalBlastMapper;
    private final BlastRepository blastRepository;

    @Override
    public List<BlastResponse> readAll() {
        return blastRepository.findAll().stream()
            .map(externalBlastMapper::blastToResponse)
            .toList();
    }

    @Override
    @Transactional
    public ExternalBlastSyncResultDTO syncExternalBlasts() {
        List<Blast> externalBlasts = externalBlastRepository.findAll().stream()
            .map(externalBlastMapper::requestToBlast)
            .toList();
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

    private boolean hasChanges(Blast local, Blast external) {
        return !Objects.equals(local.getDataHash(), external.getDataHash());
    }

    private void updateExternalFields(Blast localBlast, Blast externalBlast, OffsetDateTime syncedAt) {
        localBlast.setDataHash(externalBlast.getDataHash());
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

}
