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
    private TraineeService traineeService;

    @InjectMocks
    private TrainingController trainingController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(trainingController).build();
    }

    @Test
    public void getTraineesTrainingListTest_whenRequestIsValid_ReturnResponseOk() throws Exception {
        String username = "Ben.Gosling";
        LocalDate from = LocalDate.parse(LocalDate.of(2024, 9, 30).toString());
        LocalDate to = LocalDate.parse(LocalDate.of(2024, 10, 30).toString());
        String trainerName = "Rick";
        String trainingType = "cross-fit";

        List<TrainingDTO> trainings = new ArrayList<>();

        TrainingDTO trainingResponse = new TrainingDTO();
        trainingResponse.setTrainingName("run");
        trainingResponse.setTrainingType(trainingType);
        trainingResponse.setTrainingDate(LocalDate.parse(LocalDate.of(2024, 10, 2).toString()));
        trainingResponse.setTrainingDuration(70);
        trainingResponse.setTrainerName(trainerName);

        trainings.add(trainingResponse);



        when(trainingService.getTraineesTrainingList(username, from, to, trainerName, trainingType)).thenReturn(trainings);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/trainings/trainee/training/list")
                .contentType(MediaType.APPLICATION_JSON)
                        .param("username", username)
                        .param("from", String.valueOf(from))
                        .param("to", String.valueOf(to))
                        .param("trainerName", trainingResponse.getTrainerName())
                        .param("trainingType", trainingResponse.getTrainingType())

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
        LocalDate from = LocalDate.parse(LocalDate.of(2024, 9, 30).toString());
        LocalDate to = LocalDate.parse(LocalDate.of(2024, 10, 30).toString());
        String traineeName = "Shon";
        String trainingType = "stretching";

        List<TrainingDTO> trainings = new ArrayList<>();

        TrainingDTO trainingResponse = new TrainingDTO();
        trainingResponse.setTrainingName("run");
        trainingResponse.setTrainingType(trainingType);
        trainingResponse.setTrainingDate(LocalDate.parse(LocalDate.of(2024, 10, 2).toString()));
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
