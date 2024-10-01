package epam.com.gymapplication.webmvctest;


import epam.com.gymapplication.controller.TrainingController;
import epam.com.gymapplication.dto.TraineeDTO;
import epam.com.gymapplication.dto.TrainingDTO;
import epam.com.gymapplication.entity.Trainee;
import epam.com.gymapplication.entity.Training;
import epam.com.gymapplication.entity.TrainingType;
import epam.com.gymapplication.service.TraineeService;
import epam.com.gymapplication.service.TrainingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class TrainingControllerTest {
    private MockMvc mockMvc;

    @Mock
    private TrainingService trainingService;


    @InjectMocks
    private TrainingController trainingController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(trainingController).build();
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



        when(trainingService.getTraineesTrainingList(trainingRequest.getUsername(),
                trainingRequest.getFrom(), trainingRequest.getTo(),
                trainingRequest.getTrainerName(), trainingRequest.getTrainingType())).thenReturn(trainings);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/trainings/trainee/training/list")
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



        when(trainingService.getTrainersTrainingList(username, from, to, traineeName, trainingType)).thenReturn(trainings);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/trainings/trainer/training/list")
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
    public void addTrainingTest_whenRequestIsValid_ReturnResponseOk() throws Exception {

        TrainingDTO trainingDTO = new TrainingDTO();
        trainingDTO.setTraineeUsername("Ash.Salivan");
        trainingDTO.setTrainerUsername("Bernard.Rayans");
        trainingDTO.setTrainingName("run");
        trainingDTO.setTrainingDate(LocalDate.parse(LocalDate.of(2024, 10, 5).toString()));
        trainingDTO.setTrainingDuration(45);


        doNothing().when(trainingService).addTraining(trainingDTO);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/trainings/add")
                .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                 {
                 "traineeUsername": "Ash.Salivan",
                 "trainerUsername": "Bernard.Rayans",
                 "trainingName": "run",
                 "trainingDate": "2024-10-05",
                 "trainingDuration": 45
                 }""")
                .characterEncoding("UTF-8")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }


}
