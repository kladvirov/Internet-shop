package org.example.repository.hibernate;

import lombok.RequiredArgsConstructor;
import org.example.exception.RepositoryException;
import org.example.model.User;
import org.example.repository.UserRepository;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {

    private final SessionFactory sessionFactory;

    private static final String FIND_ALL_QUERY = "FROM User";

    @Override
    public User findById(Long id) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(User.class, id);
        } catch (HibernateException e) {
            throw new RepositoryException("There was an exception during finding User by id");
        }
    }

    @Override
    public List<User> findAll(int size, int page) {
        try (Session session = sessionFactory.openSession()) {
            Query<User> query = session.createQuery(FIND_ALL_QUERY, User.class);
            query.setFirstResult(size * page);
            query.setMaxResults(size);
            return query.list();
        } catch (HibernateException e) {
            throw new RepositoryException("There was an exception during finding all Users");
        }
    }

    @Override
    public User save(User user) {
        try (Session session = sessionFactory.openSession()) {
            try {
                Transaction transaction = session.beginTransaction();
                session.persist(user);
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
                session.merge(user);
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
                User user = session.get(User.class, id);
                session.remove(user);
                transaction.commit();
            } catch (HibernateException e) {
                session.getTransaction().rollback();
                throw new RepositoryException("There was an exception during deleting User");
            }
        }
    }
}
