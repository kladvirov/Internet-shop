package by.kladvirov.service;

import by.kladvirov.dto.GoodCreationDto;
import by.kladvirov.dto.GoodDto;
import by.kladvirov.dto.GoodUpdateDto;
import by.kladvirov.mapper.GoodMapper;
import by.kladvirov.model.Good;
import by.kladvirov.repository.GoodRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
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