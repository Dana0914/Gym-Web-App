package epam.com.gymapplication;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import static org.mockito.Mockito.*;



@ExtendWith(MockitoExtension.class)
public class TrainersTrainingSessionServiceTest {

    @Mock
    private ObjectMapper objectMapper;

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
        TrainingDTO trainingDTO = new TrainingDTO();
        trainingDTO.setTrainingDuration(2);
        trainingDTO.setTrainingDate(LocalDate.now());
        trainingDTO.setActionType(ActionType.ADD);
        trainingDTO.setTrainerUsername("John Doe");
        trainingDTO.setTrainerFirstname("John");
        trainingDTO.setTrainerLastname("Doe");
        trainingDTO.setIsActive(true);



    }

    @Test
    public void givenMockingIsDoneByMockito_whenExchangeIsCalled_thenReturnMockedObject() throws JsonProcessingException {
        // given
        TrainingDTO mockTrainingDTO = new TrainingDTO();
        mockTrainingDTO.setTrainerUsername("John.Doe");
        mockTrainingDTO.setTrainerFirstname("John");
        mockTrainingDTO.setTrainerLastname("Doe");
        mockTrainingDTO.setActive(true);
        mockTrainingDTO.setTrainingDuration(2);
        mockTrainingDTO.setTrainingDate(LocalDate.now());
        mockTrainingDTO.setActionType(ActionType.ADD);

        String mockTrainingJson = objectMapper.writeValueAsString(mockTrainingDTO);


        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<TrainingDTO> entity = new HttpEntity<>(mockTrainingDTO, headers);

        // when
        when(restTemplate.exchange(
                eq("http://localhost:8763/api/workload"),
                eq(HttpMethod.POST),
                eq(entity),
                eq(String.class)))
                .thenReturn(new ResponseEntity<>(mockTrainingJson,
                        HttpStatus.OK));

        // act
        ResponseEntity<String> response = trainersTrainingSessionService.notifySecondaryService(mockTrainingDTO);


        Assertions.assertEquals(response.getBody(), mockTrainingJson);
        Assertions.assertEquals(response.getStatusCode(), HttpStatus.OK);
        Assertions.assertTrue(response.hasBody());

        // Verify
        verify(restTemplate).exchange(
                eq("http://localhost:8763/api/workload"),
                eq(HttpMethod.POST),
                eq(entity),
                eq(String.class));



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

