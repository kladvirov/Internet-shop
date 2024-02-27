package by.kladvirov.service;

import by.kladvirov.dto.GoodCreationDto;
import by.kladvirov.dto.GoodDto;
import by.kladvirov.dto.GoodUpdateDto;
import by.kladvirov.exception.RepositoryException;
import by.kladvirov.exception.ServiceException;
import by.kladvirov.mapper.GoodMapper;
import by.kladvirov.model.Good;
import by.kladvirov.repository.GoodRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class GoodService {

    private final GoodRepository goodRepository;

    private final GoodMapper goodMapper;

    public GoodDto findById(Long id) {
        Good good = goodRepository.findById(id).orElseThrow(() -> new ServiceException("There is no such good", HttpStatus.NOT_FOUND));
        return goodMapper.toDto(good);
    }

    public List<GoodDto> findAll(int size, int page) {
        try {
            return goodMapper.toDto(goodRepository.findAll(size, page));
        } catch (RepositoryException ex) {
            throw new ServiceException("Error during finding all goods", HttpStatus.BAD_REQUEST);
        }
    }

    public GoodDto save(GoodCreationDto goodDto) {
        try {
            Good entity = goodMapper.toEntity(goodDto);
            return goodMapper.toDto(goodRepository.save(entity));
        } catch (RepositoryException ex) {
            throw new ServiceException("Error during saving good", HttpStatus.BAD_REQUEST);
        }
    }

    public void update(Long id, GoodUpdateDto goodDto) {
        try {
            goodRepository.update(id, goodMapper.toEntity(goodDto));
        } catch (RepositoryException ex) {
            throw new ServiceException("Error during updating good", HttpStatus.BAD_REQUEST);
        }
    }

    public void delete(Long id) {
        try {
            goodRepository.delete(id);
        } catch (RepositoryException ex) {
            throw new ServiceException("Error during deleting good", HttpStatus.BAD_REQUEST);
        }
    }

}