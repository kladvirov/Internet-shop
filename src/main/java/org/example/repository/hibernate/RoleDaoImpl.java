package org.example.repository.hibernate;

import org.example.model.Role;
import org.example.util.hibernate.HibernateSessionFactory;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class RoleDaoImpl implements RoleDao{

    @Override
    public Role findById(Long id) {
        return HibernateSessionFactory.getSessionFactory().openSession().get(Role.class, id);
    }

    @Override
    public List<Role> findAll() {
        List<Role> roles = (List<Role>) HibernateSessionFactory.getSessionFactory().openSession().createQuery("From Role").list();
        return roles;
    }

    @Override
    public void save(Role role) {
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.save(role);
        transaction.commit();
        session.close();
    }

    @Override
    public void update(Role role) {
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.update(role);
        transaction.commit();
        session.close();
    }

    @Override
    public void delete(Long id) {
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        Role role = session.load(Role.class,id);
        session.delete(role);
        transaction.commit();
        session.close();
    }
}
