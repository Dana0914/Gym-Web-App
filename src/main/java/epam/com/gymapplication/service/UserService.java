package epam.com.gymapplication.service;

import epam.com.gymapplication.dao.UserRepository;
import epam.com.gymapplication.model.User;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private UserRepository userRepository;


    public void save(User user) {
        logger.info("User saved {}", user);
        userRepository.save(user);
    }

    public User findById(Long id) {
        logger.info("Found user by id {} ", id);
        return userRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("User with id " + id + " not found"));

    }

    public List<User> findAll() {
        return (List<User>) userRepository.findAll();
    }


    public void deleteById(Long id)  {
        logger.info("Deleted user {} ", id);
        userRepository.deleteById(id);
    }

    public void update(User user)  {
        logger.info("Updated user by id {} ", user.getId());
        userRepository.update(user);
    }

    public Optional<User> findByFirstname(String name)  {
        logger.info("Found user by firstname {} ", name);
        User byFirstName = userRepository.findByFirstName(name);
        if (byFirstName == null) {
            return Optional.empty();
        }
        return Optional.of(byFirstName);
    }

    public Optional<User> findByLastname(String name) {
        logger.info("Found user by lastname {} ", name);
        User byLastName = userRepository.findByLastName(name);
        if (byLastName == null) {
            return Optional.empty();
        }
        return Optional.of(byLastName);
    }

    public Optional<User> findByUsername(String username)  {
        logger.info("Found user by username {} ", username);
        User byUsername = userRepository.findByUsername(username);
        if (byUsername == null) {
            return Optional.empty();
        }
        return Optional.of(byUsername);
    }
}
