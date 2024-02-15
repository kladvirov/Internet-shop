package org.example.mapper;

import org.example.dto.GoodCreationDto;
import org.example.dto.GoodDto;
import org.example.dto.GoodUpdateDto;
import org.example.model.Good;
import org.mapstruct.Mapper;

@Mapper
public interface GoodMapper {

    Good toEntity(GoodCreationDto goodDto);

    Good toEntity(GoodUpdateDto goodUpdateDto);

    GoodDto toDto(Good good);
}
