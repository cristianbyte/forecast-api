package red.coder.forecast_api.adapters.abstract_services.external;

import java.util.List;

import red.coder.forecast_api.adapters.abstract_services.generic.ReadAllService;
import red.coder.forecast_api.adapters.abstract_services.generic.ReadService;
import red.coder.forecast_api.domain.entity.Blast;

public interface IBlastDataService extends 
    ReadService<String, Blast>, 
    ReadAllService<List<Blast>> {
}
