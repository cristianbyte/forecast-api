package red.coder.forecast_api.adapters.repositories.external;

import java.util.List;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Repository;
import org.springframework.web.reactive.function.client.WebClient;

import lombok.AllArgsConstructor;
import red.coder.forecast_api.adapters.exceptions.SupabaseRequestException;
import red.coder.forecast_api.api.dto.external.ExternalBlastDTO;
import reactor.core.publisher.Mono;

@Repository
@AllArgsConstructor
public class ExternalBlastRepository {

    private static final ParameterizedTypeReference<List<ExternalBlastDTO>> EXTERNAL_BLAST_LIST_TYPE =
        new ParameterizedTypeReference<>() {
        };

    @Qualifier("supabaseWebClient")
    private final WebClient supabaseWebClient;

    public List<ExternalBlastDTO> findAll() {
        List<ExternalBlastDTO> response = supabaseWebClient.get()
            .uri(uriBuilder -> uriBuilder
                .path("/rest/v1/v_external_blasts")
                .queryParam("select", "*")
                .build())
            .retrieve()
            .onStatus(HttpStatusCode::isError, clientResponse -> clientResponse.bodyToMono(String.class)
                .defaultIfEmpty("")
                .flatMap(responseBody -> Mono.error(new SupabaseRequestException(
                    "Supabase request failed",
                    clientResponse.statusCode().value(),
                    responseBody))))
            .bodyToMono(EXTERNAL_BLAST_LIST_TYPE)
            .block();

        return response == null ? List.of() : response;
    }
}
