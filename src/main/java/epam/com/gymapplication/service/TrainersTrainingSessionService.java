package epam.com.gymapplication.service;


import epam.com.gymapplication.dao.*;
import epam.com.gymapplication.dto.TrainingDTO;
import epam.com.gymapplication.entity.*;
import epam.com.gymapplication.profile.PasswordGenerator;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;


@Service
public class TrainersTrainingSessionService {
    private static final Logger logger = LoggerFactory.getLogger(TrainersTrainingSessionService.class);

    private final TrainerRepository trainerRepository;
    private final TrainingRepository trainingRepository;
    private final UserRepository userRepository;
    private final PasswordGenerator passwordGenerator;
    private final PasswordEncoder passwordEncoder;
    private final TrainingTypeRepository trainingTypeRepository;
    private final TraineeRepository traineeRepository;


    private final RestTemplate restTemplate;
    private static final String ADDRESS_SERVICE_URL = "http://localhost:8763/api/workload/trainers/trainings";


    public TrainersTrainingSessionService(TrainerRepository trainerRepository,
                                          TrainingRepository trainingRepository,
                                          UserRepository userRepository,
                                          PasswordGenerator passwordGenerator,
                                          PasswordEncoder passwordEncoder,
                                          TrainingTypeRepository trainingTypeRepository,
                                          TraineeRepository traineeRepository,
                                          RestTemplate restTemplate) {

        this.trainerRepository = trainerRepository;
        this.trainingRepository = trainingRepository;
        this.userRepository = userRepository;
        this.passwordGenerator = passwordGenerator;
        this.passwordEncoder = passwordEncoder;
        this.trainingTypeRepository = trainingTypeRepository;
        this.traineeRepository = traineeRepository;
        this.restTemplate = restTemplate;
    }

    public void addTraining(TrainingDTO trainingDTO) {
        User user = new User();
        user.setUsername(trainingDTO.getTrainerUsername());
        user.setFirstName(trainingDTO.getTrainerFirstname());
        user.setLastName(trainingDTO.getTrainerLastname());
        user.setPassword(passwordEncoder.encode(passwordGenerator.generatePassword()));
        user.setActive(trainingDTO.getIsActive());

        Trainer trainer = new Trainer();
        trainer.setUser(user);

        TrainingType trainingType = trainingTypeRepository
                .findById(1L)
                .orElseThrow(() ->
                        new RuntimeException("TrainingType not found with ID"));
        logger.info("TrainingType ID: {}", trainingType.getId());

        trainer.setSpecialization(trainingType.getId());

        userRepository.save(user);

        logger.info("User added successfully {} ", user);

        trainerRepository.save(trainer);

        logger.info("Trainer added successfully {} ", trainer);

        Training training = new Training();
        training.setTrainingDate(trainingDTO.getTrainingDate());
        training.setTrainingDuration(training.getTrainingDuration());
        training.setActionType(trainingDTO.getActionType());


        training.setTrainer(trainer);
        training.setTrainingName("stretching");

        Trainee trainee = traineeRepository
                .findById(2L)
                .orElseThrow(() ->
                        new RuntimeException("Trainee not found with ID"));
        logger.info("Trainee ID: {}", trainee.getId());

        training.setTrainee(trainee);

        trainingRepository.save(training);

        logger.info("Training added successfully {} ", training);

        notifySecondaryService(trainingDTO);
    }



    public void deleteTraining(TrainingDTO trainingDTO) {
        User user = userRepository
                .findByUsername(trainingDTO
                        .getTrainerUsername())
                .orElseThrow(() ->
                        new RuntimeException("User not found by username"));

        logger.info("User username: {}", user.getUsername());

        Trainer trainer = trainerRepository
                .findByUsername(trainingDTO
                        .getTrainerUsername())
                .orElseThrow(() ->
                        new RuntimeException("Trainer not found by username"));
        logger.info("Trainer username: {}", trainer.getUser().getUsername());

        Training training = trainingRepository
                .findByTrainingDate(trainingDTO
                        .getTrainingDate())
                .orElseThrow(() ->
                        new RuntimeException("Training not found by date"));
        logger.info("Training date: {}", training.getTrainingDate());


        userRepository.delete(user);
        logger.info("User deleted successfully {} ", user);

        trainerRepository.delete(trainer);
        logger.info("Trainer deleted successfully {} ", trainer);

        trainingRepository.delete(training);
        logger.info("Training deleted successfully {} ", training);

        notifySecondaryService(trainingDTO);
    }

    // Method to send workload update to the secondary microservice
    @CircuitBreaker(name = "myCircuitBreaker", fallbackMethod = "fallbackMethod")
    public ResponseEntity<String> notifySecondaryService(TrainingDTO trainingDTO) {
        logger.info("Trying to call secondary microservice...");

        // Call the secondary microservice and send the request
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<TrainingDTO> entity = new HttpEntity<>(trainingDTO, headers);


        // Send the POST request to the secondary microservice
        ResponseEntity<String> response = restTemplate.exchange(ADDRESS_SERVICE_URL,
                HttpMethod.POST,
                entity,
                String.class);


        if (response.getStatusCode() == HttpStatus.OK) {
            logger.info("Successfully call secondary microservice {}", response.getBody());
        } else {
            logger.info("Failed to call secondary microservice {}", response.getBody());
        }
        return response;
    }

    public ResponseEntity<String> fallBackMethod(TrainingDTO trainingDTO, Throwable throwable) {
        logger.error("Falling back exception {}", throwable.getMessage());

        trainingDTO = new TrainingDTO(
                "Unavailable.Trainer",
                "Unavailable",
                "Trainer",
                false,
                0,
                LocalDate.now(),
                ActionType.ADD);

        logger.info("TrainingDTO : {}", trainingDTO);

        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body("Service is unavailable, please try again");

    }



}
