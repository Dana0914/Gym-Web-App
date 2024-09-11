package epam.com.gymapplication.service;

import epam.com.gymapplication.customexception.ProfileIsActiveException;
import epam.com.gymapplication.customexception.ProfileIsInActiveException;
import epam.com.gymapplication.customexception.ServiceException;
import epam.com.gymapplication.dao.TraineeDAO;
import epam.com.gymapplication.dao.UserDAO;
import epam.com.gymapplication.model.Trainee;
import epam.com.gymapplication.model.User;
import epam.com.gymapplication.profile.PasswordGenerator;
import epam.com.gymapplication.profile.UserProfileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;



@Service
public class TraineeService {
    private static final Logger logger = LoggerFactory.getLogger(TraineeService.class);

    @Autowired
    private TraineeDAO traineeDAO;

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private UserProfileService userProfileService;

    @Autowired
    private PasswordGenerator passwordGenerator;


    @Transactional
    public Trainee createTraineeProfile(Trainee trainee, User user) throws ServiceException {


        String username = userProfileService.concatenateUsername(user.getFirstName(), user.getLastName());
        String password = passwordGenerator.generatePassword();


        User user1 = new User();
        user1.setFirstName(user.getFirstName());
        user1.setLastName(user.getLastName());
        user1.setUsername(username);
        user1.setPassword(password);
        user1.setActive(user.isActive());


        userDAO.save(user1);


        Trainee trainee1 = new Trainee();
        trainee1.setAddress(trainee.getAddress());
        trainee1.setDateOfBirth(trainee.getDateOfBirth());
        trainee1.setUserId(user1.getId());
        trainee1.setUser(user1);


        traineeDAO.save(trainee1);

        return trainee1;
    }

    public Trainee findTraineeProfileByUsername(Trainee trainee) throws ServiceException {
        if (trainee.getUser().getUsername() == null || trainee.getUser().getUsername().isEmpty()) {
            logger.warn("Entity find by username failed");
            throw new ServiceException("Entity find by username failed");
        }
        return traineeDAO.findByUsername(trainee.getUser().getUsername());


    }

    public boolean authenticateTraineeProfile(Trainee trainee) throws ServiceException {
        if (trainee.getUser().getUsername() == null || trainee.getUser().getUsername().isEmpty()
        || trainee.getUser().getPassword() == null || trainee.getUser().getPassword().isEmpty()) {
            logger.warn("Entity authentication failed");
            throw new ServiceException("Entity authentication failed, username and password are invalid");

        }
        Trainee traineeProfileByUsername = findTraineeProfileByUsername(trainee);
        return traineeProfileByUsername.getUser().getUsername().equals(trainee.getUser().getUsername())
                && traineeProfileByUsername.getUser().getPassword().equals(trainee.getUser().getPassword());

    }

    public Trainee passwordChange(Trainee trainee) {
        Trainee traineeProfileByUsername = findTraineeProfileByUsername(trainee);
        if (traineeProfileByUsername.getUser().getPassword().equals(trainee.getUser().getPassword())) {
            traineeProfileByUsername.getUser().setPassword(passwordGenerator.generatePassword());
        }
        return traineeProfileByUsername;
    }

    @Transactional
    public void activateTrainee(Long id) throws ProfileIsActiveException {
        Trainee traineeById = traineeDAO.findById(id);
        if (traineeById.getUser().isActive()) {
            throw new ProfileIsActiveException("Trainee Profile is already activated");
        }
        traineeById.getUser().setActive(true);
        traineeDAO.save(traineeById);

    }

    @Transactional
    public void deactivateTrainee(Long id) throws ProfileIsInActiveException {
        Trainee traineeById = traineeDAO.findById(id);
        if (!traineeById.getUser().isActive()) {
            throw new ProfileIsInActiveException("Trainee Profile is already inactive");
        }
        traineeById.getUser().setActive(false);
        traineeDAO.save(traineeById);


    }

    @Transactional
    public void updateTraineeProfile(Trainee trainee) {
        if (trainee.getUser().getUsername() == null || trainee.getUser().getUsername().isEmpty()
        || trainee.getUser().getPassword() == null || trainee.getUser().getPassword().isEmpty()
        || trainee.getUser().getFirstName() == null || trainee.getUser().getFirstName().isEmpty()
        || trainee.getUser().getLastName() == null || trainee.getUser().getLastName().isEmpty()) {
            logger.warn("Entity update profile failed");
            throw new ServiceException("Entity update profile failed, username and password are invalid");
        }
        Trainee traineeById = traineeDAO.findById(trainee.getId());
        traineeById.getUser().setFirstName(trainee.getUser().getFirstName());
        traineeById.getUser().setLastName(trainee.getUser().getLastName());

    }

