package epam.com.gymapplication.controller;


import epam.com.gymapplication.entity.TrainingType;
import epam.com.gymapplication.service.TrainingTypeService;
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

    @GetMapping(value = "/api/trainingTypes")
    public ResponseEntity<List<TrainingType>> getAllTrainingTypes() {
        List<TrainingType> trainingTypes = trainingTypeService.getAllTrainingTypes();
        return new ResponseEntity<>(trainingTypes, HttpStatus.OK);
    }
}
