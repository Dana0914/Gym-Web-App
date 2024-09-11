package epam.com.gymapplication.dao;

import epam.com.gymapplication.model.User;

import java.util.List;
import java.util.Optional;

public interface UserDAO {
    void save(User user);
    Optional<User> findById(Long id);
    void update(User user);
    void delete(User user);
    List<User> findAll();
    User findByUsername(String username);
    User findByFirstName(String firstName);
    User findByLastName(String lastName);

}
