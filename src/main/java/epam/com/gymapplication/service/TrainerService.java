package epam.com.gymapplication.service;

import epam.com.gymapplication.dao.*;
import epam.com.gymapplication.dto.TraineeDTO;
import epam.com.gymapplication.dto.TrainerDTO;
import epam.com.gymapplication.dto.TrainingDTO;
import epam.com.gymapplication.entity.*;
import epam.com.gymapplication.profile.PasswordGenerator;
import epam.com.gymapplication.profile.UserProfileService;
import epam.com.gymapplication.utility.exception.BadRequestException;
import epam.com.gymapplication.utility.exception.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.time.LocalDate;
import java.util.List;



@Service
public class TrainerService {
    private static final Logger logger = LoggerFactory.getLogger(TrainerService.class);

    private final UserProfileService userProfileService;
    private final PasswordGenerator passwordGenerator;
    private final TrainerRepository trainerRepository;
    private final UserRepository userRepository;
    private final TrainingTypeRepository trainingTypeRepository;
    private final TraineeRepository traineeRepository;
    private final TrainingRepository trainingRepository;

    public TrainerService(UserProfileService userProfileService, PasswordGenerator passwordGenerator, TrainerRepository trainerRepository, UserRepository userRepository, TrainingTypeRepository trainingTypeRepository, TraineeRepository traineeRepository, TrainingRepository trainingRepository) {
        this.userProfileService = userProfileService;
        this.passwordGenerator = passwordGenerator;
        this.trainerRepository = trainerRepository;
        this.userRepository = userRepository;
        this.trainingTypeRepository = trainingTypeRepository;
        this.traineeRepository = traineeRepository;
        this.trainingRepository = trainingRepository;
    }


    public List<TrainerDTO> findUnassignedTraineesTrainersListByTraineeUsername(String username)  {
        List<Trainer> trainersListNotAssignedToTrainee = trainerRepository
                .findTrainersListNotAssignedToTraineeByUsername(username);

        List<TrainerDTO> trainerDTOs = trainersListNotAssignedToTrainee.stream().map(trainer -> {
            TrainerDTO trainerDTO = new TrainerDTO();
            trainerDTO.setUsername(trainer.getUser().getUsername());
            trainerDTO.setFirstname(trainer.getUser().getFirstName());
            trainerDTO.setLastname(trainer.getUser().getLastName());
            trainerDTO.setActive(trainer.getUser().getActive());
            trainerDTO.setSpecialization(trainer.getTrainingType().getId());
            return trainerDTO;

        }).toList();

        return trainerDTOs;

    }

    public TrainerDTO createTrainerProfile(TrainerDTO trainerDTO)  {

        String username = userProfileService.concatenateUsername(
                trainerDTO.getFirstname(),
                trainerDTO.getLastname());

        String password = passwordGenerator.generatePassword();


        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setFirstName(trainerDTO.getFirstname());
        user.setLastName(trainerDTO.getLastname());

        userRepository.save(user);

        logger.info("Persisted user {} in database", username);

        TrainingType trainingType = trainingTypeRepository
                .findById(trainerDTO.getSpecialization())
                .orElseThrow(() ->
                        new EntityNotFoundException("Entity not found by id " + trainerDTO.getSpecialization()));

        logger.info("Training type found by id {} ", trainingType);


        Trainer trainer = new Trainer();
        trainer.setUser(user);
        trainer.setTrainingType(trainingType);


        trainerRepository.save(trainer);

        logger.info("Persisted trainer {} in database", trainer);

        TrainerDTO trainerResponse = new TrainerDTO();
        trainerResponse.setUsername(username);
        trainerResponse.setPassword(password);


        return trainerResponse;


    }

