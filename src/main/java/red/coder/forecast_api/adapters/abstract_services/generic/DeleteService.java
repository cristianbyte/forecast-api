package red.coder.forecast_api.adapters.abstract_services.generic;

public interface DeleteService<Id> {
    void delete(Id id);
}
