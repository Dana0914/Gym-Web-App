package epam.com.gymapplication.service;

import epam.com.gymapplication.dao.TraineeRepository;
import epam.com.gymapplication.dao.TrainerRepository;
import epam.com.gymapplication.dao.UserRepository;
import epam.com.gymapplication.dto.TraineeProfileResponseDTO;
import epam.com.gymapplication.entity.Trainee;
import epam.com.gymapplication.entity.Trainer;
import epam.com.gymapplication.entity.User;
import epam.com.gymapplication.model.TraineeRequest;
import epam.com.gymapplication.model.TraineeResponse;
import epam.com.gymapplication.profile.PasswordGenerator;
import epam.com.gymapplication.profile.UserProfileService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;
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

    @Autowired
    private ModelMapper modelMapper;



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




    public TraineeResponse createTraineeProfile(TraineeRequest traineeRequest)  {
        String username = userProfileService.concatenateUsername(
                traineeRequest.getFirstName(),
                traineeRequest.getLastName());

        String password = passwordGenerator.generatePassword();

        User toUserEntity = modelMapper.map(traineeRequest, User.class);
        Trainee trainee = modelMapper.map(traineeRequest, Trainee.class);


        toUserEntity.setUsername(username);
        toUserEntity.setPassword(password);


        User savedUser = userRepository.save(toUserEntity);

        trainee.setUser(savedUser);
        Trainee savedTrainee = traineeRepository.save(trainee);

        TraineeResponse traineeResponse = modelMapper.map(savedTrainee, TraineeResponse.class);

        traineeResponse.setPassword(savedTrainee.getUser().getPassword());
        traineeResponse.setUsername(savedTrainee.getUser().getUsername());

        return traineeResponse;

    }

    public TraineeProfileResponseDTO findTraineeProfileByUsername(String username) {
        logger.info("Find Trainee Profile by Username");
        Trainee traineeByUsername = traineeRepository.findByUsername(username).orElseThrow();
        TraineeProfileResponseDTO traineeProfileResponseDTO = new TraineeProfileResponseDTO();
        traineeProfileResponseDTO.setFirstname(traineeByUsername.getUser().getFirstName());
        traineeProfileResponseDTO.setLastname(traineeByUsername.getUser().getLastName());
        traineeProfileResponseDTO.setAddress(traineeByUsername.getAddress());
        traineeProfileResponseDTO.setDateOfBirth(traineeByUsername.getDateOfBirth());
        traineeProfileResponseDTO.setTrainers(traineeByUsername.getTrainers());
        traineeProfileResponseDTO.setActive(traineeByUsername.getUser().isActive());


        return traineeProfileResponseDTO;


    }

    public boolean authenticateTraineeProfile(String username, String password) {
        Trainee traineeProfileByUsername = traineeRepository.findByUsername(username).orElseThrow();

        return traineeProfileByUsername.getUser().getUsername().equals(username) &&
                traineeProfileByUsername.getUser().getPassword().equals(password);

    }

    public boolean changePassword(String username, String password, String oldPassword) {
        Trainee traineeProfileByUsername = traineeRepository.findByUsername(username).orElseThrow();
        if (traineeProfileByUsername.getUser().getPassword().equals(oldPassword)) {
            traineeProfileByUsername.getUser().setPassword(password);
        }
        return false;

    }


    public void activateOrDeactivateTraineeStatus(Long id, boolean isActive) {
        Trainee traineeById = traineeRepository.findById(id).orElseThrow();
        if (traineeById.getUser().isActive()!= isActive) {
            traineeById.getUser().setActive(isActive);
            traineeRepository.save(traineeById);
        }
        System.out.println("Trainee is " + (isActive ? "active" : "inactive"));


    }



    public void updateTraineeProfile(Long id, String firstname, String lastname) {
        Trainee traineeById = traineeRepository.findById(id).orElseThrow();

        traineeById.getUser().setFirstName(firstname);
        traineeById.getUser().setLastName(lastname);
        traineeById.getUser().setUsername(userProfileService.concatenateUsername(firstname, lastname));

        logger.info("Entity update profile successfully");
        traineeRepository.save(traineeById);

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
        logger.info("Trainee saved");

    }

    public void updateTrainee(Trainee trainee)  {
        Trainee findTraineeById = traineeRepository.findById(trainee.getId()).orElseThrow();
        logger.info("Trainee found by id {}", trainee.getId());

        traineeRepository.updateTrainee(findTraineeById.getId(),
                trainee.getUser().getId(),
                trainee.getAddress(),
                trainee.getDateOfBirth());

        logger.info("Trainee updated");

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
