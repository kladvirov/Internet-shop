package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.dto.GoodCreationDto;
import org.example.dto.GoodDto;
import org.example.dto.GoodUpdateDto;
import org.example.mapper.GoodMapper;
import org.example.repository.GoodRepository;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class GoodService {

    private final GoodRepository goodRepository;

    private final GoodMapper goodMapper;

    public GoodDto findById(Long id) {
        return goodMapper.toDto(goodRepository.findById(id));
    }

    public List<GoodDto> findAll(int size, int page) {
        return goodRepository.findAll(size, page).stream()
                .map(goodMapper::toDto)
                .collect(Collectors.toList());
    }

    public GoodDto save(GoodCreationDto goodDto) {
        return goodMapper.toDto(goodRepository.save(goodMapper.toEntity(goodDto)));
    }

    public void update(GoodUpdateDto goodDto) {
        goodRepository.update(goodMapper.toEntity(goodDto));
    }

    public void delete(Long id) {
        goodRepository.delete(id);
    }
}