package red.coder.forecast_api.adapters.abstract_services.generic;

import java.util.Optional;

public interface ReadService<Id, Response> {
    Optional<Response> getById(Id id);
}
