package org.example.repository.hibernate;

import org.example.exception.RepositoryException;
import org.example.model.Order;
import org.example.repository.OrderRepository;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class OrderRepositoryImpl implements OrderRepository {

    private final SessionFactory sessionFactory;

    private static final String FIND_ALL_QUERY = "FROM Order";

    public OrderRepositoryImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Order findById(Long id) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(Order.class, id);
        } catch (HibernateException e) {
            throw new RepositoryException("There was an exception during finding Order by id");
        }
    }

    @Override
    public List<Order> findAll(int size, int page) {
        try (Session session = sessionFactory.openSession()) {
            Query<Order> query = session.createQuery(FIND_ALL_QUERY, Order.class);
            query.setFirstResult(size * page);
            query.setMaxResults(size);
            return query.list();
        } catch (HibernateException e) {
            throw new RepositoryException("There was an exception during finding all Orders");
        }
    }

    @Override
    public Order save(Order order) {
        try (Session session = sessionFactory.openSession()) {
            try {
                Transaction transaction = session.beginTransaction();
                session.save(order);
                transaction.commit();
            } catch (HibernateException e) {
                session.getTransaction().rollback();
                throw new RepositoryException("There was an exception during saving Good");
            }
            return order;
        }
    }

    @Override
    public void update(Order order) {
        try (Session session = sessionFactory.openSession()) {
            try {
                Transaction transaction = session.beginTransaction();
                session.update(order);
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
                Order order = session.load(Order.class, id);
                session.remove(order);
                transaction.commit();
            } catch (HibernateException e) {
                session.getTransaction().rollback();
                throw new RepositoryException("There was an exception during saving Good");
            }
        }
    }
}
