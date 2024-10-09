package epam.com.gymapplication.webmvctest;

import epam.com.gymapplication.controller.TraineeController;
import epam.com.gymapplication.dto.TraineeDTO;
import epam.com.gymapplication.dto.TrainerDTO;
import epam.com.gymapplication.dto.TrainingDTO;
import epam.com.gymapplication.service.TraineeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
public class TraineeControllerTest {

    @Autowired
    private MockMvc mockMvc;


    @Mock
    private TraineeService traineeService;

    @InjectMocks
    private TraineeController traineeController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(traineeController).build();
    }

    @Test
    public void getTraineesTrainingListTest_whenRequestIsValid_ReturnResponseOk() throws Exception {
        TrainingDTO trainingRequest = new TrainingDTO();
        trainingRequest.setUsername("Ben.Gosling");
        trainingRequest.setFrom(LocalDate.parse("2024-09-30"));
        trainingRequest.setTo(LocalDate.parse("2024-10-30"));
        trainingRequest.setTrainerName("Rick");
        trainingRequest.setTrainingType("cross-fit");

        List<TrainingDTO> trainings = new ArrayList<>();

        TrainingDTO trainingResponse = new TrainingDTO();
        trainingResponse.setTrainingName("run");
        trainingResponse.setTrainingType(trainingRequest.getTrainingType());
        trainingResponse.setTrainingDate(LocalDate.parse("2024-10-02"));
        trainingResponse.setTrainingDuration(70);
        trainingResponse.setTrainerName(trainingRequest.getTrainerName());

        trainings.add(trainingResponse);



        when(traineeService.getTraineesTrainingList(trainingRequest.getUsername(),
                trainingRequest.getFrom(), trainingRequest.getTo(),
                trainingRequest.getTrainerName(), trainingRequest.getTrainingType())).thenReturn(trainings);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/trainees/trainings")
                        .param("username", trainingRequest.getUsername())
                        .param("from", trainingRequest.getFrom().toString())
                        .param("to", trainingRequest.getTo().toString())
                        .param("trainerName", trainingRequest.getTrainerName())
                        .param("trainingType", trainingRequest.getTrainingType())
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].trainingName").value(trainingResponse.getTrainingName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].trainingDate").value(trainingResponse.getTrainingDate().toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].trainingDuration").value(trainingResponse.getTrainingDuration()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].trainerName").value(trainingResponse.getTrainerName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].trainingType").value(trainingResponse.getTrainingType()))
                .andDo(print());
    }

    @Test
    public void testRegisterTraineeProfile_validRequest_ReturnsValidResponse() throws Exception {

        String username = "Ben.Gosling";
        String expectedPassword = "testPassword";



        TraineeDTO traineeResponse = new TraineeDTO();
        traineeResponse.setUsername(username);
        traineeResponse.setPassword(expectedPassword);

        System.out.println(traineeResponse);


        when(traineeService.createTraineeProfile(any(TraineeDTO.class))).thenReturn(traineeResponse);


        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/trainees/registration")
                        .content("""
                            {
                            "firstname": "Ben",
                            "lastname": "Gosling",
                            "address": "Kennington st 1",
                            "dateOfBirth": "2020-01-01"
                            }""")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .accept(MediaType.APPLICATION_JSON))

                .andExpect(status().isCreated()).andDo(print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.username").value(traineeResponse.getUsername()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.password").value(traineeResponse.getPassword()));
    }

    @Test
    public void testChangeLogin_validRequest_ReturnsTrue() throws Exception {
        TraineeDTO traineeDTO = new TraineeDTO();
        traineeDTO.setUsername("Ben.Gosling");
        traineeDTO.setOldPassword("testPassword");
        traineeDTO.setNewPassword("newPassword");


        when(traineeService.changePassword(any(TraineeDTO.class))).thenReturn(true);

        mockMvc.perform(MockMvcRequestBuilders.put("/api/trainees/login")
                        .content("""
                {
                   "username": "Ben.Gosling",
                   "oldPassword": "testPassword",
                   "newPassword": "newPassword"
                }""")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());



    }

    @Test
    public void testGetTraineeProfile_validRequest_ReturnsValidResponse() throws Exception {
        String username = "Ben.Gosling";

        TrainerDTO trainerDTO = new TrainerDTO();
        trainerDTO.setUsername("Abel.Sanchez");
        trainerDTO.setFirstname("Abel");
        trainerDTO.setLastname("Sanchez");


        List<TrainerDTO> trainers = new ArrayList<>();
        trainers.add(trainerDTO);

        TraineeDTO traineeDTO = new TraineeDTO();
        traineeDTO.setFirstname("Ben");
        traineeDTO.setLastname("Gosling");
        traineeDTO.setDateOfBirth(LocalDate.parse("2024-10-20"));
        traineeDTO.setAddress("Baker street");
        traineeDTO.setActive(true);
        traineeDTO.setTrainers(trainers);


        when(traineeService.findTraineeProfileByUsername(username)).thenReturn(traineeDTO);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/trainees/{username}", "Ben.Gosling")
                .param("username", username)

                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstname").value(traineeDTO.getFirstname()))

                .andExpect(MockMvcResultMatchers.jsonPath("$.lastname").value(traineeDTO.getLastname()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.dateOfBirth").value(traineeDTO.getDateOfBirth().toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.address").value(traineeDTO.getAddress()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.active").value(traineeDTO.getActive()))

                .andExpect(MockMvcResultMatchers.jsonPath("$.trainers[0].username").value(trainerDTO.getUsername()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.trainers[0].firstname").value(trainerDTO.getFirstname()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.trainers[0].lastname").value(trainerDTO.getLastname()))
                .andDo(print());

    }

    @Test
    public void updateTraineeProfileTest_whenRequestIsValid_ReturnValidResponse() throws Exception {
        TrainerDTO trainerDTO = new TrainerDTO();
        trainerDTO.setUsername("Abel.Sanchez");
        trainerDTO.setFirstname("Abel");
        trainerDTO.setLastname("Sanchez");

        List<TrainerDTO> trainers = new ArrayList<>();
        trainers.add(trainerDTO);


        TraineeDTO traineeResponse = new TraineeDTO();
        traineeResponse.setUsername("Ben.Gosling");
        traineeResponse.setFirstname("Ben");
        traineeResponse.setLastname("Gosling");
        traineeResponse.setDateOfBirth(LocalDate.of(2024, 10, 20));
        traineeResponse.setAddress("Baker street");
        traineeResponse.setActive(true);
        traineeResponse.setTrainers(trainers);

        when(traineeService.updateTraineeProfile(eq(1L), any(TraineeDTO.class))).thenReturn(traineeResponse);

        mockMvc.perform(MockMvcRequestBuilders.put("/api/trainees/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""

                                {
                                "username": "Ben.Gosling",
                                "firstname": "Ben",
                                "lastname": "Gosling",
                                "address": "Baker street",
                                "dateOfBirth": "2024-10-20",
                                "active":true
                                }""")
                        .characterEncoding("UTF-8")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.username").value(traineeResponse.getUsername()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstname").value(traineeResponse.getFirstname()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastname").value(traineeResponse.getLastname()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.dateOfBirth").value(traineeResponse.getDateOfBirth().toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.address").value(traineeResponse.getAddress()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.active").value(traineeResponse.getActive()))

                .andExpect(MockMvcResultMatchers.jsonPath("$.trainers[0].username").value(trainerDTO.getUsername()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.trainers[0].firstname").value(trainerDTO.getFirstname()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.trainers[0].lastname").value(trainerDTO.getLastname()))

                .andDo(print());
    }



    @Test
    public void deleteTraineeProfile_whenRequestIsValid_ReturnNoContent() throws Exception {
        String username = "Ben.Gosling";

        doNothing().when(traineeService).deleteTraineeProfileByUsername(username);

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/trainees/{username}", "Ben.Gosling")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .param("username", username)
                        .characterEncoding("UTF-8")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent())
                .andDo(print());

        verify(traineeService, times(1)).deleteTraineeProfileByUsername(username);

    }

    @Test
    public void activateDeactivateTraineeProfileTest_whenRequestIsValid_ReturnOk() throws Exception {
        String username = "Ben.Gosling";

        TraineeDTO traineeDTO = new TraineeDTO();
        traineeDTO.setActive(true);

        doNothing().when(traineeService).activateOrDeactivateTraineeStatus(username, traineeDTO.getActive());


        mockMvc.perform(MockMvcRequestBuilders.patch("/api/trainees/{username}/status", "Ben.Gosling")
                        .param("username", username)
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content("""
                        {
                          "active": true
                        }
                        """)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());

        verify(traineeService, times(1)).activateOrDeactivateTraineeStatus(username, traineeDTO.getActive());

    }

}
