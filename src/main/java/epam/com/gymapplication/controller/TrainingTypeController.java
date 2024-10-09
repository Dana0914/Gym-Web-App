package epam.com.gymapplication.controller;


import epam.com.gymapplication.dto.TrainingTypeDTO;
import epam.com.gymapplication.entity.TrainingType;
import epam.com.gymapplication.service.TrainingTypeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TrainingTypeController {

    private final TrainingTypeService trainingTypeService;

    public TrainingTypeController(TrainingTypeService trainingTypeService) {
        this.trainingTypeService = trainingTypeService;
    }

    @Operation(summary = "Find the training type list")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found training type list",
                    content = {@Content(mediaType = "application/json",schema = @Schema(implementation = TrainingTypeDTO.class))}),
            @ApiResponse(responseCode = "404",description = "TrainingType list not found",content = @Content),
            @ApiResponse(responseCode = "400",description = "Invalid request",content = @Content)})

    @GetMapping(value = "/api/trainingTypes")
    public ResponseEntity<List<TrainingType>> getAllTrainingTypes() {
        List<TrainingType> trainingTypes = trainingTypeService.getAllTrainingTypes();
        return new ResponseEntity<>(trainingTypes, HttpStatus.OK);
    }
}
