package by.kladvirov.repository.hibernate;

import by.kladvirov.model.Good;
import lombok.RequiredArgsConstructor;
import by.kladvirov.exception.RepositoryException;
import by.kladvirov.repository.GoodRepository;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

@RequiredArgsConstructor
public class GoodRepositoryImpl implements GoodRepository {

    private final SessionFactory sessionFactory;

    private static final String FIND_ALL_QUERY = "FROM Good";

    @Override
    public Good findById(Long id) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(Good.class, id);
        } catch (HibernateException e) {
            throw new RepositoryException("There was an exception during finding Good by id");
        }
    }

    @Override
    public List<Good> findAll(int size, int page) {
        try (Session session = sessionFactory.openSession()) {
            Query<Good> query = session.createQuery(FIND_ALL_QUERY, Good.class);
            query.setFirstResult(size * page);
            query.setMaxResults(size);
            return query.list();
        } catch (HibernateException e) {
            throw new RepositoryException("There was an exception during finding all Goods");
        }
    }

    @Override
    public Good save(Good good) {
        try (Session session = sessionFactory.openSession()) {
            try {
                Transaction transaction = session.beginTransaction();
                session.persist(good);
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
                session.merge(good);
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
                Good good = session.get(Good.class, id);
                session.remove(good);
                transaction.commit();
            } catch (HibernateException e) {
                session.getTransaction().rollback();
                throw new RepositoryException("There was an exception during deleting Good");
            }
        }
    }

}
