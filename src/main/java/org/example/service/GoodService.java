package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.dto.GoodCreationDto;
import org.example.dto.GoodDto;
import org.example.dto.GoodUpdateDto;
import org.example.mapper.GoodMapper;
import org.example.model.Good;
import org.example.repository.GoodRepository;

import java.util.List;

@RequiredArgsConstructor
public class GoodService {

    private final GoodRepository goodRepository;

    private final GoodMapper goodMapper;

    public GoodDto findById(Long id) {
        return goodMapper.toDto(goodRepository.findById(id));
    }

    public List<GoodDto> findAll(int size, int page) {
        return goodMapper.toDto(goodRepository.findAll(size, page));
    }

    public GoodDto save(GoodCreationDto goodDto) {
        Good entity = goodMapper.toEntity(goodDto);
        return goodMapper.toDto(goodRepository.save(entity));
    }

    public void update(GoodUpdateDto goodDto) {
        goodRepository.update(goodMapper.toEntity(goodDto));
    }

    public void delete(Long id) {
        goodRepository.delete(id);
    }

}