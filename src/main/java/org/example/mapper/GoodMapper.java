package org.example.mapper;

import org.example.dto.GoodCreationDto;
import org.example.dto.GoodDto;
import org.example.model.Good;

import java.util.HashSet;

public class GoodMapper {

    public GoodDto toDto(Good good) {
        return new GoodDto(good.getName(), good.getPrice(), good.getIsAvailable());
    }

    public Good toEntity(GoodCreationDto goodDto) {
        return new Good(goodDto.getName(), goodDto.getPrice(), goodDto.getCreateDate(),
                goodDto.getExpirationDate(), goodDto.getIsAvailable(), new HashSet<>());
    }
}
