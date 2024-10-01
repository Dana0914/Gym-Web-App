package epam.com.gymapplication.controller;


import epam.com.gymapplication.dto.*;
import epam.com.gymapplication.service.TraineeService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/trainees")
public class TraineeController {
    private final TraineeService traineeService;


    public TraineeController(TraineeService traineeService) {
        this.traineeService = traineeService;

    }

    @PostMapping(value = "/register" ,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<TraineeDTO> registerTrainee(@Valid
            @RequestBody TraineeDTO traineeRequest) {

        TraineeDTO registeredTraineeRequest = traineeService.createTraineeProfile(traineeRequest);
        return new ResponseEntity<>(registeredTraineeRequest, HttpStatus.CREATED);

    }

    @GetMapping("/login")
    public ResponseEntity<String> login(@Valid @RequestParam("username") String username,
                                        @RequestParam("password") String password) {

        boolean authenticatedTraineeProfile = traineeService.authenticateTraineeProfile(username, password);
        if (authenticatedTraineeProfile) {
            return ResponseEntity.ok("Authorized successfully");
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
    }

    @PutMapping("/change-login")
    public ResponseEntity<TraineeDTO> changeLogin(@Validated(ChangeLogin.class) @RequestBody
            TraineeDTO traineeDTO) {
        boolean passwordChange = traineeService.changePassword(traineeDTO);
        if (passwordChange) {
            return ResponseEntity.ok(traineeDTO);
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(traineeDTO);

    }

    @GetMapping(value = "/trainee-profile")
    public ResponseEntity<TraineeDTO> getTraineeProfile(@Validated(GetTraineeProfile.class)
            @RequestParam("username") String username) {

        TraineeDTO traineeProfileByUsername = traineeService.findTraineeProfileByUsername(username);
        return new ResponseEntity<>(traineeProfileByUsername, HttpStatus.OK);

    }

    @PutMapping(value = "/trainee-profile/{id}")
    public ResponseEntity<TraineeDTO> updateTraineeProfile(@Validated(UpdatedTraineeProfile.class)
                                                               @PathVariable("id") Long id,
                                                           @RequestBody
                                                           TraineeDTO traineeDTO) {
        TraineeDTO updateTraineeProfile = traineeService.updateTraineeProfile(id, traineeDTO);
        return new ResponseEntity<>(updateTraineeProfile, HttpStatus.OK);


    }

    @DeleteMapping(value = "/delete")
    public ResponseEntity<Void> deleteTraineeProfile(@Validated(DeleteTraineeProfile.class)
            @RequestParam("username") String username) {
        traineeService.deleteTraineeProfileByUsername(username);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);

    }

    @PutMapping(value = "/list",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)

    public ResponseEntity<List<TrainerDTO>> updateTraineesTrainerList(@Validated(UpdateTraineesTrainerList.class)
            @RequestBody TraineeDTO traineeDTO) {
        List<TrainerDTO> trainerDTOS = traineeService.updateTraineesTrainerList(traineeDTO);
        return new ResponseEntity<>(trainerDTOS, HttpStatus.OK);
    }

    @PatchMapping(value = "/active-inactive")
    public ResponseEntity<Void> activateDeactivateTrainee(@Validated(ActivateDeactivateTrainee.class)
            @RequestParam("username") String username,
            @RequestBody TraineeDTO traineeDTO) {

        traineeService.activateOrDeactivateTraineeStatus(username, traineeDTO.getActive());
        return new ResponseEntity<>(HttpStatus.OK);

    }


























}
