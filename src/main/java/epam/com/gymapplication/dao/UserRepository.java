package epam.com.gymapplication.dao;

import epam.com.gymapplication.model.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

    void update(User user);

    @Query(value = "select u from User u where u.firstname = :firstname", nativeQuery = false)
    User findByFirstName(@Param("firstname") String name);

    @Query(value = "select u from User u where u.lastname = :lastname", nativeQuery = false)
    User findByLastName(@Param("lastname") String name);

    @Query(value = "select u from User u where u.username = :username", nativeQuery = false)
    User findByUsername(@Param("username") String username);
}
