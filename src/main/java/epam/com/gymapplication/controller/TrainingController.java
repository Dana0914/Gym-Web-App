package epam.com.gymapplication.controller;

import epam.com.gymapplication.dto.TrainingDTO;
import epam.com.gymapplication.service.TrainingService;
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
public class TrainingController {
    private final TrainingService trainingService;

    public TrainingController(TrainingService trainingService) {
        this.trainingService = trainingService;
    }

    @Operation(summary = "Add training")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Added trainings",
                    content = {@Content(mediaType = "application/json",schema = @Schema(implementation = TrainingDTO.class))}),
            @ApiResponse(responseCode = "404",description = "Training not found to add",content = @Content),
            @ApiResponse(responseCode = "400",description = "Invalid request body",content = @Content)})

    @PostMapping(value = "/api/trainings")
    public ResponseEntity<Void> addTraining(@Validated(TrainingDTO.AddTraining.class)
            @RequestBody TrainingDTO trainingDTO) {

        trainingService.addTraining(trainingDTO);
        return new ResponseEntity<>(HttpStatus.OK);


    }




}
