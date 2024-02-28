package by.kladvirov.mapper;

import by.kladvirov.dto.GoodCreationDto;
import by.kladvirov.dto.GoodDto;
import by.kladvirov.dto.GoodUpdateDto;
import by.kladvirov.model.Good;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "SPRING", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface GoodMapper {

    Good toEntity(GoodCreationDto goodDto);

    Good toEntity(GoodUpdateDto goodUpdateDto);

    GoodDto toDto(Good good);

    List<GoodDto> toDto(List<Good> goods);

}
