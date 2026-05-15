package red.coder.forecast_api.adapters.abstract_services.generic;

public interface UpdateService<Id, Request, Response> {
    Response update(Id id, Request request);
}
