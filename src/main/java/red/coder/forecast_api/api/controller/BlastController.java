package red.coder.forecast_api.api.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import red.coder.forecast_api.adapters.services.BlastService;
import red.coder.forecast_api.api.dto.external.ExternalBlastSyncResultDTO;

@RestController
@RequestMapping("/blasts")
@AllArgsConstructor
public class BlastController {

    private final BlastService blastService;

    @PostMapping("/sync")
    public ExternalBlastSyncResultDTO sync() {
        return blastService.syncExternalBlasts();
    }
}
