package org.example.repository.hibernate;

import org.example.model.Order;
import org.example.util.hibernate.HibernateSessionFactory;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class OrderDaoImpl implements OrderDao {

    @Override
    public Order findById(Long id) {
        return HibernateSessionFactory.getSessionFactory().openSession().get(Order.class, id);
    }

    @Override
    public List<Order> findAll() {
        List<Order> orders = (List<Order>) HibernateSessionFactory.getSessionFactory().openSession().createQuery("From Order").list();
        return orders;
    }

    @Override
    public void save(Order order) {
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.save(order);
        transaction.commit();
        session.close();
    }

    @Override
    public void update(Order order) {
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.update(order);
        transaction.commit();
        session.close();
    }

    @Override
    public void delete(Long id) {
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        Order order = session.load(Order.class,id);
        session.delete(order);
        transaction.commit();
        session.close();
    }
}