    public TrainerDTO findTrainerProfileByUsername(String username) {
        Trainer trainerByProfileUsername = trainerRepository
                .findByUsername(username)
                .orElseThrow(() ->
                new EntityNotFoundException("Entity not found by username " + username));

        logger.info("Found trainer by profile username: " + trainerByProfileUsername);

        //map entity to DTO
        TrainerDTO trainerResponse = new TrainerDTO();
        trainerResponse.setFirstname(trainerByProfileUsername.getUser().getFirstName());
        trainerResponse.setLastname(trainerByProfileUsername.getUser().getLastName());
        trainerResponse.setSpecialization(trainerByProfileUsername.getTrainingType().getId());
        trainerResponse.setActive(trainerByProfileUsername.getUser().getActive());

        // Map trainers using the TrainerDTO
        List<TraineeDTO> traineeDTOs = trainerByProfileUsername.getTrainees().stream()
                .map(trainer -> {

                    logger.info("trainer info {}", trainer);

                    TraineeDTO traineeDto = new TraineeDTO();
                    traineeDto.setUsername(trainer.getUser().getUsername());
                    traineeDto.setFirstname(trainer.getUser().getFirstName());
                    traineeDto.setLastname(trainer.getUser().getLastName());

                    return traineeDto;
                })
                .toList();

        trainerResponse.setTrainees(traineeDTOs);

        return trainerResponse;


    }

    public boolean authenticateTrainerProfile(String username, String password)  {
        Trainer trainerProfileByUsername = trainerRepository
                .findByUsername(username)
                .orElseThrow(() ->
                new EntityNotFoundException("Entity not found by username " + username));

        if (trainerProfileByUsername.getUser().getUsername().equals(username)
                && trainerProfileByUsername.getUser().getPassword().equals(password)) {
            return true;
        }
        throw new BadRequestException("Password or username is invalid");

    }


    public boolean changePassword(TrainerDTO trainerDTO) {
        String username = trainerDTO.getUsername();
        String oldPassword = trainerDTO.getOldPassword();
        String newPassword = trainerDTO.getNewPassword();

        Trainer trainerProfileByUsername = trainerRepository
                .findByUsername(username)
                .orElseThrow(() ->
                new EntityNotFoundException("Trainer not found by username: " + username));

        logger.info("Found Trainer Profile by Username {} ", trainerProfileByUsername);


        if (trainerProfileByUsername.getUser().getPassword().equals(oldPassword)) {
            trainerProfileByUsername.getUser().setPassword(newPassword);

            trainerRepository.save(trainerProfileByUsername);
            logger.info("Changed password for trainer {} ", trainerProfileByUsername);

            return true;
        }
        throw new BadRequestException("Password or username is invalid");

    }


    public void activateOrDeactivateTrainerStatus(String username, Boolean isActive) {
        Trainer trainerById = trainerRepository
                .findByUsername(username)
                .orElseThrow(() ->
                new EntityNotFoundException("Entity not found by username " + username));

        if (trainerById.getUser().getActive()!= isActive) {
            trainerById.getUser().setActive(isActive);

            trainerRepository.save(trainerById);

            logger.info("Persisted trainer by status {}", trainerById);
        }
        throw new BadRequestException("Status is not active or deactivated " + isActive);




    }


    @Transactional
    public TrainerDTO updateTrainerProfile(Long id, TrainerDTO trainerDTO) {
        Trainer trainerById = trainerRepository
                .findById(id)
                .orElseThrow(() ->
                new EntityNotFoundException("Entity not found by id " + id));

        logger.info("Found trainer profile by id {}", trainerById);


        trainerById.getUser().setFirstName(trainerDTO.getFirstname());
        trainerById.getUser().setLastName(trainerDTO.getLastname());
        trainerById.getUser().setUsername(userProfileService.concatenateUsername(trainerDTO.getFirstname(),
                trainerDTO.getLastname()));
        trainerById.getUser().setActive(trainerDTO.getActive());

        TrainingType trainingType = trainingTypeRepository
                .findById(trainerDTO.getSpecialization())
                .orElseThrow(() ->
                new EntityNotFoundException("Entity not found by id " + trainerDTO.getSpecialization()));

        logger.info("Training type found by id {} ", trainingType);

        trainerById.setTrainingType(trainingType);

        trainerRepository.save(trainerById);
        logger.info("Persisted trainer profile {} in database", trainerById);


        // Map trainers using the TrainerDTO
        List<TraineeDTO> traineeDTOs = trainerById.getTrainees().stream()
                .map(trainee -> {

                    logger.info("trainee info {}", trainee);

                    TraineeDTO traineeDto = new TraineeDTO();
                    traineeDto.setUsername(trainee.getUser().getUsername());
                    traineeDto.setFirstname(trainee.getUser().getFirstName());
                    traineeDto.setLastname(trainee.getUser().getLastName());

                    return traineeDto;
                })
                .toList();

        trainerDTO.setTrainees(traineeDTOs);


        return trainerDTO;

    }


