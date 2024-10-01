package epam.com.gymapplication.webmvctest;



import epam.com.gymapplication.controller.TrainerController;
import epam.com.gymapplication.dto.TraineeDTO;
import epam.com.gymapplication.dto.TrainerDTO;
import epam.com.gymapplication.service.TrainerService;
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


import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class TrainerControllerTest {
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
    public void testRegisterTrainerProfile_validRequest_ReturnsValidResponse() throws Exception {

        String username = "Ben.Gosling";
        String expectedPassword = "testPassword";



        TrainerDTO trainerResponse = new TrainerDTO();
        trainerResponse.setUsername(username);
        trainerResponse.setPassword(expectedPassword);


        when(trainerService.createTrainerProfile(any(TrainerDTO.class))).thenReturn(trainerResponse);


        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/trainers/register")
                        .content("""
                            {
                            "firstname": "Ben",
                            "lastname": "Gosling",
                            "specalization": 1
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

        mockMvc.perform(MockMvcRequestBuilders.put("/api/trainers/change-login")
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

        mockMvc.perform(MockMvcRequestBuilders.get("/api/trainers/trainer-profile")
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

        mockMvc.perform(MockMvcRequestBuilders.put("/api/trainers/trainer-profile/{id}", 1L)
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

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/trainers/delete")
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


        mockMvc.perform(MockMvcRequestBuilders.patch("/api/trainers/active-inactive")
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
