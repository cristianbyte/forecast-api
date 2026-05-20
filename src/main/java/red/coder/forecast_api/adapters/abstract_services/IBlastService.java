package red.coder.forecast_api.adapters.abstract_services;

import java.util.List;

import red.coder.forecast_api.adapters.abstract_services.generic.ReadAllService;
import red.coder.forecast_api.api.dto.external.ExternalBlastSyncResultDTO;
import red.coder.forecast_api.api.dto.response.BlastResponse;
import red.coder.forecast_api.domain.enums.BlastStatus;

public interface IBlastService extends ReadAllService<List<BlastResponse>> {

    List<BlastResponse> findAll(String location, BlastStatus status, String period);

    ExternalBlastSyncResultDTO syncExternalBlasts();
}
