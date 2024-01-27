package org.example.service;

import org.example.model.Good;
import org.example.repository.hibernate.GoodDaoImpl;

import java.util.List;

public class GoodService {
    private GoodDaoImpl goodsDao = new GoodDaoImpl();

    public GoodService() {
    }
    public Good findGood(Long id) {
        return goodsDao.findById(id);
    }

    public List<Good> findAllGoods() {
        return goodsDao.findAll();
    }

    public void saveGood(Good good) {
        goodsDao.save(good);
    }

    public void updateGood(Good good) {
        goodsDao.update(good);
    }

    public void deleteGood(Long id) {
        goodsDao.delete(id);
    }
}