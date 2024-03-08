package by.kladvirov.repository;

import by.kladvirov.model.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    @Query("from User u left join fetch u.roles left join fetch u.orders where u.id = :id")
    Optional<User> findUserById(@Param("id") Long id);

    @Query("from User u left join fetch u.roles r left join fetch u.orders left join fetch r.authorities where u.login = :login")
    Optional<User> findByLogin(@Param("login") String login);

    @Query("from User u left join fetch u.roles left join fetch u.orders")
    List<User> findAllUsers(Pageable pageable);
}

