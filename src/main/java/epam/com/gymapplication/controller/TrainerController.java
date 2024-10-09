package epam.com.gymapplication.controller;


import epam.com.gymapplication.dto.*;
import epam.com.gymapplication.service.TrainerService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
public class TrainerController {

    private final TrainerService trainerService;

    public TrainerController(TrainerService trainerService) {
        this.trainerService = trainerService;
    }


    @GetMapping("/api/trainers/trainings")
    public ResponseEntity<List<TrainingDTO>> getTrainerTrainingList(
                                                                    @RequestParam("username") String username,
                                                                    @RequestParam("from") LocalDate from,
                                                                    @RequestParam("to") LocalDate to,
                                                                    @RequestParam("traineeName") String traineeName,
                                                                    @RequestParam("trainingType") String trainingType) {


        List<TrainingDTO> result = trainerService.getTrainersTrainingList(username, from, to,
                traineeName, trainingType);

        return ResponseEntity.ok(result);
    }

    @PostMapping(value = "api/trainers/registration")

    public ResponseEntity<TrainerDTO> registerTrainer(@Valid
            @RequestBody TrainerDTO trainerRequestDTO) {

        TrainerDTO registeredTrainerRequest = trainerService.createTrainerProfile(trainerRequestDTO);
        return new ResponseEntity<>(registeredTrainerRequest, HttpStatus.CREATED);


    }

    @GetMapping("/api/trainers/{username}/{password}")
    public ResponseEntity<String> login(@Valid
                                            @PathVariable("username") String username,
                                            @PathVariable("password") String password) {

        boolean authenticatedTrainerProfile = trainerService.authenticateTrainerProfile(username, password);
        if (authenticatedTrainerProfile) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PutMapping(value = "api/trainers/login")
    public ResponseEntity<String> changeLogin(@Validated(ChangeLogin.class)
                                                  @RequestBody TrainerDTO trainerDTO) {
        boolean passwordChange = trainerService.changePassword(trainerDTO);
        if (passwordChange) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @GetMapping(value = "/api/trainers/{username}/profile")
    public ResponseEntity<TrainerDTO> getTrainerProfile(@Valid
            @PathVariable("username") String username) {
        TrainerDTO trainerProfileByUsername = trainerService.findTrainerProfileByUsername(username);
        return new ResponseEntity<>(trainerProfileByUsername, HttpStatus.OK);
    }

    @PutMapping(value = "/api/trainers/{id}")
    public ResponseEntity<TrainerDTO> updateTrainerProfile(@Validated(TrainerDTO.UpdateTrainerProfile.class)
            @PathVariable("id") Long id, @RequestBody TrainerDTO trainerDTO)
    {

        TrainerDTO updateTrainerProfile = trainerService.updateTrainerProfile(id, trainerDTO);
        return new ResponseEntity<>(updateTrainerProfile, HttpStatus.OK);


    }
    @GetMapping(value = "/api/trainers/{username}")
    public ResponseEntity<List<TrainerDTO>> getUnassignedOnTraineeTrainersList(
            @Validated(TrainerDTO.UnassignedTrainersOnTrainee.class)
            @PathVariable("username") String username)
    {

        List<TrainerDTO> unassignedTraineesTrainersList = trainerService.findUnassignedTraineesTrainersListByTraineeUsername(username);
        return new ResponseEntity<>(unassignedTraineesTrainersList, HttpStatus.OK);

    }

    @PatchMapping(value = "/api/trainers/{username}/status")
    public ResponseEntity<Void> activateDeactivateTrainer(@Validated(TrainerDTO.ActivateDeactivateTrainer.class)
            @PathVariable("username") String username,
                                                          @RequestBody TrainerDTO trainerDTO) {

        trainerService.activateOrDeactivateTrainerStatus(username, trainerDTO.getActive());
        return new ResponseEntity<>(HttpStatus.OK);

    }

    @DeleteMapping(value = "/api/trainers/{username}")
    public ResponseEntity<String> deleteTrainerProfile(@Valid
            @PathVariable("username") String username) {

        trainerService.deleteTrainerProfileByUsername(username);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);

    }









}
