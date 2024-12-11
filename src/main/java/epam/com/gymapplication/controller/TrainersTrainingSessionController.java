package epam.com.gymapplication.controller;


import epam.com.gymapplication.dto.TrainingDTO;
import epam.com.gymapplication.service.TrainersTrainingSessionService;
import epam.com.gymapplication.utility.exception.ResourceNotFoundException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping(value = "api/session")
public class TrainersTrainingSessionController {

    private final TrainersTrainingSessionService trainersTrainingSessionService;


    public TrainersTrainingSessionController(TrainersTrainingSessionService trainersTrainingSessionService) {

        this.trainersTrainingSessionService = trainersTrainingSessionService;

    }
    @Operation(summary = "Create a new training session")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully added the training session",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = TrainingDTO.class))}),
            @ApiResponse(responseCode = "201", description = "Training session created",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = TrainingDTO.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid request data", content = @Content),
            @ApiResponse(responseCode = "401", description = "Unauthorized access", content = @Content),
            @ApiResponse(responseCode = "403", description = "Forbidden - You don't have permission", content = @Content),
            @ApiResponse(responseCode = "404", description = "Training not found", content = @Content)
    })


    @PostMapping(value = "trainers/trainings/planned")
    public ResponseEntity<TrainingDTO> addTraining(
                                              @Validated(TrainingDTO.AddTrainersTrainingWorkload.class)
                                              @RequestBody TrainingDTO trainingDTO) {

        try {
            trainersTrainingSessionService.addTraining(trainingDTO);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(trainingDTO, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(trainingDTO, HttpStatus.OK);

    }

    @Operation(summary = "Cancel training for a trainer")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Training successfully cancelled",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "204", description = "No content - Training already cancelled or does not exist"),
            @ApiResponse(responseCode = "400", description = "Invalid request data", content = @Content),
            @ApiResponse(responseCode = "401", description = "Unauthorized access", content = @Content),
            @ApiResponse(responseCode = "403", description = "Forbidden - You don't have permission", content = @Content),
            @ApiResponse(responseCode = "404", description = "Training not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    @DeleteMapping(value = "trainers/trainings/cancelled")
    public ResponseEntity<TrainingDTO> deleteTraining(@Validated(TrainingDTO.AddTrainersTrainingWorkload.class)
                                                     @RequestBody TrainingDTO trainingDTO) {
        try {

            trainersTrainingSessionService.deleteTraining(trainingDTO);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(trainingDTO, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);

    }


}
