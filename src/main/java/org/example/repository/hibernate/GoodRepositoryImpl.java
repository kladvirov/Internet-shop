package org.example.repository.hibernate;

import org.example.exception.RepositoryException;
import org.example.model.Good;
import org.example.repository.GoodRepository;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;

public class GoodRepositoryImpl implements GoodRepository {

    private final SessionFactory sessionFactory;

    private static final String FIND_ALL_QUERY = "FROM Good";

    public GoodRepositoryImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Good findById(Long id) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(Good.class, id);
        } catch (HibernateException e) {
            throw new RepositoryException("There was an exception during finding Good by id");
        }
    }

    @Override
    public List<Good> findAll() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery(FIND_ALL_QUERY, Good.class).list();
        } catch (HibernateException e) {
            throw new RepositoryException("There was an exception during finding all Goods");
        }
    }

    @Override
    public Good save(Good good) {
        try (Session session = sessionFactory.openSession()) {
            try {
                Transaction transaction = session.beginTransaction();
                session.save(good);
                transaction.commit();
            } catch (HibernateException e) {
                session.getTransaction().rollback();
                throw new RepositoryException("There was an exception during saving Good");
            }
            return good;
        }
    }

    @Override
    public void update(Good good) {
        try (Session session = sessionFactory.openSession()) {
            try {
                Transaction transaction = session.beginTransaction();
                session.update(good);
                transaction.commit();
            } catch (HibernateException e) {
                session.getTransaction().rollback();
                throw new RepositoryException("There was an exception during updating Good");
            }
        }
    }

    @Override
    public void delete(Long id) {
        try (Session session = sessionFactory.openSession()) {
            try {
                Transaction transaction = session.beginTransaction();
                Good good = session.load(Good.class, id);
                session.delete(good);
                transaction.commit();
            } catch (HibernateException e) {
                session.getTransaction().rollback();
                throw new RepositoryException("There was an exception during deleting Good");
            }
        }
    }
}
