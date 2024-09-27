package epam.com.gymapplication.service;

import epam.com.gymapplication.dao.TraineeRepository;
import epam.com.gymapplication.dao.TrainerRepository;
import epam.com.gymapplication.dao.UserRepository;
import epam.com.gymapplication.dto.TraineeDTO;
import epam.com.gymapplication.dto.TrainerDTO;
import epam.com.gymapplication.entity.Trainee;
import epam.com.gymapplication.entity.Trainer;
import epam.com.gymapplication.entity.User;
import epam.com.gymapplication.profile.PasswordGenerator;
import epam.com.gymapplication.profile.UserProfileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;



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


    // correct it problems with deseralizing arraylist into json
    public List<TrainerDTO> updateTraineesTrainerList(List<Trainer> trainers, String username)  {
        Trainee traineeByUsername = traineeRepository.findByUsername(username).orElseThrow();

        logger.info("Updated trainees trainers list");
        traineeRepository.updateTraineesTrainerList(trainers, traineeByUsername.getUser().getUsername());

        List<TrainerDTO> savedTrainersResponseDTO = traineeByUsername.getTrainers().stream().
                map(trainer -> {
                    TrainerDTO trainerDTO = new TrainerDTO();
                    trainerDTO.setUsername(trainer.getUser().getUsername());
                    trainerDTO.setFirstname(trainer.getUser().getFirstName());
                    trainerDTO.setLastname(trainer.getUser().getLastName());
                    trainerDTO.setSpecialization(trainer.getTrainingType().getId());
                    return trainerDTO;
                })
                .toList();
        return savedTrainersResponseDTO;

    }



    public void assignTraineeToTrainer(Long traineeId, Long trainerId) {
        Trainee trainee = traineeRepository.findById(traineeId).orElseThrow();
        Trainer trainer = trainerRepository.findById(trainerId).orElseThrow();


        trainee.getTrainers().add(trainer);
        trainer.getTrainees().add(trainee);


        trainerRepository.save(trainer);
        traineeRepository.save(trainee);
    }




    public TraineeDTO createTraineeProfile(TraineeDTO traineeDTO)  {
        String username = userProfileService.concatenateUsername(
                traineeDTO.getFirstname(),
                traineeDTO.getLastname());

        String password = passwordGenerator.generatePassword();

        User toUserEntity = new User();
        toUserEntity.setUsername(username);
        toUserEntity.setPassword(password);
        toUserEntity.setFirstName(traineeDTO.getFirstname());
        toUserEntity.setLastName(traineeDTO.getLastname());
        toUserEntity.setActive(traineeDTO.getActive());


        userRepository.save(toUserEntity);


        Trainee trainee = new Trainee();
        trainee.setUser(toUserEntity);
        trainee.setAddress(traineeDTO.getAddress());
        trainee.setDateOfBirth(traineeDTO.getDateOfBirth());


        traineeRepository.save(trainee);

        TraineeDTO traineeResponse = new TraineeDTO();
        traineeResponse.setUsername(trainee.getUser().getUsername());
        traineeResponse.setPassword(trainee.getUser().getPassword());


        return traineeResponse;

    }

    public TraineeDTO findTraineeProfileByUsername(String username) {
        Trainee traineeByUsername = traineeRepository.findByUsername(username).orElseThrow();
        logger.info("Found Trainee Profile by Username {} ", traineeByUsername);



        //Map entity fields to DTO

        TraineeDTO traineeProfileResponseDTO = new TraineeDTO();
        traineeProfileResponseDTO.setFirstname(traineeByUsername.getUser().getFirstName());
        traineeProfileResponseDTO.setLastname(traineeByUsername.getUser().getLastName());
        traineeProfileResponseDTO.setAddress(traineeByUsername.getAddress());
        traineeProfileResponseDTO.setDateOfBirth(traineeByUsername.getDateOfBirth());





        // Map trainers using the TrainerDTO
        List<TrainerDTO> trainerDTOs = traineeByUsername.getTrainers().stream()
                .map(trainer -> {
                    System.out.println(trainer.getUser());

                    logger.info("trainer info {}", trainer);
                    TrainerDTO trainerDto = new TrainerDTO();

                    trainerDto.setUsername(trainer.getUser().getUsername());
                    trainerDto.setFirstname(trainer.getUser().getFirstName());
                    trainerDto.setLastname(trainer.getUser().getLastName());
                    trainerDto.setSpecialization(trainer.getTrainingType().getId());


                    return trainerDto;
                })
                .toList();



        traineeProfileResponseDTO.setTrainers(trainerDTOs);


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
            traineeRepository.save(traineeProfileByUsername);
            return true;
        }
        return false;

    }


    public void activateOrDeactivateTraineeStatus(String username, Boolean isActive) {
        Trainee traineeById = traineeRepository.findByUsername(username).orElseThrow();
        if (traineeById.getUser().getActive()!= isActive) {
            traineeById.getUser().setActive(isActive);
            traineeRepository.save(traineeById);
        }
        System.out.println("Trainee is " + (isActive ? "active" : "inactive"));


    }



    public TraineeDTO updateTraineeProfile(Long id, TraineeDTO traineeDTO) {
        Trainee traineeById = traineeRepository.findById(id).orElseThrow();


        traineeById.getUser().setFirstName(traineeDTO.getFirstname());
        traineeById.getUser().setLastName(traineeDTO.getLastname());
        traineeById.getUser().setUsername(userProfileService.concatenateUsername(traineeDTO.getFirstname(), traineeDTO.getLastname()));
        traineeById.setAddress(traineeDTO.getAddress());
        traineeById.setDateOfBirth(traineeDTO.getDateOfBirth());
        traineeById.getUser().setActive(traineeDTO.getActive());

        logger.info("Entity update profile successfully");
        traineeRepository.save(traineeById);


        // Map trainers using the TrainerDTO
        List<TrainerDTO> trainerDTOs = traineeById.getTrainers().stream()
                .map(trainer -> {
                    System.out.println(trainer.getUser());

                    logger.info("trainer info {}", trainer);
                    TrainerDTO trainerDto = new TrainerDTO();

                    trainerDto.setUsername(trainer.getUser().getUsername());
                    trainerDto.setFirstname(trainer.getUser().getFirstName());
                    trainerDto.setLastname(trainer.getUser().getLastName());
                    trainerDto.setSpecialization(trainer.getTrainingType().getId());

                    return trainerDto;
                })
                .toList();


        traineeDTO.setTrainers(trainerDTOs);


        return traineeDTO;

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
