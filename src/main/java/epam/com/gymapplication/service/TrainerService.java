package epam.com.gymapplication.service;

import epam.com.gymapplication.customexception.ProfileIsActiveException;
import epam.com.gymapplication.customexception.ProfileIsInActiveException;
import epam.com.gymapplication.dao.TraineeRepository;
import epam.com.gymapplication.dao.TrainerRepository;
import epam.com.gymapplication.dao.TrainingTypeRepository;
import epam.com.gymapplication.dao.UserRepository;
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



import java.util.List;
import java.util.Optional;
import java.util.Set;


@Service
public class TrainerService {
    private static final Logger logger = LoggerFactory.getLogger(TrainerService.class);


    @Autowired
    private UserProfileService userProfileService;

    @Autowired
    private PasswordGenerator passwordGenerator;

    @Autowired
    private TraineeRepository traineeRepository;

    @Autowired
    private TrainerRepository trainerRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TrainingTypeRepository trainingTypeRepository;





    public List<Trainer> findTrainersNotAssignedToTrainee(String username)  {
        Trainee traineeByUsername = traineeRepository.findByUsername(username).orElseThrow();
        logger.info("Finding trainer list by username: " + username);
        return trainerRepository.findTrainersNotAssignedToTrainee(traineeByUsername.getUser().getUsername());


    }




    public Trainer createTrainerProfile(Trainer trainer, User user, TrainingType trainingType)  {
        String username = userProfileService.concatenateUsername(user.getFirstName(), user.getLastName());
        String password = passwordGenerator.generatePassword();



        user.setFirstName(user.getFirstName());
        user.setLastName(user.getLastName());
        user.setUsername(username);
        user.setPassword(password);
        user.setActive(user.isActive());

        userRepository.save(user);


        trainingType.setTrainingTypeName(trainingType.getTrainingTypeName());

        trainingTypeRepository.save(trainingType);


        trainer.setUser(user);
        trainer.setTrainingType(trainingType);

        trainerRepository.save(trainer);

        return trainer;
    }

    public Optional<Trainer> findTrainerProfileByUsername(Trainer trainer) {
        Trainer trainerByProfileUsername = trainerRepository.findByUsername(trainer.getUser().getUsername());
        if (trainerByProfileUsername == null) {
            return Optional.empty();
        }
        logger.info("Finding trainer by profile username: " + trainer.getUser().getUsername());
        return Optional.of(trainerByProfileUsername);


    }

    public boolean authenticateTraineeProfile(String username, String password)  {
        Trainer trainerProfileByUsername = trainerRepository.findByUsername(username);
        return trainerProfileByUsername.getUser().getUsername().equals(username)
                && trainerProfileByUsername.getUser().getPassword().equals(password);

    }


    public Trainer passwordChange(Long id, String password) {
        Trainer trainerProfileById = trainerRepository.findById(id).orElseThrow();
        if (trainerProfileById.getUser().getPassword().equals(password)) {
            trainerProfileById.getUser().setPassword(passwordGenerator.generatePassword());
        }
        return trainerProfileById;
    }


    public void activateTrainer(Long id) throws ProfileIsActiveException {
        Trainer trainerById = trainerRepository.findById(id).orElseThrow();
        if (trainerById.getUser().isActive()) {
            throw new ProfileIsActiveException("Trainee Profile is already activated");
        }
        trainerById.getUser().setActive(true);
        trainerRepository.save(trainerById);

    }


    public void deactivateTrainer(Long id) throws ProfileIsInActiveException {
        Trainer trainerById = trainerRepository.findById(id).orElseThrow();
        if (!trainerById.getUser().isActive()) {
            throw new ProfileIsInActiveException("Trainee Profile is already inactive");
        }
        trainerById.getUser().setActive(false);
        trainerRepository.save(trainerById);


    }


    public void updateTrainerProfile(Long id, String firstname, String lastname) {
        logger.info("Entity update profile successfully");
        Trainer trainerById = trainerRepository.findById(id).orElseThrow();

        trainerById.getUser().setFirstName(firstname);
        trainerById.getUser().setLastName(lastname);
        trainerById.getUser().setUsername(userProfileService.concatenateUsername(firstname, lastname));

        trainerRepository.save(trainerById);

    }


    public void deleteTrainerProfileByUsername(String username)  {
        Trainer trainerProfileByUsername = trainerRepository.findByUsername(username);
        trainerRepository.delete(trainerProfileByUsername);
        logger.info("Trainer profile deleted by username {} ", username);
    }



    public void saveTrainer(Trainer trainer) {
        trainerRepository.save(trainer);
        logger.info("Trainer saved successfully");

    }

    public void updateTrainer(Trainer trainer) {
        Optional<Trainer> byId = trainerRepository.findById(trainer.getId());
        trainerRepository.updateTrainee(byId.orElseThrow().getId(),
                trainer.getUser().getId(),
                trainer.getTrainingType().getId());

        logger.info("Trainer updated");

    }

    public void deleteById(Long id)  {
        trainerRepository.deleteById(id);
        logger.info("Trainer id deleted");

    }

    public Trainer findTrainerById(Long id)  {
        logger.info("Found entity by id {} ", id);
        return trainerRepository.findById(id).orElseThrow();

    }


    public Optional<Trainer> findByFirstName(String firstName) {
        Trainer trainerByFirstname = trainerRepository.findByFirstName(firstName);
        if (trainerByFirstname == null) {
            return Optional.empty();
        }
        logger.info("Found entity by name {} ", firstName);
        return Optional.of(trainerByFirstname);
    }

    public Optional<Trainer> findByLastName(String lastName)  {
        Trainer trainerByLastname = trainerRepository.findByLastName(lastName);
        if (trainerByLastname == null) {
            return Optional.empty();
        }
        logger.info("Found entity by lastName {} ", lastName);
        return Optional.of(trainerByLastname);
    }

    public Optional<Trainer> findByUsername(String username)  {
        Trainer trainerByUsername = trainerRepository.findByUsername(username);
        if (trainerByUsername == null) {
            return Optional.empty();
        }
        logger.info("Found entity by username {} ", username);
        return Optional.of(trainerByUsername);
    }

    public List<Trainer> findAll() {
        return (List<Trainer>) trainerRepository.findAll();
    }
}
