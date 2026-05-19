package red.coder.forecast_api.api.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import red.coder.forecast_api.adapters.abstract_services.IBlastService;
import red.coder.forecast_api.api.dto.external.ExternalBlastSyncResultDTO;
import red.coder.forecast_api.api.dto.response.BlastResponse;

@RestController
@RequestMapping("/blasts")
@AllArgsConstructor
public class BlastController {

    private final IBlastService blastService;

    @GetMapping
    public ResponseEntity<List<BlastResponse>> read() {
        return ResponseEntity.ok(blastService.readAll());
    }

    @PostMapping("/sync")
    public ExternalBlastSyncResultDTO sync() {
        return blastService.syncExternalBlasts();
    }
}
