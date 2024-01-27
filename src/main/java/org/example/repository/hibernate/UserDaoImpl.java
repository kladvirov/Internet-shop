package org.example.repository.hibernate;

import org.example.model.User;
import org.example.util.hibernate.HibernateSessionFactory;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class UserDaoImpl implements UserDao {

    @Override
    public User findById(Long id) {
        return HibernateSessionFactory.getSessionFactory().openSession().get(User.class, id);
    }

    @Override
    public List<User> findAll() {
        List<User> users = (List<User>) HibernateSessionFactory.getSessionFactory().openSession().createQuery("From User").list();
        return users;
    }

    @Override
    public void save(User user) {
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.save(user);
        transaction.commit();
        session.close();
    }

    @Override
    public void update(User user) {
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.update(user);
        transaction.commit();
        session.close();
    }

    @Override
    public void delete(Long id) {
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        User user = session.load(User.class,id);
        session.delete(user);
        transaction.commit();
        session.close();
    }
}
