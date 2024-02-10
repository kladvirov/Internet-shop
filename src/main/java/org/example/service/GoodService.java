package org.example.service;

import org.example.dto.GoodDto;
import org.example.mapper.GoodMapper;
import org.example.model.Good;
import org.example.repository.GoodRepository;

import java.util.List;
import java.util.stream.Collectors;

public class GoodService {
    private final GoodRepository goodRepository;

    public GoodService(GoodRepository goodRepository) {
        this.goodRepository = goodRepository;
    }

    public GoodDto findGood(Long id) {
        return new GoodMapper().goodToDto(goodRepository.findById(id));
    }

    public List<GoodDto> findAllGoods(int size, int page) {
        GoodMapper goodMapper = new GoodMapper();
        return goodRepository.findAll(size, page).stream()
                .map(goodMapper::goodToDto)
                .collect(Collectors.toList());
    }

    public Good saveGood(Good good) {
        return goodRepository.save(good);
    }

    public void updateGood(Good good) {
        goodRepository.update(good);
    }

    public void deleteGood(Long id) {
        goodRepository.delete(id);
    }
}