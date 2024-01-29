package org.example.repository.hibernate;

import org.example.model.Order;
import org.example.repository.OrderRepository;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;

public class OrderRepositoryImpl implements OrderRepository {

    private final SessionFactory sessionFactory;

    OrderRepositoryImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Order findById(Long id) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(Order.class, id);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Order> findAll() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("From Order", Order.class).list();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Order save(Order order) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.save(order);
            transaction.commit();
        } catch (HibernateException e) {
            throw new RuntimeException(e);
        }
        return order;
    }

    @Override
    public void update(Order order) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.update(order);
            transaction.commit();
        } catch (HibernateException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(Long id) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            Order order = session.load(Order.class, id);
            session.delete(order);
            transaction.commit();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
