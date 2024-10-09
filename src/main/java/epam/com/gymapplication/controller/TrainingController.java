package epam.com.gymapplication.controller;

import epam.com.gymapplication.dto.TrainingDTO;
import epam.com.gymapplication.service.TrainingService;
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


    @PostMapping(value = "/api/trainings")
    public ResponseEntity<Void> addTraining(@Validated(TrainingDTO.AddTraining.class)
            @RequestBody TrainingDTO trainingDTO) {

        trainingService.addTraining(trainingDTO);
        return new ResponseEntity<>(HttpStatus.OK);


    }




}
