package epam.com.gymapplication.controller;


import epam.com.gymapplication.dto.*;
import epam.com.gymapplication.service.TrainerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

    @Operation(summary = "Get Trainers training list by parameters")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found training list",
                    content = {@Content(mediaType = "application/json",schema = @Schema(implementation = TrainingDTO.class))}),
            @ApiResponse(responseCode = "404",description = "No trainings found for the specified trainer",content = @Content),
            @ApiResponse(responseCode = "400",description = "Invalid request parameters",content = @Content)})

    @GetMapping("/api/trainers/trainings")
    public ResponseEntity<List<TrainingDTO>> getTrainerTrainingList(@Valid
                                                                    @RequestParam("username") String username,
                                                                    @RequestParam("from") LocalDate from,
                                                                    @RequestParam("to") LocalDate to,
                                                                    @RequestParam("traineeName") String traineeName,
                                                                    @RequestParam("trainingType") String trainingType) {


        List<TrainingDTO> result = trainerService.getTrainersTrainingList(username, from, to,
                traineeName, trainingType);

        return ResponseEntity.ok(result);
    }

    @Operation(summary = "Trainer profile registration")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Registered a trainer",
                    content = {@Content(mediaType = "application/json",schema = @Schema(implementation = TrainerDTO.class))}),
            @ApiResponse(responseCode = "404",description = "Trainer not found",content = @Content),
            @ApiResponse(responseCode = "400",description = "Invalid trainer request body",content = @Content)})

    @PostMapping(value = "api/trainers/registration")

    public ResponseEntity<TrainerDTO> registerTrainer(@Valid
            @RequestBody TrainerDTO trainerRequestDTO) {

        TrainerDTO registeredTrainerRequest = trainerService.createTrainerProfile(trainerRequestDTO);
        return new ResponseEntity<>(registeredTrainerRequest, HttpStatus.CREATED);


    }

    @Operation(summary = "Get trainer login")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Authorized the login",
                    content = {@Content(mediaType = "application/json",schema = @Schema(implementation = String.class))}),
            @ApiResponse(responseCode = "404",description = "Trainer username or password not found",content = @Content),
            @ApiResponse(responseCode = "400",description = "Invalid username or password",content = @Content)})

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

    @Operation(summary = "Change the login")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Changed the login",
                    content = {@Content(mediaType = "application/json",schema = @Schema(implementation = TrainerDTO.class))}),
            @ApiResponse(responseCode = "404",description = "Password not found",content = @Content),
            @ApiResponse(responseCode = "400",description = "Invalid password",content = @Content)})

    @PutMapping(value = "api/trainers/login")
    public ResponseEntity<String> changeLogin(@Validated(ChangeLogin.class)
                                                  @RequestBody TrainerDTO trainerDTO) {
        boolean passwordChange = trainerService.changePassword(trainerDTO);
        if (passwordChange) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @Operation(summary = "Get Trainer profile by username")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found trainer profile",
                    content = {@Content(mediaType = "application/json",schema = @Schema(implementation = TrainerDTO.class))}),
            @ApiResponse(responseCode = "404",description = "Trainer profile not found by username",content = @Content),
            @ApiResponse(responseCode = "400",description = "Invalid username",content = @Content)})

    @GetMapping(value = "/api/trainers/{username}/profile")
    public ResponseEntity<TrainerDTO> getTrainerProfile(@Valid
            @PathVariable("username") String username) {
        TrainerDTO trainerProfileByUsername = trainerService.findTrainerProfileByUsername(username);
        return new ResponseEntity<>(trainerProfileByUsername, HttpStatus.OK);
    }


    @Operation(summary = "Update trainer profile by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Updated trainer profile",
                    content = {@Content(mediaType = "application/json",schema = @Schema(implementation = TrainerDTO.class))}),
            @ApiResponse(responseCode = "404",description = "Trainer not found by id",content = @Content),
            @ApiResponse(responseCode = "400",description = "Invalid trainer id",content = @Content)})

    @PutMapping(value = "/api/trainers/{id}")
    public ResponseEntity<TrainerDTO> updateTrainerProfile(@Validated(TrainerDTO.UpdateTrainerProfile.class)
            @PathVariable("id") Long id, @RequestBody TrainerDTO trainerDTO)
    {

        TrainerDTO updateTrainerProfile = trainerService.updateTrainerProfile(id, trainerDTO);
        return new ResponseEntity<>(updateTrainerProfile, HttpStatus.OK);

    }

    @Operation(summary = "Get unassigned trainers list on trainee")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found unassigned trainers list",
                    content = {@Content(mediaType = "application/json",schema = @Schema(implementation = TrainerDTO.class))}),
            @ApiResponse(responseCode = "404",description = "Trainers list not found by trainee username",content = @Content),
            @ApiResponse(responseCode = "400",description = "Invalid trainee username",content = @Content)})

    @GetMapping(value = "/api/trainers/{username}")
    public ResponseEntity<List<TrainerDTO>> getUnassignedOnTraineeTrainersList(
            @Validated(TrainerDTO.UnassignedTrainersOnTrainee.class)
            @PathVariable("username") String username)
    {

        List<TrainerDTO> unassignedTraineesTrainersList = trainerService.findUnassignedTraineesTrainersListByTraineeUsername(username);
        return new ResponseEntity<>(unassignedTraineesTrainersList, HttpStatus.OK);

    }

    @Operation(summary = "Activate or Deactivate the status")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Changed status of trainer profile",
                    content = {@Content(mediaType = "application/json",schema = @Schema(implementation = TrainerDTO.class))}),
            @ApiResponse(responseCode = "404",description = "Trainer not found by username",content = @Content),
            @ApiResponse(responseCode = "400",description = "Invalid trainer username",content = @Content)})

    @PatchMapping(value = "/api/trainers/{username}/status")
    public ResponseEntity<Void> activateDeactivateTrainer(@Validated(TrainerDTO.ActivateDeactivateTrainer.class)
            @PathVariable("username") String username,
                                                          @RequestBody TrainerDTO trainerDTO) {

        trainerService.activateOrDeactivateTrainerStatus(username, trainerDTO.getActive());
        return new ResponseEntity<>(HttpStatus.OK);

    }

    @Operation(summary = "Delete Trainer profile by username")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Deleted trainee profile by username",
                    content = {@Content(mediaType = "application/json",schema = @Schema(implementation = TrainerDTO.class))}),
            @ApiResponse(responseCode = "404",description = "Trainer not found to delete by username",content = @Content),
            @ApiResponse(responseCode = "400",description = "Invalid trainer username",content = @Content)})

    @DeleteMapping(value = "/api/trainers/{username}")
    public ResponseEntity<String> deleteTrainerProfile(@Valid
            @PathVariable("username") String username) {

        trainerService.deleteTrainerProfileByUsername(username);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);

    }









}
