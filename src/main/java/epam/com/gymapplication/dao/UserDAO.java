package epam.com.gymapplication.dao;

import epam.com.gymapplication.model.User;

import java.util.List;


public interface UserDAO {
    void save(User user);
    User findById(Long id);
    void update(User user);
    void delete(User user);
    List<User> findAll();
    User findByUsername(String username);
    User findByFirstName(String firstName);
    User findByLastName(String lastName);

}
