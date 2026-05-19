package red.coder.forecast_api.adapters.services;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import red.coder.forecast_api.adapters.mappers.ExternalBlastMapper;
import red.coder.forecast_api.adapters.repositories.external.ExternalBlastRepository;
import red.coder.forecast_api.domain.entity.Blast;

@Service
@AllArgsConstructor
public class ExternalBlastService {

    private final ExternalBlastRepository externalBlastRepository;
    private final ExternalBlastMapper externalBlastMapper;

    public List<Blast> readAll() {
        return externalBlastRepository.findAll().stream()
                .map(externalBlastMapper::createBlast)
                .toList();
    }

}
