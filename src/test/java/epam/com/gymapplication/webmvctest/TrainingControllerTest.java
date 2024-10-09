package epam.com.gymapplication.webmvctest;


import epam.com.gymapplication.controller.TrainingController;
import epam.com.gymapplication.dto.TrainingDTO;
import epam.com.gymapplication.service.TrainingService;
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
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;


import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class TrainingControllerTest {

    @Autowired
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
    public void addTrainingTest_whenRequestIsValid_ReturnResponseOk() throws Exception {

        TrainingDTO trainingDTO = new TrainingDTO();
        trainingDTO.setTraineeUsername("Ash.Salivan");
        trainingDTO.setTrainerUsername("Bernard.Rayans");
        trainingDTO.setTrainingName("run");
        trainingDTO.setTrainingDate(LocalDate.parse(LocalDate.of(2024, 10, 5).toString()));
        trainingDTO.setTrainingDuration(45);


        doNothing().when(trainingService).addTraining(trainingDTO);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/trainings")
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
