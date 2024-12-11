package epam.com.gymapplication.controller;


import epam.com.gymapplication.dto.*;
import epam.com.gymapplication.service.TraineeService;
import epam.com.gymapplication.utility.exception.ResourceNotFoundException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;


import java.time.LocalDate;
import java.util.List;


@RestController
public class TraineeController {
    private final TraineeService traineeService;


    @Autowired
    public TraineeController(TraineeService traineeService) {
        this.traineeService = traineeService;
    }

    @Operation(summary = "Get Trainees training list by parameters")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found training list",
                    content = {@Content(mediaType = "application/json",schema = @Schema(implementation = TrainingDTO.class))}),
            @ApiResponse(responseCode = "404",description = "No trainings found for the specified trainee",content = @Content),
            @ApiResponse(responseCode = "400",description = "Invalid request parameters",content = @Content)})
    @GetMapping("/api/trainees/trainings")
    public ResponseEntity<List<TrainingDTO>> getTraineeTrainingList(@Valid
                                                                    @RequestParam("username") String username,
                                                                    @RequestParam("from") LocalDate from,
                                                                    @RequestParam("to") LocalDate to,
                                                                    @RequestParam("trainerName") String trainerName,
                                                                    @RequestParam("trainingType") String trainingType
    ) throws ResourceNotFoundException {

        List<TrainingDTO> result = traineeService.getTraineesTrainingList(username, from, to, trainerName, trainingType);

        return ResponseEntity.ok(result);

    }

    @Operation(summary = "Trainee profile registration")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Registered a trainee",
                    content = {@Content(mediaType = "application/json",schema = @Schema(implementation = TraineeDTO.class))}),
            @ApiResponse(responseCode = "404",description = "Trainee not found",content = @Content),
            @ApiResponse(responseCode = "400",description = "Invalid trainee request body",content = @Content)})
    @PostMapping(value = "/api/trainees/registration")

    public ResponseEntity<TraineeDTO> registerTrainee(@Valid
            @RequestBody TraineeDTO traineeRequest) {

        TraineeDTO registeredTraineeRequest = traineeService.createTraineeProfile(traineeRequest);
        return new ResponseEntity<>(registeredTraineeRequest, HttpStatus.CREATED);

    }

    @Operation(summary = "Get trainee login")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Authorized the login",
                    content = {@Content(mediaType = "application/json",schema = @Schema(implementation = String.class))}),
            @ApiResponse(responseCode = "404",description = "Trainee username or password not found",content = @Content),
            @ApiResponse(responseCode = "400",description = "Invalid username or password",content = @Content)})

    @GetMapping(value = "/api/trainees/login")
    public ResponseEntity<String> login(@Valid @RequestParam("username") String username,
                                        @RequestParam("password") String password) throws BadRequestException, ResourceNotFoundException {

        if (traineeService.authenticateTraineeProfile(username, password)) {
            return ResponseEntity.ok("Login successful");

        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

    }



    @Operation(summary = "Change the login")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Changed the login",
                    content = {@Content(mediaType = "application/json",schema = @Schema(implementation = TraineeDTO.class))}),
            @ApiResponse(responseCode = "404",description = "Password not found",content = @Content),
            @ApiResponse(responseCode = "400",description = "Invalid password",content = @Content)})

    @PutMapping("/api/trainees/change-login")
    public ResponseEntity<TraineeDTO> changeLogin(@Validated(ChangeLogin.class)
                                                      @RequestBody TraineeDTO traineeDTO) {
        try {
            traineeService.changePassword(traineeDTO);
        } catch (BadRequestException | ResourceNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
        return ResponseEntity.ok(traineeDTO);

    }

    @Operation(summary = "Get Trainee profile by username")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found trainee profile",
                    content = {@Content(mediaType = "application/json",schema = @Schema(implementation = TraineeDTO.class))}),
            @ApiResponse(responseCode = "404",description = "Trainee profile not found by username",content = @Content),
            @ApiResponse(responseCode = "400",description = "Invalid username",content = @Content)})

    @GetMapping(value = "/api/trainees/{username}")
    public ResponseEntity<TraineeDTO> getTraineeProfile(@Valid
            @PathVariable("username") String username) throws ResourceNotFoundException {

        TraineeDTO traineeProfileByUsername = traineeService.findTraineeProfileByUsername(username);
        return ResponseEntity.ok(traineeProfileByUsername);


    }

    @Operation(summary = "Update trainee profile by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Updated trainee profile",
                    content = {@Content(mediaType = "application/json",schema = @Schema(implementation = TraineeDTO.class))}),
            @ApiResponse(responseCode = "404",description = "Trainee not found by id",content = @Content),
            @ApiResponse(responseCode = "400",description = "Invalid trainee id",content = @Content)})

    @PutMapping(value = "/api/trainees/{id}")
    public ResponseEntity<TraineeDTO> updateTraineeProfile(@Validated(TraineeDTO.UpdatedTraineeProfile.class)
                                                               @PathVariable("id") Long id,
                                                           @RequestBody
                                                           TraineeDTO traineeDTO) throws ResourceNotFoundException {

        TraineeDTO updateTraineeProfile = traineeService.updateTraineeProfile(id, traineeDTO);
        return new ResponseEntity<>(updateTraineeProfile, HttpStatus.OK);


    }

    @Operation(summary = "Delete Trainee by username")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Deleted an trainee by username",
                    content = {@Content(mediaType = "application/json",schema = @Schema(implementation = TraineeDTO.class))}),
            @ApiResponse(responseCode = "404",description = "Trainee not found to delete",content = @Content),
            @ApiResponse(responseCode = "400",description = "Invalid trainee username",content = @Content)})

    @DeleteMapping(value = "/api/trainees/{username}")
    public ResponseEntity<Void> deleteTraineeProfile(@Validated(TraineeDTO.DeleteTraineeProfile.class)
            @PathVariable("username") String username) throws ResourceNotFoundException {

        traineeService.deleteTraineeProfileByUsername(username);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);

    }

    @Operation(summary = "Update Trainees training list")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Updated Trainees Trainers List",
                    content = {@Content(mediaType = "application/json",schema = @Schema(implementation = TraineeDTO.class))}),
            @ApiResponse(responseCode = "404",description = "Trainees trainer list not found",content = @Content),
            @ApiResponse(responseCode = "400",description = "Invalid request body",content = @Content)})

    @PutMapping(value = "/api/trainees/trainers")
    public ResponseEntity<List<TrainerDTO>> updateTraineesTrainerList(
            @Validated(TraineeDTO.UpdatedTraineesTrainerList.class)
            @RequestBody TraineeDTO traineeDTO) throws ResourceNotFoundException {

        List<TrainerDTO> trainerDTOS = traineeService.updateTraineesTrainerList(traineeDTO);
        return new ResponseEntity<>(trainerDTOS, HttpStatus.OK);
    }


    @Operation(summary = "Change the status of trainee profile")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Activated/Deactivated the status",
                    content = {@Content(mediaType = "application/json",schema = @Schema(implementation = TraineeDTO.class))}),
            @ApiResponse(responseCode = "404",description = "Trainee not found by username",content = @Content),
            @ApiResponse(responseCode = "400",description = "Invalid trainee username",content = @Content)})

    @PatchMapping(value = "/api/trainees/{username}/status")
    public ResponseEntity<Void> activateDeactivateTrainee(@Validated(TraineeDTO.ActivateDeactivateTrainee.class)
            @PathVariable("username") String username,
            @RequestBody TraineeDTO traineeDTO) {

        try {
            traineeService.activateOrDeactivateTraineeStatus(username, traineeDTO.getActive());
        } catch (BadRequestException | ResourceNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }


























}
