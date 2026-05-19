package red.coder.forecast_api.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
@EnableConfigurationProperties(SupabaseProperties.class)
public class SupabaseClientConfig {

    @Bean
    @Qualifier("supabaseWebClient")
    WebClient supabaseWebClient(WebClient.Builder webClientBuilder, SupabaseProperties supabaseProperties) {
        return webClientBuilder
            .baseUrl(supabaseProperties.url())
            .defaultHeader("apikey", supabaseProperties.publishableKey())
            .defaultHeader(HttpHeaders.AUTHORIZATION, "Bearer " + supabaseProperties.publishableKey())
            .defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
            .build();
    }
}
