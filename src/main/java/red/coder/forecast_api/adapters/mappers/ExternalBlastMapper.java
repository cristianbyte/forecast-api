package red.coder.forecast_api.adapters.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.ObjectFactory;

import red.coder.forecast_api.api.dto.external.ExternalBlastDTO;
import red.coder.forecast_api.domain.entity.Blast;

@Mapper(componentModel = "spring")
public interface ExternalBlastMapper {

    @ObjectFactory
    Blast createBlast(ExternalBlastDTO dto);

    @ObjectFactory
    void updateBlast(ExternalBlastDTO dto, @MappingTarget Blast blast);
}
