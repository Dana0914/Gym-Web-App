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
import java.util.Optional;



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
    public Trainee createTraineeProfile(String firstname, String lastname, boolean isActive) throws ServiceException {
        if (firstname == null || firstname.isEmpty() || lastname == null || lastname.isEmpty()) {
            logger.warn("Trainee profile save failed");
            throw new ServiceException("Create Trainee Profile Failed, firstname or lastname is empty or invalid");
        }

        Trainee trainee = new Trainee();
        User user = new User();

        String username = userProfileService.concatenateUsername(firstname, lastname);
        String password = passwordGenerator.generatePassword();

        user.setUsername(username);
        user.setPassword(password);
        user.setActive(isActive);

        trainee.setUser(user);

        userDAO.save(user);

        traineeDAO.save(trainee);


        return trainee;
    }

    public Optional<Trainee> findTraineeProfileByUsername(String username) {
        if (username == null || username.isEmpty()) {
            return Optional.empty();
        }
        Optional<Trainee> byUsername = traineeDAO.findByUsername(username);
        return Optional.of(byUsername.orElseThrow());

    }

    public boolean authenticateTraineeProfile(String username, String password) {
        Optional<Trainee> traineeProfileByUsername = findTraineeProfileByUsername(username);
        return traineeProfileByUsername.get().getUser().getUsername().equals(username)
                && traineeProfileByUsername.get().getUser().getPassword().equals(password);
    }

    public Optional<Trainee> passwordChange(String username, String password) {
        Optional<Trainee> traineeProfileByUsername = findTraineeProfileByUsername(username);
        if (traineeProfileByUsername.get().getUser().getPassword().equals(password)) {
            traineeProfileByUsername.get().getUser().setPassword(passwordGenerator.generatePassword());
        }
        return traineeProfileByUsername;
    }

    @Transactional
    public void activateTrainee(Trainee trainee) throws ProfileIsActiveException {
        Optional<Trainee> traineeById = findTraineeById(trainee.getId());
        if (traineeById.isPresent()) {
            if (traineeById.get().getUser().isActive()) {
                throw new ProfileIsActiveException("Trainee Profile is already activated");
            }
            traineeById.get().getUser().setActive(true);
            traineeDAO.save(traineeById.get());
        }
    }

    @Transactional
    public void deactivateTrainee(Trainee trainee) throws ProfileIsInActiveException {
        Optional<Trainee> traineeById = findTraineeById(trainee.getId());
        if (traineeById.isPresent()) {
            if (!traineeById.get().getUser().isActive()) {
                throw new ProfileIsInActiveException("Trainee Profile is already inactive");
            }
            traineeById.get().getUser().setActive(false);
            traineeDAO.save(traineeById.get());
        }

    }

    @Transactional
    public boolean updateTraineeProfile(Long id, String name, String surname) {
        Optional<Trainee> traineeById = findTraineeById(id);
        if (traineeById.isPresent()) {
            traineeById.get().getUser().setFirstName(name);
            traineeById.get().getUser().setLastName(surname);
        }
        return false;
    }

    @Transactional
    public void deleteTraineeProfileByUsername(String username) throws ServiceException {
        if (username == null || username.isEmpty()) {
            logger.warn("Trainee profile delete failed");
            throw new ServiceException("Delete Trainee Profile Failed, username is empty or null");
        }

        logger.info("Trainee profile delete successful {} ", username);
        Optional<Trainee> traineeProfileByUsername = findTraineeProfileByUsername(username);
        traineeProfileByUsername.ifPresent(trainee -> traineeDAO.delete(trainee));


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
        logger.info("Trainee deleted {}", trainee);

    }

    public Optional<Trainee> findTraineeById(Long id)  {
        if (id == null || id <= 0) {
            return Optional.empty();
        }
        logger.info("Found trainee by id {} ", id);
        Optional<Trainee> byId = traineeDAO.findById(id);
        return Optional.of(byId.orElseThrow());
    }



    public Optional<Trainee> findTraineeByFirstName(String firstName) {
        if (firstName == null || firstName.isEmpty()) {
            return Optional.empty();
        }
        logger.info("Trainee found by name {}", firstName);
        return Optional.of(traineeDAO.findByFirstName(firstName).orElseThrow());
    }

    public Optional<Trainee> findByLastName(String lastName) {
        if (lastName == null || lastName.isEmpty()) {
            return Optional.empty();
        }
        logger.info("Trainee found by lastName {}", lastName);
        return Optional.of(traineeDAO.findByLastName(lastName).orElseThrow());
    }
}
