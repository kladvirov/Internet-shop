package by.kladvirov.service;

import by.kladvirov.dto.GoodCreationDto;
import by.kladvirov.dto.GoodDto;
import by.kladvirov.dto.GoodUpdateDto;
import by.kladvirov.exception.ServiceException;
import by.kladvirov.mapper.GoodMapper;
import by.kladvirov.model.Good;
import by.kladvirov.repository.GoodRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class GoodService {

    private final GoodRepository goodRepository;

    private final GoodMapper goodMapper;

    @Transactional(readOnly = true)
    public GoodDto findById(Long id) {
        Good good = goodRepository.findById(id).orElseThrow(() -> new ServiceException("There is no such good", HttpStatus.NOT_FOUND));
        return goodMapper.toDto(good);
    }

    @Transactional(readOnly = true)
    public List<GoodDto> findAll(Pageable pageable) {
        try {
            return goodMapper.toDto(goodRepository.findAll(pageable).toList());
        } catch (Exception ex) {
            throw new ServiceException("Error during finding all goods", HttpStatus.BAD_REQUEST);
        }
    }

    @Transactional
    public GoodDto save(GoodCreationDto goodDto) {
        try {
            Good entity = goodMapper.toEntity(goodDto);
            return goodMapper.toDto(goodRepository.save(entity));
        } catch (Exception ex) {
            throw new ServiceException("Error during saving good", HttpStatus.BAD_REQUEST);
        }
    }

    @Transactional
    public void update(Long id, GoodUpdateDto goodDto) {
        try {
            Good good = goodRepository.findById(id).orElseThrow(() -> new ServiceException("There is no such good", HttpStatus.NOT_FOUND));
            Good mappedGood = goodMapper.toEntity(goodDto);
            updateGood(good, mappedGood);
            goodRepository.save(good);
        } catch (Exception ex) {
            throw new ServiceException("Error during updating good", HttpStatus.BAD_REQUEST);
        }
    }

    @Transactional
    public void delete(Long id) {
        try {
            goodRepository.deleteById(id);
        } catch (Exception ex) {
            throw new ServiceException("Error during deleting good", HttpStatus.BAD_REQUEST);
        }
    }

    private void updateGood(Good good, Good mappedGood) {
        good.setIsAvailable(mappedGood.getIsAvailable());
        good.setPrice(mappedGood.getPrice());
    }

}