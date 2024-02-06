package org.example.repository.hibernate;

import org.example.exception.RepositoryException;
import org.example.model.User;
import org.example.repository.UserRepository;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;

public class UserRepositoryImpl implements UserRepository {

    private final SessionFactory sessionFactory;

    private static final String FIND_ALL_QUERY = "FROM User";

    public UserRepositoryImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public User findById(Long id) {
        try(Session session = sessionFactory.openSession()) {
            return session.get(User.class, id);
        } catch (HibernateException e) {
            throw new RepositoryException("There was an exception during finding User by id");
        }
    }

    @Override
    public List<User> findAll() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery(FIND_ALL_QUERY, User.class).list();
        } catch (HibernateException e) {
            throw new RepositoryException("There was an exception during finding all Users");
        }
    }

    @Override
    public User save(User user) {
        try (Session session = sessionFactory.openSession()) {
            try {
                Transaction transaction = session.beginTransaction();
                session.save(user);
                transaction.commit();
            } catch (HibernateException e) {
                session.getTransaction().rollback();
                throw new RepositoryException("There was an exception during saving User");
            }
            return user;
        }
    }

    @Override
    public void update(User user) {
        try (Session session = sessionFactory.openSession()) {
            try {
                Transaction transaction = session.beginTransaction();
                session.update(user);
                transaction.commit();
            } catch (HibernateException e) {
                session.getTransaction().rollback();
                throw new RepositoryException("There was an exception during updating User");
            }
        }
    }

    @Override
    public void delete(Long id) {
        try (Session session = sessionFactory.openSession()) {
            try {
                Transaction transaction = session.beginTransaction();
                User user = session.load(User.class, id);
                session.delete(user);
                transaction.commit();
            } catch (HibernateException e) {
                session.getTransaction().rollback();
                throw new RepositoryException("There was an exception during deleting User");
            }
        }
    }
}
