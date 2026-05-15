package red.coder.forecast_api.adapters.abstract_services.internal;

import red.coder.forecast_api.adapters.abstract_services.generic.CreateService;
import red.coder.forecast_api.adapters.abstract_services.generic.DeleteService;
import red.coder.forecast_api.adapters.abstract_services.generic.ReadService;
import red.coder.forecast_api.adapters.abstract_services.generic.UpdateService;
import red.coder.forecast_api.domain.entity.Blast;

public interface IBlastService extends 
    CreateService<Blast, String>, 
    ReadService<String, Blast>, 
    UpdateService<String, Blast, Blast>, 
    DeleteService<String> {
}
