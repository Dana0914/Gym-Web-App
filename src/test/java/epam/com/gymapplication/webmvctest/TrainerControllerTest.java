package epam.com.gymapplication.webmvctest;



import epam.com.gymapplication.controller.TrainerController;
import epam.com.gymapplication.dto.TraineeDTO;
import epam.com.gymapplication.dto.TrainerDTO;
import epam.com.gymapplication.dto.TrainingDTO;
import epam.com.gymapplication.service.TrainerService;
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
import static org.mockito.Mockito.times;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
public class TrainerControllerTest {

    @Autowired
    private MockMvc mockMvc;


    @Mock
    private TrainerService trainerService;

    @InjectMocks
    private TrainerController trainerController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(trainerController).build();
    }

    @Test
    public void getTrainersTrainingListTest_whenRequestIsValid_ReturnResponseOk() throws Exception {
        String username = "Ben.Gosling";
        LocalDate from = LocalDate.parse("2024-09-30");
        LocalDate to = LocalDate.parse("2024-10-30");
        String traineeName = "Shon";
        String trainingType = "stretching";

        List<TrainingDTO> trainings = new ArrayList<>();

        TrainingDTO trainingResponse = new TrainingDTO();
        trainingResponse.setTrainingName("run");
        trainingResponse.setTrainingType(trainingType);
        trainingResponse.setTrainingDate(LocalDate.parse("2024-10-02"));
        trainingResponse.setTrainingDuration(70);
        trainingResponse.setTraineeName(traineeName);

        trainings.add(trainingResponse);



        when(trainerService.getTrainersTrainingList(username, from, to, traineeName, trainingType)).thenReturn(trainings);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/trainers/trainings")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("username", username)
                        .param("from", String.valueOf(from))
                        .param("to", String.valueOf(to))
                        .param("traineeName", trainingResponse.getTraineeName())
                        .param("trainingType", trainingResponse.getTrainingType())

                        .characterEncoding("UTF-8")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].trainingName").value(trainingResponse.getTrainingName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].trainingDate").value(trainingResponse.getTrainingDate().toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].trainingDuration").value(trainingResponse.getTrainingDuration()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].traineeName").value(trainingResponse.getTraineeName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].trainingType").value(trainingResponse.getTrainingType()))
                .andDo(print());
    }

    @Test
    public void testRegisterTrainerProfile_validRequest_ReturnsValidResponse() throws Exception {

        String username = "Ben.Gosling";
        String expectedPassword = "testPassword";



        TrainerDTO trainerResponse = new TrainerDTO();
        trainerResponse.setUsername(username);
        trainerResponse.setPassword(expectedPassword);


        when(trainerService.createTrainerProfile(any(TrainerDTO.class))).thenReturn(trainerResponse);


        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/trainers/registration")
                        .content("""
                            {
                            "firstname": "Ben",
                            "lastname": "Gosling",
                            "specialization": 1
                            }""")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated()).andDo(print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.username").value(trainerResponse.getUsername()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.password").value(trainerResponse.getPassword()));
    }

    @Test
    public void testChangeLogin_validRequest_ReturnsTrue() throws Exception {
        TrainerDTO trainerDTO = new TrainerDTO();
        trainerDTO.setUsername("Ben.Gosling");
        trainerDTO.setOldPassword("testPassword");
        trainerDTO.setNewPassword("newPassword");


        when(trainerService.changePassword(any(TrainerDTO.class))).thenReturn(true);

        mockMvc.perform(MockMvcRequestBuilders.put("/api/trainers/login")
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
    public void testGetTrainerProfile_validRequest_ReturnsValidResponse() throws Exception {
        String username = "Ben.Gosling";

        TraineeDTO traineeDTO = new TraineeDTO();
        traineeDTO.setUsername("Abel.Sanchez");
        traineeDTO.setFirstname("Abel");
        traineeDTO.setLastname("Sanchez");


        List<TraineeDTO> trainees = new ArrayList<>();
        trainees.add(traineeDTO);

        TrainerDTO trainerDTO = new TrainerDTO();
        trainerDTO.setFirstname("Ben");
        trainerDTO.setLastname("Gosling");
        trainerDTO.setSpecialization(1L);
        trainerDTO.setActive(true);
        trainerDTO.setTrainees(trainees);


        when(trainerService.findTrainerProfileByUsername(username)).thenReturn(trainerDTO);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/trainers/{username}/profile", "Ben.Gosling")
                        .param("username", username)

                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstname").value(trainerDTO.getFirstname()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastname").value(trainerDTO.getLastname()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.active").value(trainerDTO.getActive()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.specialization").value(trainerDTO.getSpecialization()))

                .andExpect(MockMvcResultMatchers.jsonPath("$.trainees[0].username").value(traineeDTO.getUsername()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.trainees[0].firstname").value(traineeDTO.getFirstname()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.trainees[0].lastname").value(traineeDTO.getLastname()))
                .andDo(print());

    }

    @Test
    public void updateTrainerProfileTest_whenRequestIsValid_ReturnValidResponse() throws Exception {
        TraineeDTO traineeDTO = new TraineeDTO();
        traineeDTO.setUsername("Abel.Sanchez");
        traineeDTO.setFirstname("Abel");
        traineeDTO.setLastname("Sanchez");

        List<TraineeDTO> trainees = new ArrayList<>();
        trainees.add(traineeDTO);


        TrainerDTO trainerResponse = new TrainerDTO();
        trainerResponse.setUsername("Ben.Gosling");
        trainerResponse.setFirstname("Ben");
        trainerResponse.setLastname("Gosling");
        trainerResponse.setActive(true);
        trainerResponse.setSpecialization(1L);
        trainerResponse.setTrainees(trainees);

        when(trainerService.updateTrainerProfile(eq(1L), any(TrainerDTO.class))).thenReturn(trainerResponse);

        mockMvc.perform(MockMvcRequestBuilders.put("/api/trainers/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                "username": "Ben.Gosling",
                                "firstname": "Ben",
                                "lastname": "Gosling",
                                "specialization": 1,
                                "active":true
                                }""")
                        .characterEncoding("UTF-8")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.username").value(trainerResponse.getUsername()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstname").value(trainerResponse.getFirstname()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastname").value(trainerResponse.getLastname()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.specialization").value(trainerResponse.getSpecialization()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.active").value(trainerResponse.getActive()))

                .andExpect(MockMvcResultMatchers.jsonPath("$.trainees[0].username").value(traineeDTO.getUsername()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.trainees[0].firstname").value(traineeDTO.getFirstname()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.trainees[0].lastname").value(traineeDTO.getLastname()))

                .andDo(print());
    }

    @Test
    public void deleteTrainerProfile_whenRequestIsValid_ReturnNoContent() throws Exception {
        String username = "Ben.Gosling";

        doNothing().when(trainerService).deleteTrainerProfileByUsername(username);

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/trainers/{username}", "Ben.Gosling")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .param("username", username)
                        .characterEncoding("UTF-8")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent())
                .andDo(print());

        verify(trainerService, times(1)).deleteTrainerProfileByUsername(username);

    }

    @Test
    public void activateDeactivateTrainerProfileTest_whenRequestIsValid_ReturnOk() throws Exception {
        String username = "Ben.Gosling";

        TrainerDTO trainerDTO = new TrainerDTO();
        trainerDTO.setActive(true);

        doNothing().when(trainerService).activateOrDeactivateTrainerStatus(username, trainerDTO.getActive());


        mockMvc.perform(MockMvcRequestBuilders.patch("/api/trainers/{username}/status", "Ben.Gosling")
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

        verify(trainerService, times(1)).activateOrDeactivateTrainerStatus(username, trainerDTO.getActive());

    }


}
