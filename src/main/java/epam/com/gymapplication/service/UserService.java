package epam.com.gymapplication.service;

import epam.com.gymapplication.dao.UserRepository;
import epam.com.gymapplication.entity.User;
import epam.com.gymapplication.utility.exception.ResourceNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;

@Transactional
@Service
public class UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);


    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    public void save(User user) {
        logger.info("User saved {}", user);

        userRepository.save(user);
    }

    public User findById(Long id) throws ResourceNotFoundException {
        logger.info("Found user by id {} ", id);

        return userRepository
                .findById(id)
                .orElseThrow(() ->
                new ResourceNotFoundException("User with id " + id + " not found"));

    }

    public List<User> findAll() {
        return (List<User>) userRepository.findAll();
    }


    public void deleteById(Long id)  {
        logger.info("Deleted user {} ", id);

        userRepository.deleteById(id);
    }


    public User findByFirstname(String name) throws ResourceNotFoundException {
        logger.info("Found user by firstname {} ", name);

        return userRepository
                .findByFirstName(name)
                .orElseThrow(() ->
                new ResourceNotFoundException("User with name " + name + " not found"));
    }

    public User findByLastname(String name) throws ResourceNotFoundException {
        logger.info("Found user by lastname {} ", name);

        return userRepository
                .findByLastName(name)
                .orElseThrow(() ->
                new ResourceNotFoundException("User with name " + name + " not found"));

    }

    public User findByUsername(String username) throws ResourceNotFoundException {
        logger.info("Found user by username {} ", username);

        return userRepository
                .findByUsername(username)
                .orElseThrow(() ->
                new ResourceNotFoundException("User with username " + username + " not found"));
    }
}
