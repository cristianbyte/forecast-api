package red.coder.forecast_api.adapters.abstract_services;

import java.util.List;

import red.coder.forecast_api.adapters.abstract_services.generic.ReadAllService;
import red.coder.forecast_api.api.dto.external.ExternalBlastSyncResultDTO;
import red.coder.forecast_api.api.dto.response.BlastResponse;

public interface IBlastService extends ReadAllService<List<BlastResponse>> {

    ExternalBlastSyncResultDTO syncExternalBlasts();
}
