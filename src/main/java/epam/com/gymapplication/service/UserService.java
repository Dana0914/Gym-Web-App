package epam.com.gymapplication.service;

import epam.com.gymapplication.customexception.ServiceException;
import epam.com.gymapplication.dao.UserDAO;
import epam.com.gymapplication.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private UserDAO userDAO;

    @Transactional
    public void save(User user) throws ServiceException {
        if (user.getUsername() == null || user.getUsername().isEmpty()
        || user.getPassword() == null || user.getPassword().isEmpty()
        || user.getFirstName() == null || user.getFirstName().isEmpty()
        || user.getLastName() == null || user.getLastName().isEmpty()) {

            logger.warn("User save failed");
            throw new ServiceException("User save failed, user is invalid");

        }
        logger.info("User saved {}", user);
        userDAO.save(user);
    }

    public User findById(Long id) throws ServiceException {
        if (id == null || id <= 0) {
            logger.warn("User by id not found");
            throw new ServiceException("User finding failed, id is invalid");
        }
        logger.info("Found user by id {} ", id);
        return userDAO.findById(id);



    }

    public List<User> findAll() {
        return userDAO.findAll();
    }

    @Transactional
    public void delete(User user) throws ServiceException {
        if (user.getUsername() == null || user.getUsername().isEmpty()
                || user.getPassword() == null || user.getPassword().isEmpty()
                || user.getFirstName() == null || user.getFirstName().isEmpty()
                || user.getLastName() == null || user.getLastName().isEmpty()) {

            logger.warn("User delete failed");
            throw new ServiceException("User delete failed, user does not exist");
        }

        logger.info("Deleted user {} ", user);
        userDAO.delete(user);
    }

    public void update(User user) throws ServiceException {
        if (user.getUsername() == null || user.getUsername().isEmpty()
                || user.getPassword() == null || user.getPassword().isEmpty()
                || user.getFirstName() == null || user.getFirstName().isEmpty()
                || user.getLastName() == null || user.getLastName().isEmpty()) {

            logger.warn("User update failed");
            throw new ServiceException("User update failed, user is invalid");

        }
        logger.info("Updated user by id {} ", user.getId());
        userDAO.update(user);
    }

    public User findByFirstname(String name) throws ServiceException {
        if (name == null || name.isEmpty()) {
            logger.warn("User find by name failed");
            throw new ServiceException("User find by name failed, name is invalid");
        }
        logger.info("Found user by firstname {} ", name);
        return userDAO.findByFirstName(name);
    }

    public User findByLastname(String name) throws ServiceException {
        if (name == null || name.isEmpty()) {
            logger.warn("User find by last name failed");
            throw new ServiceException("User find by last name failed, lastname is invalid");
        }
        logger.info("Found user by lastname {} ", name);
        return userDAO.findByLastName(name);
    }

    public User findByUsername(String username) throws ServiceException {
        if (username == null || username.isEmpty()) {
            logger.warn("User find by username failed");
            throw new ServiceException("User find by username failed, username is invalid");
        }
        logger.info("Found user by username {} ", username);
        return userDAO.findByUsername(username);
    }
}
