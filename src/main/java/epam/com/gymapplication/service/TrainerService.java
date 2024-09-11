package epam.com.gymapplication.service;

import epam.com.gymapplication.customexception.ProfileIsActiveException;
import epam.com.gymapplication.customexception.ProfileIsInActiveException;
import epam.com.gymapplication.customexception.ServiceException;
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
import java.util.Optional;


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

    @Transactional
    public Trainer createTrainerProfile(Trainer trainer, User user, TrainingType trainingType) throws ServiceException {
        if (trainer == null || user == null || trainingType == null) {
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
        trainingType1.setTrainingTypeName("aerobics");

        trainingTypeDAO.save(trainingType1);


        Trainer trainer1 = new Trainer();
        trainer1.setUserId(user1.getId());
        trainer1.setUser(user1);
        trainer1.setTrainingType(trainingType1);
        trainer1.setSpecialization(trainingType1.getId());

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

    public boolean authenticateTrainerProfile(Trainer trainer) throws ServiceException {
        if (trainer.getUser().getUsername() == null || trainer.getUser().getUsername().isEmpty()
        || trainer.getUser().getPassword() == null || trainer.getUser().getPassword().isEmpty()) {
            logger.warn("Entity authentication failed");
            throw new ServiceException("Entity authentication failed, username and password are invalid");

        }
        Trainer trainerProfileByUsername = trainerDAO.findByUsername(trainer.getUser().getUsername());
        return trainerProfileByUsername.getUser().getUsername().equals(trainer.getUser().getUsername())
                && trainerProfileByUsername.getUser().getPassword().equals(trainer.getUser().getPassword());

    }

    public Trainer passwordChange(Trainer trainer) throws ServiceException {
        if (trainer.getUser().getUsername() == null || trainer.getUser().getUsername().isEmpty()
        || trainer.getUser().getPassword() == null || trainer.getUser().getPassword().isEmpty()) {
            logger.warn("Entity password change failed");
            throw new ServiceException("Entity password change failed, username and password are invalid");
        }

        Trainer trainerProfileByUsername = trainerDAO.findByUsername(trainer.getUser().getUsername());
        if (trainerProfileByUsername.getUser().getPassword().equals(trainer.getUser().getPassword())) {
            trainerProfileByUsername.getUser().setPassword(passwordGenerator.generatePassword());
        }
        return trainerProfileByUsername;
    }

    @Transactional
    public void activateTrainee(Trainer trainer) throws ProfileIsActiveException {
        if (trainer.getUser().isActive()) {
            throw new ProfileIsActiveException("Trainer Profile is already activated");
        }
        trainer.getUser().setActive(true);
        trainerDAO.save(trainer);

    }

    @Transactional
    public void deactivateTrainer(Trainer trainer) throws ProfileIsInActiveException {
        if (!trainer.getUser().isActive()) {
            throw new ProfileIsInActiveException("Trainer Profile is already inactive");
        }
        trainer.getUser().setActive(false);
        trainerDAO.save(trainer);


    }

    @Transactional
    public void updateTrainerProfile(Trainer trainer) {
        if (trainer.getUser().getFirstName() == null || trainer.getUser().getFirstName().isEmpty()
        || trainer.getUser().getLastName() == null || trainer.getUser().getLastName().isEmpty()) {
            logger.warn("Entity update failed");
            throw new ServiceException("Entity update failed, username and password are invalid");
        }
        Trainer trainerById = trainerDAO.findById(trainer.getUser().getId());
        trainerById.getUser().setFirstName(trainer.getUser().getFirstName());
        trainerById.getUser().setLastName(trainer.getUser().getLastName());

    }

    @Transactional
    public void deleteTrainerProfileByUsername(Trainer trainer) throws ServiceException {
        if (trainer.getUser().getUsername() == null || trainer.getUser().getUsername().isEmpty()) {
            logger.warn("Trainer profile delete failed");
            throw new ServiceException("Delete Trainer Profile Failed, username is empty or null");
        }
        logger.info("Trainer profile delete successful {} ", trainer);
        Trainer trainerProfileByUsername = trainerDAO.findByUsername(trainer.getUser().getUsername());
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
                || trainer.getUser().getUsername().isEmpty()
                || trainer.getSpecialization() == null
                || trainer.getSpecialization() <= 0) {
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
                || trainer.getUser().getUsername().isEmpty()
                || trainer.getSpecialization() == null
                || trainer.getSpecialization() <= 0) {
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
                || trainer.getUser().getUsername().isEmpty()
                || trainer.getSpecialization() == null
                || trainer.getSpecialization() <= 0) {
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
        Trainer byId = trainerDAO.findById(id);
        return byId;
    }


    public  Optional<Trainer> findByFirstName(String firstName) throws ServiceException {
        if (firstName == null || firstName.isEmpty()) {
            logger.warn("Trainer findByFirstName failed");
            throw new ServiceException("Trainee find by firstName failed, firstName is invalid");
        }
        logger.info("Found trainer by name {} ", firstName);
        Optional<Trainer> byFirstName = trainerDAO.findByFirstName(firstName);
        return Optional.of(byFirstName.orElseThrow());
    }

    public Optional<Trainer> findByLastName(String lastName) throws ServiceException {
        if (lastName == null || lastName.isEmpty()) {
            logger.warn("Trainer findByLastName failed");
            throw new ServiceException("Trainee findByLastName failed, lastName is invalid");
        }
        logger.info("Found trainer by lastName {} ", lastName);
        Optional<Trainer> byLastName = trainerDAO.findByLastName(lastName);
        return Optional.of(byLastName.orElseThrow());
    }

    public Optional<Trainer> findBySpecialization(Long specialization) throws ServiceException {
        if (specialization == null || specialization <= 0) {
            logger.warn("Trainer findBySpecialization failed");
            throw new ServiceException("Trainee findBySpecialization id failed, specialization id is invalid");
        }
        logger.info("Found trainer by specialization id {} ", specialization);
        Optional<Trainer> bySpecialization = trainerDAO.findBySpecialization(specialization);
        return Optional.of(bySpecialization.orElseThrow());
    }

    public List<Trainer> findAll() {
        return trainerDAO.findAll();
    }
}
