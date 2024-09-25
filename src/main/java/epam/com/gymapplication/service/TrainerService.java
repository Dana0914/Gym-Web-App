package epam.com.gymapplication.service;

import epam.com.gymapplication.dao.TraineeRepository;
import epam.com.gymapplication.dao.TrainerRepository;
import epam.com.gymapplication.dao.TrainingTypeRepository;
import epam.com.gymapplication.dao.UserRepository;
import epam.com.gymapplication.dto.TrainerRequestDTO;
import epam.com.gymapplication.dto.TrainerResponseDTO;
import epam.com.gymapplication.entity.Trainee;
import epam.com.gymapplication.entity.Trainer;
import epam.com.gymapplication.entity.TrainingType;
import epam.com.gymapplication.entity.User;
import epam.com.gymapplication.profile.PasswordGenerator;
import epam.com.gymapplication.profile.UserProfileService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



import java.util.List;
import java.util.Optional;



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

    @Autowired
    private ModelMapper modelMapper = new ModelMapper();





    public List<Trainer> findTrainersNotAssignedToTrainee(String username)  {
        Trainee traineeByUsername = traineeRepository.findByUsername(username).orElseThrow();
        logger.info("Found trainer list by username: " + username);

        return trainerRepository.findTrainersNotAssignedToTrainee(traineeByUsername.getUser().getUsername());


    }




    public TrainerResponseDTO createTrainerProfile(TrainerRequestDTO trainerRequestDTO)  {
        String username = userProfileService.concatenateUsername(trainerRequestDTO.getFirstname(),
                trainerRequestDTO.getLastname());
        String password = passwordGenerator.generatePassword();


        User toUserEntity = modelMapper.map(trainerRequestDTO, User.class);
        Trainer trainer = modelMapper.map(trainerRequestDTO, Trainer.class);


        toUserEntity.setUsername(username);
        toUserEntity.setPassword(password);

        User savedUser = userRepository.save(toUserEntity);


        TrainingType trainingTypeRepositoryById = trainingTypeRepository.
                findById(trainerRequestDTO.getSpecialization()).orElseThrow();

        trainer.setUser(savedUser);
        trainer.setTrainingType(trainingTypeRepositoryById);


        Trainer savedTrainer = trainerRepository.save(trainer);
        TrainerResponseDTO responseDTO = modelMapper.map(savedTrainer, TrainerResponseDTO.class);

        responseDTO.setUsername(savedTrainer.getUser().getUsername());
        responseDTO.setPassword(savedTrainer.getUser().getPassword());

        return responseDTO;
    }

    public Optional<Trainer> findTrainerProfileByUsername(Trainer trainer) {
        Trainer trainerByProfileUsername = trainerRepository.findByUsername(trainer.getUser().getUsername());
        if (trainerByProfileUsername == null) {
            return Optional.empty();
        }
        logger.info("Finding trainer by profile username: " + trainer.getUser().getUsername());
        return Optional.of(trainerByProfileUsername);


    }

    public boolean authenticateTrainerProfile(String username, String password)  {
        Trainer trainerProfileByUsername = trainerRepository.findByUsername(username);
        return trainerProfileByUsername.getUser().getUsername().equals(username)
                && trainerProfileByUsername.getUser().getPassword().equals(password);

    }


    public boolean changePassword(String username, String password, String oldPassword) {
        Trainer trainerProfileByUsername = trainerRepository.findByUsername(username);
        if (trainerProfileByUsername.getUser().getPassword().equals(oldPassword)) {
            trainerProfileByUsername.getUser().setPassword(password);
        }
        return false;

    }


    public void activateOrDeactivateTrainerStatus(Long id, boolean isActive) {
        Trainer trainerById = trainerRepository.findById(id).orElseThrow();
        if (trainerById.getUser().isActive()!= isActive) {
            trainerById.getUser().setActive(isActive);
            trainerRepository.save(trainerById);
        }
        System.out.println("Trainer is " + (isActive ? "active" : "inactive"));


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
                trainer.getTrainingType().getId(),
                trainer.getUser().getId());

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
