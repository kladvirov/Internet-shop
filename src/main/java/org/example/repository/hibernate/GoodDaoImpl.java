package org.example.repository.hibernate;

import org.example.model.Good;
import org.example.util.hibernate.HibernateSessionFactory;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class GoodDaoImpl implements GoodDao {

    @Override
    public Good findById(Long id) {
        return HibernateSessionFactory.getSessionFactory().openSession().get(Good.class, id);
    }

    @Override
    public List<Good> findAll() {
        List<Good> goods = (List<Good>) HibernateSessionFactory.getSessionFactory().openSession().createQuery("From Good").list();
        return goods;
    }

    @Override
    public void save(Good good) {
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.save(good);
        transaction.commit();
        session.close();
    }

    @Override
    public void update(Good good) {
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.update(good);
        transaction.commit();
        session.close();
    }

    @Override
    public void delete(Long id) {
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        Good good = session.load(Good.class,id);
        session.delete(good);
        transaction.commit();
        session.close();
    }
}