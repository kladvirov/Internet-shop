package org.example.service;

import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.example.dto.GoodDto;
import org.example.mapper.GoodMapper;
import org.example.model.Good;
import org.example.repository.GoodRepository;

import java.util.List;
import java.util.stream.Collectors;

@ToString
@EqualsAndHashCode
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

    public GoodDto save(Good good) {
        return goodMapper.toDto(goodRepository.save(good));
    }

    public void update(Good good) {
        goodRepository.update(good);
    }

    public void delete(Long id) {
        goodRepository.delete(id);
    }
}