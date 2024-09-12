package epam.com.gymapplication.service;

import epam.com.gymapplication.customexception.ProfileIsActiveException;
import epam.com.gymapplication.customexception.ProfileIsInActiveException;
import epam.com.gymapplication.customexception.ServiceException;
import epam.com.gymapplication.dao.TraineeDAO;
import epam.com.gymapplication.dao.TrainerDAO;
import epam.com.gymapplication.dao.TrainingTypeDAO;
import epam.com.gymapplication.dao.UserDAO;
import epam.com.gymapplication.model.Trainee;
import epam.com.gymapplication.model.Trainer;
import epam.com.gymapplication.model.TrainingType;
import epam.com.gymapplication.model.User;
import epam.com.gymapplication.profile.PasswordGenerator;
import epam.com.gymapplication.profile.UserProfileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.Set;


@Service
public class TrainerService {
    private static final Logger logger = LoggerFactory.getLogger(TrainerService.class);

    @Autowired
    private TrainerDAO trainerDAO;

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private TrainingTypeDAO trainingTypeDAO;

    @Autowired
    private UserProfileService userProfileService;

    @Autowired
    private PasswordGenerator passwordGenerator;

    @Autowired
    private TraineeDAO traineeDAO;


    @Transactional
    public void updateTraineesTrainerList(Set<Trainer> trainers, String username) throws ServiceException {
        Trainee byUsername = traineeDAO.findByUsername(username);
        if (byUsername != null) {
            byUsername.setTrainers(trainers);
            logger.info("Trainee updated {} ", byUsername);
            traineeDAO.update(byUsername);
        } else {
            logger.warn("Trainee {} not found", username);
            throw new ServiceException("Trainee not found to be updated");
        }
    }

    @Transactional
    public void assignTraineeToTrainer(Long traineeId, Long trainerId) {
        Trainee trainee = traineeDAO.findById(traineeId);
        Trainer trainer = trainerDAO.findById(trainerId);


        trainee.getTrainers().add(trainer);
        trainer.getTrainees().add(trainee);

        trainerDAO.save(trainer);
        traineeDAO.save(trainee);
    }


    public List<Trainer> findTrainerNotAssignedToTrainee(String username) throws ServiceException {
        if (username == null || username.isEmpty()) {
            logger.error("Finding trainer list by username failed");
            throw new ServiceException("Finding trainer list by username failed, username is invalid");

        }
        logger.info("Finding trainer list by username: " + username);
        return trainerDAO.findTrainerNotAssignedToTrainee(username);

    }





    @Transactional
    public Trainer createTrainerProfile(Trainer trainer, User user, TrainingType trainingType) throws ServiceException {
        if (user.getFirstName() == null || user.getLastName() == null
        || user.getUsername() == null || user.getPassword() == null || trainingType.getTrainingTypeName() == null) {
            logger.warn("Trainer save failed");
            throw new ServiceException("Trainer save failed, trainer is invalid");
        }
        String username = userProfileService.concatenateUsername(user.getFirstName(), user.getLastName());
        String password = passwordGenerator.generatePassword();


        User user1 = new User();
        user1.setFirstName(user.getFirstName());
        user1.setLastName(user.getLastName());
        user1.setUsername(username);
        user1.setPassword(password);
        user1.setActive(user.isActive());

        userDAO.save(user1);

        TrainingType trainingType1 = new TrainingType();
        trainingType1.setTrainingTypeName(trainingType.getTrainingTypeName());

        trainingTypeDAO.save(trainingType1);


        Trainer trainer1 = new Trainer();
        trainer1.setUser(user1);
        trainer1.setTrainingType(trainingType1);

        trainerDAO.save(trainer1);

        return trainer1;
    }

    public Trainer findTrainerProfileByUsername(Trainer trainer) throws ServiceException {
        if (trainer.getUser().getUsername() == null || trainer.getUser().getUsername().isEmpty()) {
            logger.warn("Entity find by id failed");
            throw new ServiceException("Entity find by username failed");
        }
        return trainerDAO.findByUsername(trainer.getUser().getUsername());


    }

    public boolean authenticateTraineeProfile(String username, String password) throws ServiceException {
        if (username == null || username.isEmpty()
                || password == null || password.isEmpty()) {
            logger.warn("Entity authentication failed");
            throw new ServiceException("Entity authentication failed, username and password are invalid");

        }
        Trainer trainerProfileByUsername = trainerDAO.findByUsername(username);
        return trainerProfileByUsername.getUser().getUsername().equals(username)
                && trainerProfileByUsername.getUser().getPassword().equals(password);

    }


    public Trainer passwordChange(Long id, String password) {
        Trainer trainerProfileById = findTrainerById(id);
        if (trainerProfileById.getUser().getPassword().equals(password)) {
            trainerProfileById.getUser().setPassword(passwordGenerator.generatePassword());
        }
        return trainerProfileById;
    }

    @Transactional
    public void activateTrainer(Long id) throws ProfileIsActiveException {
        Trainer trainerById = trainerDAO.findById(id);
        if (trainerById.getUser().isActive()) {
            throw new ProfileIsActiveException("Trainee Profile is already activated");
        }
        trainerById.getUser().setActive(true);
        trainerDAO.save(trainerById);

    }

