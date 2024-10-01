package epam.com.gymapplication.controller;

import epam.com.gymapplication.dto.TrainingDTO;
import epam.com.gymapplication.service.TrainingService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/trainings")
public class TrainingController {
    private final TrainingService trainingService;

    public TrainingController(TrainingService trainingService) {
        this.trainingService = trainingService;
    }


    @GetMapping("/trainee/training/list")
    public ResponseEntity<List<TrainingDTO>> getTraineeTrainingList(@Valid
            @RequestParam("username") String traineeUsername,
            @RequestParam("from") LocalDate from,
            @RequestParam("to") LocalDate to,
            @RequestParam("trainerName") String trainerName,
            @RequestParam("trainingType") String trainingType) {

        List<TrainingDTO> result = trainingService.getTraineesTrainingList(traineeUsername, from, to,
                trainerName, trainingType);

        return ResponseEntity.ok(result);

    }

    @GetMapping("/trainer/training/list")
    public ResponseEntity<List<TrainingDTO>> getTrainerTrainingList(@Valid
            @RequestParam("username") String username,
                                                                    @RequestParam("from") LocalDate from,
                                                                    @RequestParam("to") LocalDate to,
                                                                    @RequestParam("traineeName") String traineeName,
                                                                    @RequestParam("trainingType") String trainingType) {


        List<TrainingDTO> result = trainingService.getTrainersTrainingList(username, from, to,
                traineeName, trainingType);

        return ResponseEntity.ok(result);
    }

    @PostMapping(value = "/add")
    @ResponseBody
    public ResponseEntity<Void> addTraining(@Valid
            @RequestBody TrainingDTO trainingDTO) {

        trainingService.addTraining(trainingDTO);
        return new ResponseEntity<>(HttpStatus.OK);


    }




}