    @Transactional
    public void deleteTraineeProfileByUsername(Trainee trainee) throws ServiceException {
        if (trainee.getUser().getUsername() == null || trainee.getUser().getUsername().isEmpty()) {
            logger.warn("Trainee profile delete failed");
            throw new ServiceException("Delete Trainee Profile Failed, username is empty or null");
        }
        logger.info("Trainee profile delete successful {} ", trainee);
        Trainee traineeProfileByUsername = traineeDAO.findByUsername(trainee.getUser().getUsername());
        traineeDAO.delete(traineeProfileByUsername);
    }



    public List<Trainee> findAll() {
        return traineeDAO.findAll();
    }



    @Transactional
    public void saveTrainee(Trainee trainee) throws ServiceException {
        if (trainee.getUser().getFirstName() == null
                || trainee.getUser().getFirstName().isEmpty()
                || trainee.getUser().getLastName() == null
                || trainee.getUser().getLastName().isEmpty()
                || trainee.getUser().getUsername() == null
                || trainee.getUser().getUsername().isEmpty()
                || trainee.getUser().getPassword() == null
                || trainee.getUser().getPassword().isEmpty()
                || trainee.getAddress() == null
                || trainee.getAddress().isEmpty()
                || trainee.getDateOfBirth() == null
                || trainee.getDateOfBirth().toString().isEmpty()) {

            logger.warn("Trainee save failed");
            throw new ServiceException("Trainee save failed, trainee is invalid");

        }

        traineeDAO.save(trainee);
        logger.info("Trainee saved {}", trainee);

    }

    public void updateTrainee(Trainee trainee) throws ServiceException {
        if (trainee.getUser().getFirstName() == null
                || trainee.getUser().getFirstName().isEmpty()
                || trainee.getUser().getLastName() == null
                || trainee.getUser().getLastName().isEmpty()
                || trainee.getUser().getUsername() == null
                || trainee.getUser().getUsername().isEmpty()
                || trainee.getUser().getPassword() == null
                || trainee.getUser().getPassword().isEmpty()
                || trainee.getAddress() == null
                || trainee.getAddress().isEmpty()
                || trainee.getDateOfBirth() == null
                || trainee.getDateOfBirth().toString().isEmpty()) {
            logger.warn("Trainee update failed");
            throw new ServiceException("Trainee update failed, trainee is invalid");
        }

        traineeDAO.update(trainee);
        logger.info("Trainee updated {}", trainee);

    }



    @Transactional
    public void deleteTrainee(Trainee trainee) throws ServiceException {
        if (trainee.getUser().getFirstName() == null
                || trainee.getUser().getFirstName().isEmpty()
                || trainee.getUser().getLastName() == null
                || trainee.getUser().getLastName().isEmpty()
                || trainee.getUser().getUsername() == null
                || trainee.getUser().getUsername().isEmpty()
                || trainee.getUser().getPassword() == null
                || trainee.getUser().getPassword().isEmpty()
                || trainee.getAddress() == null
                || trainee.getAddress().isEmpty()
                || trainee.getDateOfBirth() == null
                || trainee.getDateOfBirth().toString().isEmpty()) {
            logger.warn("Trainee delete failed");
            throw new ServiceException("Trainee delete failed, trainee is invalid");
        }
        traineeDAO.delete(trainee);
        logger.info("Trainee deleted");

    }

    public Trainee findTraineeById(Long id) throws ServiceException {
        if (id == null || id <= 0) {
            logger.warn("Entity find by id failed");
            throw new ServiceException("Entity find by id failed, id is invalid");
        }
        logger.info("Found trainee by id {} ", id);
        return traineeDAO.findById(id);
    }



    public Trainee findTraineeByFirstName(String firstName) throws ServiceException {
        if (firstName == null || firstName.isEmpty()) {
            logger.warn("Entity find by firstname failed");
            throw new ServiceException("Entity find by firstname failed, firstname is invalid");
        }
        logger.info("Trainee found by name {}", firstName);
        return traineeDAO.findByFirstName(firstName);
    }

    public Trainee findByLastName(String lastName) throws ServiceException {
        if (lastName == null || lastName.isEmpty()) {
            logger.warn("Entity find by lastname failed");
            throw new ServiceException("Entity find by lastname failed, lastname is invalid");
        }
        logger.info("Trainee found by lastName {}", lastName);
        return traineeDAO.findByLastName(lastName);
    }
}
