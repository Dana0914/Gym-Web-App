package epam.com.gymapplication.controller;



import epam.com.gymapplication.dto.*;
import epam.com.gymapplication.service.TraineeService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;


@RestController
public class TraineeController {
    private final TraineeService traineeService;

    public TraineeController(TraineeService traineeService) {
        this.traineeService = traineeService;

    }

    @GetMapping("/api/trainees/trainings")
    public ResponseEntity<List<TrainingDTO>> getTraineeTrainingList(
                                                                    @RequestParam("username") String username, @RequestParam("from") LocalDate from,
                                                                    @RequestParam("to") LocalDate to,
                                                                    @RequestParam("trainerName") String trainerName,
                                                                    @RequestParam("trainingType") String trainingType
    ) {

        List<TrainingDTO> result = traineeService.getTraineesTrainingList(username, from, to, trainerName, trainingType);

        return ResponseEntity.ok(result);

    }

    @PostMapping(value = "/api/trainees/registration")
    public ResponseEntity<TraineeDTO> registerTrainee(@Valid
            @RequestBody TraineeDTO traineeRequest) {

        TraineeDTO registeredTraineeRequest = traineeService.createTraineeProfile(traineeRequest);
        return new ResponseEntity<>(registeredTraineeRequest, HttpStatus.CREATED);

    }

    @GetMapping(value = "/api/trainees/{username}/{password}")
    public ResponseEntity<String> login(@Valid @PathVariable("username") String username,
                                        @PathVariable("password") String password) {

        boolean authenticatedTraineeProfile = traineeService.authenticateTraineeProfile(username, password);
        return ResponseEntity.ok(authenticatedTraineeProfile ? "Login Successful" : "Login Failed");
    }

    @PutMapping("/api/trainees/login")
    public ResponseEntity<TraineeDTO> changeLogin(@Validated(ChangeLogin.class)
                                                      @RequestBody TraineeDTO traineeDTO) {
        boolean passwordChange = traineeService.changePassword(traineeDTO);
        if (passwordChange) {
            return new ResponseEntity<>(traineeDTO, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

    }

    @GetMapping(value = "/api/trainees/{username}")
    public ResponseEntity<TraineeDTO> getTraineeProfile(
            @PathVariable("username") String username) {

        TraineeDTO traineeProfileByUsername = traineeService.findTraineeProfileByUsername(username);
        return ResponseEntity.ok(traineeProfileByUsername);


    }

    @PutMapping(value = "/api/trainees/{id}")
    public ResponseEntity<TraineeDTO> updateTraineeProfile(@Validated(TraineeDTO.UpdatedTraineeProfile.class)
                                                               @PathVariable("id") Long id,
                                                           @RequestBody
                                                           TraineeDTO traineeDTO) {
        TraineeDTO updateTraineeProfile = traineeService.updateTraineeProfile(id, traineeDTO);
        return new ResponseEntity<>(updateTraineeProfile, HttpStatus.OK);


    }

    @DeleteMapping(value = "/api/trainees/{username}")
    public ResponseEntity<Void> deleteTraineeProfile(@Validated(TraineeDTO.DeleteTraineeProfile.class)
            @PathVariable("username") String username) {
        traineeService.deleteTraineeProfileByUsername(username);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);

    }

    @PutMapping(value = "/api/trainees/trainers")
    public ResponseEntity<List<TrainerDTO>> updateTraineesTrainerList(
            @Validated(TraineeDTO.UpdatedTraineesTrainerList.class)
            @RequestBody TraineeDTO traineeDTO) {

        List<TrainerDTO> trainerDTOS = traineeService.updateTraineesTrainerList(traineeDTO);
        return new ResponseEntity<>(trainerDTOS, HttpStatus.OK);
    }

    @PatchMapping(value = "/api/trainees/{username}/status")
    public ResponseEntity<Void> activateDeactivateTrainee(@Validated(TraineeDTO.ActivateDeactivateTrainee.class)
            @PathVariable("username") String username,
            @RequestBody TraineeDTO traineeDTO) {

        traineeService.activateOrDeactivateTraineeStatus(username, traineeDTO.getActive());
        return new ResponseEntity<>(HttpStatus.OK);

    }


























}
