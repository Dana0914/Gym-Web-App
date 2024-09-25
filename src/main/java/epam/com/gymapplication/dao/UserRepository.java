package epam.com.gymapplication.dao;

import epam.com.gymapplication.entity.User;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

    @Modifying
    @Transactional
    @Query(value = "update User u set u.firstname =:firstname, " +
            "u.lastname =:lastname where u.id =:id", nativeQuery = false)
    void update(@Param("firstname") String firstname,
                @Param("lastname") String lastname,
                @Param("id") Long id);

    @Query(value = "select u from User u where u.firstname = :firstname", nativeQuery = false)
    Optional<User> findByFirstName(@Param("firstname") String name);

    @Query(value = "select u from User u where u.lastname = :lastname", nativeQuery = false)
    Optional<User> findByLastName(@Param("lastname") String name);

    @Query(value = "select u from User u where u.username = :username", nativeQuery = false)
    Optional<User> findByUsername(@Param("username") String username);
}
