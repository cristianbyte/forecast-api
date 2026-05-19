package red.coder.forecast_api.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import jakarta.validation.constraints.NotBlank;

@Validated
@ConfigurationProperties(prefix = "supabase")
public record SupabaseProperties(
    @NotBlank String url,
    @NotBlank String publishableKey
) {
}
