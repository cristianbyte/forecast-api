package red.coder.forecast_api.adapters.abstract_services.generic;

public interface CreateService<Request, Response> {
    Response create(Request request);
}
