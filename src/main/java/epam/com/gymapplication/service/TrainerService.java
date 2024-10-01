package epam.com.gymapplication.service;

import epam.com.gymapplication.dao.TrainerRepository;
import epam.com.gymapplication.dao.TrainingTypeRepository;
import epam.com.gymapplication.dao.UserRepository;
import epam.com.gymapplication.dto.TraineeDTO;
import epam.com.gymapplication.dto.TrainerDTO;
import epam.com.gymapplication.entity.Trainee;
import epam.com.gymapplication.entity.Trainer;
import epam.com.gymapplication.entity.TrainingType;
import epam.com.gymapplication.entity.User;
import epam.com.gymapplication.profile.PasswordGenerator;
import epam.com.gymapplication.profile.UserProfileService;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;



@Service
public class TrainerService {
    private static final Logger logger = LoggerFactory.getLogger(TrainerService.class);


    @Autowired
    private UserProfileService userProfileService;

    @Autowired
    private PasswordGenerator passwordGenerator;


    @Autowired
    private TrainerRepository trainerRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TrainingTypeRepository trainingTypeRepository;



    public List<TrainerDTO> findUnassignedTraineesTrainersListByTraineeUsername(String username)  {
        List<Trainer> trainersListNotAssignedToTrainee = trainerRepository.findTrainersListNotAssignedToTraineeByUsername(username);
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

        TrainingType trainingType = trainingTypeRepository.findById(trainerDTO.getSpecialization())
                .orElseThrow(() -> new EntityNotFoundException("Entity not found"));
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
        Trainer trainerByProfileUsername = trainerRepository.findByUsername(username).orElseThrow(() ->
                new EntityNotFoundException("Entity not found"));

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
        Trainer trainerProfileByUsername = trainerRepository.findByUsername(username).orElseThrow(() ->
                new EntityNotFoundException("Entity not found"));
        return trainerProfileByUsername.getUser().getUsername().equals(username)
                && trainerProfileByUsername.getUser().getPassword().equals(password);

    }


    public boolean changePassword(TrainerDTO trainerDTO) {
        String username = trainerDTO.getUsername();
        String oldPassword = trainerDTO.getOldPassword();
        String newPassword = trainerDTO.getNewPassword();

        Trainer trainerProfileByUsername = trainerRepository.findByUsername(username).orElseThrow(() ->
                new EntityNotFoundException("Trainer not found by username: " + username));

        logger.info("Found Trainer Profile by Username {} ", trainerProfileByUsername);


        if (trainerProfileByUsername.getUser().getPassword().equals(oldPassword)) {
            trainerProfileByUsername.getUser().setPassword(newPassword);

            trainerRepository.save(trainerProfileByUsername);
            logger.info("Changed password for trainer {} ", trainerProfileByUsername);

            return true;
        }
        return false;

    }


    public void activateOrDeactivateTrainerStatus(String username, Boolean isActive) {
        Trainer trainerById = trainerRepository.findByUsername(username).orElseThrow(() ->
                new EntityNotFoundException("Entity not found"));
        if (trainerById.getUser().getActive()!= isActive) {
            trainerById.getUser().setActive(isActive);
            trainerRepository.save(trainerById);
            logger.info("Persisted trainer by status {}", trainerById);
        }




    }


    public TrainerDTO updateTrainerProfile(Long id, TrainerDTO trainerDTO) {
        Trainer trainerById = trainerRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("Entity not found"));
        logger.info("Found trainer profile by id {}", trainerById);


        trainerById.getUser().setFirstName(trainerDTO.getFirstname());
        trainerById.getUser().setLastName(trainerDTO.getLastname());
        trainerById.getUser().setUsername(userProfileService.concatenateUsername(trainerDTO.getFirstname(), trainerDTO.getLastname()));
        trainerById.getUser().setActive(trainerDTO.getActive());

        TrainingType trainingType = trainingTypeRepository.findById(trainerDTO.getSpecialization()).orElseThrow(() ->
                new EntityNotFoundException("Entity not found"));
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


    public void deleteTrainerProfileByUsername(String username)  {
        Trainer trainerProfileByUsername = trainerRepository.findByUsername(username).orElseThrow(() ->
                new EntityNotFoundException("Entity not found"));
        trainerRepository.delete(trainerProfileByUsername);
        logger.info("Trainer profile deleted by username {} ", username);
    }



    public void saveTrainer(Trainer trainer) {
        trainerRepository.save(trainer);
        logger.info("Persisted trainer {} ", trainer);

    }


    public void deleteById(Long id)  {
        trainerRepository.deleteById(id);
        logger.info("Trainer id deleted");

    }

    public Trainer findTrainerById(Long id)  {
        logger.info("Found entity by id {} ", id);
        return trainerRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("Entity not found"));

    }


    public Trainer findByFirstName(String firstName) {
        Trainer trainerByFirstname = trainerRepository.findByFirstName(firstName).orElseThrow(() ->
                new EntityNotFoundException("Entity not found"));

        logger.info("Found entity by name {} ", trainerByFirstname);
        return trainerByFirstname;
    }

    public Trainer findByLastName(String lastName)  {
        Trainer trainerByLastname = trainerRepository.findByLastName(lastName).orElseThrow(() ->
                new EntityNotFoundException("Entity not found"));

        logger.info("Found entity by lastName {} ", trainerByLastname);
        return trainerByLastname;
    }

    public Trainer findByUsername(String username)  {
        Trainer trainerByUsername = trainerRepository.findByUsername(username).orElseThrow(() ->
                new EntityNotFoundException("Entity not found"));

        logger.info("Found entity by username {} ", trainerByUsername);
        return trainerByUsername;
    }

    public List<Trainer> findAll() {
        return (List<Trainer>) trainerRepository.findAll();
    }
}
