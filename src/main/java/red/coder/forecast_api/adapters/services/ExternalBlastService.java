package red.coder.forecast_api.adapters.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import red.coder.forecast_api.adapters.abstract_services.external.IBlastDataService;
import red.coder.forecast_api.adapters.mappers.ExternalBlastMapper;
import red.coder.forecast_api.domain.entity.Blast;

@Service
@AllArgsConstructor
public class ExternalBlastService implements IBlastDataService {

    // private final BlastDataApiClient blastDataApiClient;
    private final ExternalBlastMapper blastMapper;

    @Override
    public Optional<Blast> getById(String id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'read'");
    }

    @Override
    public List<Blast> readAll() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'readAll'");
    }
    
}
