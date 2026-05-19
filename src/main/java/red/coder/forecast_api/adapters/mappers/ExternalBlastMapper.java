package red.coder.forecast_api.adapters.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import red.coder.forecast_api.api.dto.external.ExternalBlastDTO;
import red.coder.forecast_api.api.dto.response.BlastResponse;
import red.coder.forecast_api.domain.entity.Blast;

@Mapper(componentModel = "spring")
public interface ExternalBlastMapper {

    Blast requestToBlast(ExternalBlastDTO dto);

    BlastResponse blastToResponse(Blast blast);

    void updateBlast(ExternalBlastDTO dto, @MappingTarget Blast blast);
}
