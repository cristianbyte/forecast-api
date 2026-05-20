package red.coder.forecast_api.api.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import red.coder.forecast_api.adapters.abstract_services.IBlastService;
import red.coder.forecast_api.api.dto.external.ExternalBlastSyncResultDTO;
import red.coder.forecast_api.api.dto.response.BlastResponse;
import red.coder.forecast_api.domain.enums.BlastStatus;

@RestController
@RequestMapping("/blasts")
@AllArgsConstructor
@Validated
public class BlastController {

    private final IBlastService blastService;

    @GetMapping
    @Operation(
            summary = "List blasts",
            description = "Returns blasts filtered by optional location, status, and billing period query parameters.")
    public ResponseEntity<List<BlastResponse>> read(
            @Parameter(
                    description = "Blast location code. Example values: HN or HS.",
                    example = "HN",
                    schema = @Schema(type = "string", allowableValues = { "HN", "HS" }))
            @Pattern(regexp = "HN|HS", message = "must be HN or HS")
            @RequestParam(required = false) String location,
            @Parameter(
                    description = "Blast workflow status.",
                    example = "DRAFT",
                    schema = @Schema(implementation = BlastStatus.class))
            @RequestParam(required = false) BlastStatus status,
            @Parameter(
                    description = "Billing period in YYYY-MM format.",
                    example = "2026-05",
                    schema = @Schema(type = "string", pattern = "^\\d{4}-\\d{2}$"))
            @Pattern(regexp = "^\\d{4}-\\d{2}$", message = "must use YYYY-MM format")
            @RequestParam(required = false) String period) {
        return ResponseEntity.ok(blastService.findAll(location, status, period));
    }

    @PostMapping("/sync")
    public ExternalBlastSyncResultDTO sync() {
        return blastService.syncExternalBlasts();
    }
}
