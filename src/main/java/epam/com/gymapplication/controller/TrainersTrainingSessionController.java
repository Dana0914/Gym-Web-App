package epam.com.gymapplication.controller;


import epam.com.gymapplication.config.SwaggerConfig;
import epam.com.gymapplication.dto.TrainingDTO;
import epam.com.gymapplication.service.TrainersTrainingSessionService;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
import springfox.documentation.spring.data.rest.configuration.SpringDataRestConfiguration;


@RestController
@RequestMapping(value = "api/session")
@Api(tags = {SwaggerConfig.GYM_APP_TAG})
@Import({SpringDataRestConfiguration.class, BeanValidatorPluginsConfiguration.class})
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
    public ResponseEntity<String> addTraining(
                                              @Validated(TrainingDTO.AddTrainersTrainingWorkload.class)
                                              @RequestBody TrainingDTO trainingDTO) {


        trainersTrainingSessionService.addTraining(trainingDTO);
        return ResponseEntity.ok("Created training session");

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
    public ResponseEntity<String> deleteTraining(@Validated(TrainingDTO.AddTrainersTrainingWorkload.class)
                                                     @RequestBody TrainingDTO trainingDTO) {

        trainersTrainingSessionService.deleteTraining(trainingDTO);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);

    }


}
