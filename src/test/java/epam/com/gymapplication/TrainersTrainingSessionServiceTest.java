package epam.com.gymapplication;


import epam.com.gymapplication.dao.*;
import epam.com.gymapplication.dto.TrainingDTO;
import epam.com.gymapplication.entity.*;
import epam.com.gymapplication.profile.PasswordGenerator;
import epam.com.gymapplication.service.TrainersTrainingSessionService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.client.RestTemplate;


import java.time.LocalDate;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TrainersTrainingSessionServiceTest {

    @Mock
    private TrainingRepository trainingRepository;

    @Mock
    private TrainingTypeRepository trainingTypeRepository;

    @Mock
    private TraineeRepository traineeRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private TrainerRepository trainerRepository;

    @Mock
    private PasswordGenerator passwordGenerator;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private TrainersTrainingSessionService trainersTrainingSessionService;


    @Test
    public void whenValidTrainingDTO_thenAddTrainingSuccessfully() {
        //given
        User user = new User();
        user.setId(1L);
        user.setUsername("John.Doe");
        user.setPassword(passwordEncoder.encode(passwordGenerator.generatePassword()));
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setActive(true);


        TrainingType trainingType = new TrainingType();
        trainingType.setId(1L);
        trainingType.setTrainingTypeName("aerobics");

        when(trainingTypeRepository.findById(1L)).thenReturn(Optional.of(trainingType));

        Trainer trainer = new Trainer();
        trainer.setId(1L);
        trainer.setUser(user);
        trainer.setSpecialization(trainingType.getId());


        Trainee trainee = new Trainee();
        trainee.setId(2L);
        trainee.setUser(user);
        trainee.setDateOfBirth(LocalDate.of(2000, 1, 1));
        trainee.setAddress("Avenue 01");

        when(traineeRepository.findById(2L)).thenReturn(Optional.of(trainee));

        Training training = new Training();
        training.setId(1L);
        training.setTrainer(trainer);
        training.setTrainee(trainee);
        training.setTrainingType(trainingType);
        training.setTrainingName("strength");
        training.setTrainingDuration(2);
        training.setTrainingDate(LocalDate.now());
        training.setActionType(ActionType.ADD);


        TrainingDTO trainingDTO = new TrainingDTO();
        trainingDTO.setTrainingName(training.getTrainingName());
        trainingDTO.setTrainingDuration(training.getTrainingDuration());
        trainingDTO.setTrainingDate(training.getTrainingDate());
        trainingDTO.setActionType(training.getActionType());
        trainingDTO.setTrainerUsername(trainer.getUser().getUsername());
        trainingDTO.setTrainerFirstname(trainer.getUser().getFirstName());
        trainingDTO.setTrainerLastname(trainer.getUser().getLastName());
        trainingDTO.setIsActive(trainer.getUser().getActive());


        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<TrainingDTO> entity = new HttpEntity<>(trainingDTO, headers);

        // Arrange
        String expectedResponse = "Successfully call secondary microservice";
        when(restTemplate.exchange(
                eq("http://localhost:8763/api/workload/trainers/trainings"),
                eq(HttpMethod.POST),
                eq(entity),
                eq(String.class)))
                .thenReturn(new ResponseEntity<>(expectedResponse, HttpStatus.OK));


        // Act
        trainersTrainingSessionService.notifySecondaryService(trainingDTO);


        // Assert
        verify(restTemplate).exchange(
                eq("http://localhost:8763/api/workload/trainers/trainings"),
                eq(HttpMethod.POST),
                eq(entity),
                eq(String.class));

        //when
        trainersTrainingSessionService.addTraining(trainingDTO);

        //then
        verify(trainingRepository, times(1)).save(any(Training.class));

        Assertions.assertEquals(training.getTrainingName(), trainingDTO.getTrainingName());
        Assertions.assertEquals(training.getTrainingDuration(), trainingDTO.getTrainingDuration());
        Assertions.assertEquals(training.getTrainingDate(), trainingDTO.getTrainingDate());
        Assertions.assertEquals(training.getActionType(), trainingDTO.getActionType());
        Assertions.assertEquals(trainer.getUser().getUsername(), trainingDTO.getTrainerUsername());
        Assertions.assertEquals(trainer.getUser().getFirstName(), trainingDTO.getTrainerFirstname());
        Assertions.assertEquals(trainer.getUser().getLastName(), trainingDTO.getTrainerLastname());
        Assertions.assertEquals(trainer.getUser().getActive(), trainingDTO.getIsActive());

    }

    @Test
    public void whenValidTrainingDTO_thenDeleteTrainingSuccessfully() {
        //given
        TrainingDTO trainingDTO = new TrainingDTO();
        trainingDTO.setTrainingDuration(2);
        trainingDTO.setTrainingDate(LocalDate.now());
        trainingDTO.setActionType(ActionType.DELETE);
        trainingDTO.setTrainerUsername("John.Doe");
        trainingDTO.setTrainerFirstname("John");
        trainingDTO.setTrainerLastname("Doe");
        trainingDTO.setIsActive(true);



        User user = new User();
        user.setId(1L);
        user.setUsername(trainingDTO.getTrainerUsername());
        user.setPassword(passwordEncoder.encode(passwordGenerator.generatePassword()));
        user.setFirstName(trainingDTO.getTrainerFirstname());
        user.setLastName(trainingDTO.getTrainerLastname());
        user.setActive(trainingDTO.getIsActive());


        TrainingType trainingType = new TrainingType();
        trainingType.setId(1L);
        trainingType.setTrainingTypeName("aerobics");


        Trainer trainer = new Trainer();
        trainer.setId(1L);
        trainer.setUser(user);
        trainer.setSpecialization(trainingType.getId());



        Trainee trainee = new Trainee();
        trainee.setId(1L);
        trainee.setUser(user);
        trainee.setDateOfBirth(LocalDate.of(2000, 1, 1));
        trainee.setAddress("Avenue 01");



        Training training = new Training();
        training.setId(1L);
        training.setTrainer(trainer);
        training.setTrainee(trainee);
        training.setTrainingType(trainingType);
        training.setTrainingName("stretch");
        training.setTrainingDuration(trainingDTO.getTrainingDuration());
        training.setTrainingDate(trainingDTO.getTrainingDate());
        training.setActionType(training.getActionType());




        userRepository.delete(user);
        traineeRepository.delete(trainee);
        trainerRepository.delete(trainer);
        trainingRepository.delete(training);
        trainingTypeRepository.delete(trainingType);


        verify(userRepository, times(1)).delete(user);
        verify(traineeRepository, times(1)).delete(trainee);
        verify(trainerRepository, times(1)).delete(trainer);
        verify(trainingTypeRepository, times(1)).delete(trainingType);
        verify(trainingTypeRepository, times(1)).delete(trainingType);




    }
}