    @Transactional
    public void deactivateTrainer(Long id) throws ProfileIsInActiveException {
        Trainer trainerById = trainerDAO.findById(id);
        if (!trainerById.getUser().isActive()) {
            throw new ProfileIsInActiveException("Trainee Profile is already inactive");
        }
        trainerById.getUser().setActive(false);
        trainerDAO.save(trainerById);


    }

    @Transactional
    public void updateTrainerProfile(Long id, String firstname, String lastname) {
        if (firstname == null || firstname.isEmpty() || lastname == null || lastname.isEmpty()) {
            logger.warn("Entity update profile failed");
            throw new ServiceException("Entity update profile failed, username and password are invalid");
        }
        logger.info("Entity update profile successfully");
        Trainer trainerById = trainerDAO.findById(id);
        trainerById.getUser().setFirstName(firstname);
        trainerById.getUser().setLastName(lastname);
        trainerById.getUser().setUsername(userProfileService.concatenateUsername(firstname, lastname));

    }

    @Transactional
    public void deleteTrainerProfileByUsername(String username) throws ServiceException {
        if (username == null || username.isEmpty()) {
            logger.warn("Trainer profile delete failed");
            throw new ServiceException("Delete Trainer Profile Failed, username is empty or null");
        }
        logger.info("Trainer profile deleted by username");
        Trainer trainerProfileByUsername = trainerDAO.findByUsername(username);
        trainerDAO.delete(trainerProfileByUsername);
    }


    @Transactional
    public void saveTrainer(Trainer trainer) throws ServiceException {
        if (trainer.getUser().getFirstName() == null
                || trainer.getUser().getFirstName().isEmpty()
                || trainer.getUser().getLastName() == null
                || trainer.getUser().getLastName().isEmpty()
                || trainer.getUser().getPassword() == null
                || trainer.getUser().getPassword().isEmpty()
                || trainer.getUser().getUsername() == null
                || trainer.getUser().getUsername().isEmpty()) {
            logger.warn("Trainer save failed");
            throw new ServiceException("Trainer save failed, trainer is invalid");
        }
        trainerDAO.save(trainer);
        logger.info("Trainer saved successfully {} ", trainer);

    }

    public void updateTrainer(Trainer trainer) throws ServiceException {
        if (trainer.getUser().getFirstName() == null
                || trainer.getUser().getFirstName().isEmpty()
                || trainer.getUser().getLastName() == null
                || trainer.getUser().getLastName().isEmpty()
                || trainer.getUser().getPassword() == null
                || trainer.getUser().getPassword().isEmpty()
                || trainer.getUser().getUsername() == null
                || trainer.getUser().getUsername().isEmpty()) {

            logger.warn("Trainer update failed");
            throw new ServiceException("Trainer update failed, trainer is invalid");
        }

        trainerDAO.update(trainer);
        logger.info("Trainer updated {} ", trainer);

    }

    public void deleteTrainer(Trainer trainer) throws ServiceException {
        if (trainer.getUser().getFirstName() == null
                || trainer.getUser().getFirstName().isEmpty()
                || trainer.getUser().getLastName() == null
                || trainer.getUser().getLastName().isEmpty()
                || trainer.getUser().getPassword() == null
                || trainer.getUser().getPassword().isEmpty()
                || trainer.getUser().getUsername() == null
                || trainer.getUser().getUsername().isEmpty()) {

            logger.warn("Trainer delete failed");
            throw new ServiceException("Trainer delete failed, trainer does not exist");
        }
        trainerDAO.delete(trainer);
        logger.info("Trainer id deleted");

    }

    public Trainer findTrainerById(Long id) throws ServiceException {
        if (id == null || id <= 0) {
            logger.warn("Entity find id failed");
            throw new ServiceException("Entity find by id failed, id is invalid");
        }
        logger.info("Found entity by id {} ", id);
        return trainerDAO.findById(id);
    }


    public  Trainer findByFirstName(String firstName) throws ServiceException {
        if (firstName == null || firstName.isEmpty()) {
            logger.warn("Trainer findByFirstName failed");
            throw new ServiceException("Trainee find by firstName failed, firstName is invalid");
        }
        logger.info("Found trainer by name {} ", firstName);
        return trainerDAO.findByFirstName(firstName);
    }

    public Trainer findByLastName(String lastName) throws ServiceException {
        if (lastName == null || lastName.isEmpty()) {
            logger.warn("Trainer findByLastName failed");
            throw new ServiceException("Trainee findByLastName failed, lastName is invalid");
        }
        logger.info("Found trainer by lastName {} ", lastName);
        return trainerDAO.findByLastName(lastName);
    }

    public Trainer findBySpecialization(Long specialization) throws ServiceException {
        if (specialization == null) {
            logger.warn("Trainer findBySpecialization failed");
            throw new ServiceException("Trainee findBySpecialization id failed, specialization id is invalid");
        }
        logger.info("Found trainer by specialization id {} ", specialization);
        return trainerDAO.findBySpecialization(specialization);

    }

    public List<Trainer> findAll() {
        return trainerDAO.findAll();
    }
}
