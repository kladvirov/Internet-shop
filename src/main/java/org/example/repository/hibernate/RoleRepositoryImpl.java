package org.example.repository.hibernate;

import org.example.model.Role;
import org.example.repository.RoleRepository;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;

public class RoleRepositoryImpl implements RoleRepository {

    private final SessionFactory sessionFactory;

    RoleRepositoryImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Role findById(Long id) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(Role.class, id);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Role> findAll() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("From Role", Role.class).list();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Role save(Role role) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.save(role);
            transaction.commit();
        } catch (HibernateException e) {
            throw new RuntimeException(e);
        }
        return role;
    }

    @Override
    public void update(Role role) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.update(role);
            transaction.commit();
        } catch (HibernateException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(Long id) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            Role role = session.load(Role.class, id);
            session.delete(role);
            transaction.commit();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
