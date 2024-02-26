package by.kladvirov.repository.hibernate;

import by.kladvirov.exception.RepositoryException;
import by.kladvirov.model.Role;
import by.kladvirov.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Repository
public class RoleRepositoryImpl implements RoleRepository {

    private final SessionFactory sessionFactory;

    private static final String FIND_ALL_QUERY = "FROM Role";

    @Override
    public Optional<Role> findById(Long id) {
        try (Session session = sessionFactory.openSession()) {
            return Optional.of(session.get(Role.class, id));
        } catch (HibernateException e) {
            throw new RepositoryException("There was an exception during finding Role by id");
        }
    }

    @Override
    public List<Role> findAll(int size, int page) {
        try (Session session = sessionFactory.openSession()) {
            Query<Role> query = session.createQuery(FIND_ALL_QUERY, Role.class);
            query.setFirstResult(size * page);
            query.setMaxResults(size);
            return query.list();
        } catch (HibernateException e) {
            throw new RepositoryException("There was an exception during finding all Roles");
        }
    }

    @Override
    public Role save(Role role) {
        try (Session session = sessionFactory.openSession()) {
            try {
                Transaction transaction = session.beginTransaction();
                session.persist(role);
                transaction.commit();
            } catch (HibernateException e) {
                session.getTransaction().rollback();
                throw new RepositoryException("There was an exception during saving Role");
            }
            return role;
        }
    }

    @Override
    public void update(Long id, Role role) {
        try (Session session = sessionFactory.openSession()) {
            try {
                Transaction transaction = session.beginTransaction();
                role.setId(id);
                session.merge(role);
                transaction.commit();
            } catch (HibernateException e) {
                session.getTransaction().rollback();
                throw new RepositoryException("There was an exception during updating Role");
            }
        }
    }

    @Override
    public void delete(Long id) {
        try (Session session = sessionFactory.openSession()) {
            try {
                Transaction transaction = session.beginTransaction();
                Role role = session.get(Role.class, id);
                session.remove(role);
                transaction.commit();
            } catch (HibernateException e) {
                session.getTransaction().rollback();
                throw new RepositoryException("There was an exception during deleting Role");
            }
        }
    }

}