    @Transactional
    public void deleteTrainerProfileByUsername(String username)  {
        Trainer trainerProfileByUsername = trainerRepository
                .findByUsername(username)
                .orElseThrow(() ->
                new EntityNotFoundException("Entity not found by username " + username));

        trainerRepository.delete(trainerProfileByUsername);
        logger.info("Trainer profile deleted by username {} ", username);
    }



    public void saveTrainer(Trainer trainer) {
        trainerRepository.save(trainer);
        logger.info("Persisted trainer {} ", trainer);

    }

    @Transactional
    public void deleteById(Long id)  {
        trainerRepository.deleteById(id);
        logger.info("Trainer id deleted");

    }

    public Trainer findTrainerById(Long id)  {
        logger.info("Found entity by id {} ", id);

        return trainerRepository
                .findById(id)
                .orElseThrow(() ->
                new EntityNotFoundException("Entity not found by id " + id));

    }


    public Trainer findByFirstName(String firstName) {
        Trainer trainerByFirstname = trainerRepository
                .findByFirstName(firstName)
                .orElseThrow(() ->
                new EntityNotFoundException("Entity not found by firstname " + firstName));

        logger.info("Found entity by name {} ", trainerByFirstname);
        return trainerByFirstname;
    }

    public Trainer findByLastName(String lastName)  {
        Trainer trainerByLastname = trainerRepository
                .findByLastName(lastName)
                .orElseThrow(() ->
                new EntityNotFoundException("Entity not found by lastname " + lastName));

        logger.info("Found entity by lastName {} ", trainerByLastname);
        return trainerByLastname;
    }

    public Trainer findByUsername(String username)  {
        Trainer trainerByUsername = trainerRepository
                .findByUsername(username)
                .orElseThrow(() ->
                new EntityNotFoundException("Entity not found by username " + username));

        logger.info("Found entity by username {} ", trainerByUsername);
        return trainerByUsername;
    }

    public List<Trainer> findAll() {
        return (List<Trainer>) trainerRepository.findAll();
    }

    public List<TrainingDTO> getTrainersTrainingList(String username, LocalDate from, LocalDate to,
                                                     String traineeName, String trainingType)  {

        Trainer trainerByUsername = trainerRepository
                .findByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException("Trainer not found by username " + username));

        logger.info("Found trainer by name {}", trainerByUsername);

        Trainee traineeByName = traineeRepository
                .findByFirstName(traineeName)
                .orElseThrow(() -> new EntityNotFoundException("Trainee not found by name " + traineeName));

        logger.info("Found trainer by name {}", trainerByUsername);

        Training trainingByType = trainingRepository
                .findByTrainingType(trainingType)
                .orElseThrow(() -> new EntityNotFoundException("Training not found by type " + trainingType));

        logger.info("Found training by type {}", trainingByType);

        TrainingDTO trainingRequestDTO = new TrainingDTO();
        trainingRequestDTO.setTrainer(trainerByUsername);
        trainingRequestDTO.setTrainee(traineeByName);


        List<Training> trainersTrainingList = trainerRepository.findTrainersTrainingList(username, from,
                to, traineeName, trainingType);

        logger.info("Fetching training list for username: {}", trainerByUsername);

        List<TrainingDTO> trainingDTOS = trainersTrainingList.stream().map(training ->
        {
            TrainingDTO trainingResponseDTO = new TrainingDTO();
            trainingResponseDTO.setTrainerName(trainerByUsername.getUser().getUsername());
            trainingResponseDTO.setFrom(training.getTrainingDate());
            trainingResponseDTO.setTo(training.getTrainingDate());
            trainingResponseDTO.setTrainingType(training.getTrainingType().getTrainingTypeName());
            trainingResponseDTO.setTrainingName(trainingByType.getTrainingName());

            return trainingResponseDTO;

        }).toList();

        return trainingDTOS;
    }
}
