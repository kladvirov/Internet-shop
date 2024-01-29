package org.example.service;

import org.example.model.Good;
import org.example.repository.GoodRepository;

import java.util.List;

public class GoodService {
    private final GoodRepository goodRepository;

    public GoodService(GoodRepository goodRepository) {
        this.goodRepository = goodRepository;
    }

    public Good findGood(Long id) {
        return goodRepository.findById(id);
    }

    public List<Good> findAllGoods() {
        return goodRepository.findAll();
    }

    public void saveGood(Good good) {
        goodRepository.save(good);
    }

    public void updateGood(Good good) {
        goodRepository.update(good);
    }

    public void deleteGood(Long id) {
        goodRepository.delete(id);
    }
}