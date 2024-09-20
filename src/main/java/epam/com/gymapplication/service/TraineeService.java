package epam.com.gymapplication.service;

import epam.com.gymapplication.customexception.ProfileIsActiveException;
import epam.com.gymapplication.customexception.ProfileIsInActiveException;
import epam.com.gymapplication.dao.TraineeRepository;
import epam.com.gymapplication.dao.TrainerRepository;
import epam.com.gymapplication.dao.UserRepository;
import epam.com.gymapplication.model.Trainee;
import epam.com.gymapplication.model.Trainer;
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
public class TraineeService {
    private static final Logger logger = LoggerFactory.getLogger(TraineeService.class);

    @Autowired
    private TraineeRepository traineeRepository;

    @Autowired
    private UserProfileService userProfileService;

    @Autowired
    private PasswordGenerator passwordGenerator;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TrainerRepository trainerRepository;



    public void updateTraineesTrainerList(Set<Trainer> trainers, String username)  {
        Trainee traineeByUsername = traineeRepository.findByUsername(username).orElseThrow();

        logger.info("Updated trainees trainers list");
        traineeRepository.updateTraineesTrainerList(trainers, traineeByUsername.getUser().getUsername());

    }



    public void assignTraineeToTrainer(Long traineeId, Long trainerId) {
        Trainee trainee = traineeRepository.findById(traineeId).orElseThrow();
        Trainer trainer = trainerRepository.findById(trainerId).orElseThrow();


        trainee.getTrainers().add(trainer);
        trainer.getTrainees().add(trainee);


        trainerRepository.save(trainer);
        traineeRepository.save(trainee);
    }




    public Trainee createTraineeProfile(Trainee trainee, User user)  {

        String username = userProfileService.concatenateUsername(user.getFirstName(), user.getLastName());
        String password = passwordGenerator.generatePassword();


        User savedUser = new User();
        savedUser.setFirstName(user.getFirstName());
        savedUser.setLastName(user.getLastName());
        savedUser.setUsername(username);
        savedUser.setPassword(password);
        savedUser.setActive(user.isActive());


        userRepository.save(savedUser);


        Trainee savedTrainee = new Trainee();
        savedTrainee.setAddress(trainee.getAddress());
        savedTrainee.setDateOfBirth(trainee.getDateOfBirth());
        savedTrainee.setUser(savedUser);


        traineeRepository.save(savedTrainee);

        return savedTrainee;
    }

    public Trainee findTraineeProfileByUsername(String username) {
        logger.info("Find Trainee Profile by Username");
        return traineeRepository.findByUsername(username).orElseThrow();

    }

    public boolean authenticateTraineeProfile(String username, String password) {
        Trainee traineeProfileByUsername = traineeRepository.findByUsername(username).orElseThrow();
        return traineeProfileByUsername.getUser().getUsername().equals(username)
                && traineeProfileByUsername.getUser().getPassword().equals(password);

    }

    public Trainee changePassword(Long id, String password) {
        Trainee traineeProfileById = traineeRepository.findById(id).orElseThrow();
        if (traineeProfileById.getUser().getPassword().equals(password)) {
            traineeProfileById.getUser().setPassword(passwordGenerator.generatePassword());
        }
        return traineeProfileById;
    }


    public void activateTrainee(Long id) throws ProfileIsActiveException {
        Trainee traineeById = traineeRepository.findById(id).orElseThrow();
        if (traineeById.getUser().isActive()) {
            throw new ProfileIsActiveException("Trainee Profile is already activated");
        }
        traineeById.getUser().setActive(true);
        traineeRepository.save(traineeById);

    }


    public void deactivateTrainee(Long id) throws ProfileIsInActiveException {
        Optional<Trainee> traineeById = traineeRepository.findById(id);
        if (!traineeById.get().getUser().isActive()) {
            throw new ProfileIsInActiveException("Trainee Profile is already inactive");
        }
        traineeById.get().getUser().setActive(false);
        traineeRepository.save(traineeById.orElseThrow());


    }


    public void updateTraineeProfile(Long id, String firstname, String lastname) {
        Optional<Trainee> traineeById = traineeRepository.findById(id);

        traineeById.get().getUser().setFirstName(firstname);
        traineeById.get().getUser().setLastName(lastname);
        traineeById.get().getUser().setUsername(userProfileService.concatenateUsername(firstname, lastname));

        logger.info("Entity update profile successfully");
        traineeRepository.save(traineeById.orElseThrow());

    }


    public void deleteTraineeProfileByUsername(String username)  {
        Trainee traineeProfileByUsername = traineeRepository.findByUsername(username).orElseThrow();
        logger.info("Trainee profile deleted by username");
        traineeRepository.delete(traineeProfileByUsername);
    }



    public List<Trainee> findAll() {
        return (List<Trainee>) traineeRepository.findAll();
    }




    public void saveTrainee(Trainee trainee) {
        traineeRepository.save(trainee);
        logger.info("Trainee saved {}", trainee);

    }

    public void updateTrainee(Trainee trainee)  {
        Trainee findTraineeById = traineeRepository.findById(trainee.getId()).orElseThrow();
        traineeRepository.updateTrainee(findTraineeById.getId(),
                trainee.getUser().getId(),
                trainee.getAddress(),
                trainee.getDateOfBirth());

        logger.info("Trainee updated {}", trainee);

    }



    public void deleteTraineeById(Long id)  {
        traineeRepository.deleteById(id);
        logger.info("Trainee deleted {}", id);

    }

    public Trainee findTraineeById(Long id) {
        logger.info("Found trainee by id {} ", id);
        return traineeRepository.findById(id).orElseThrow();

    }



    public Trainee findByFirstName(String firstName)  {
        logger.info("Trainee found by name {}", firstName);
        return traineeRepository.findByFirstName(firstName).orElseThrow();

    }

    public Trainee findByLastName(String lastName)  {
        logger.info("Trainee found by lastName {}", lastName);
        return traineeRepository.findByLastName(lastName).orElseThrow();

    }

    public Trainee findByUsername(String username)  {
        logger.info("Trainee found by username {}", username);
        return traineeRepository.findByUsername(username).orElseThrow();

    }
}
