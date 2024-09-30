package epam.com.gymapplication.controller;

import epam.com.gymapplication.dto.TrainingDTO;
import epam.com.gymapplication.service.TrainingService;
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
    public ResponseEntity<List<TrainingDTO>> getTraineeTrainingList(
            @RequestParam("username") String username,
            @RequestParam("from") LocalDate from,
            @RequestParam("to") LocalDate to,
            @RequestParam("trainerName") String trainerName,
            @RequestParam("trainingName") String trainingName) {

        List<TrainingDTO> result = trainingService.getTraineesTrainingList(username, from, to,
                trainerName, trainingName);

        return ResponseEntity.ok(result);

    }

    @GetMapping("/trainer/training/list")
    public ResponseEntity<List<TrainingDTO>> getTrainerTrainingList(@RequestParam("username") String username,
                                                                    @RequestParam("from") LocalDate from,
                                                                    @RequestParam("to") LocalDate to,
                                                                    @RequestParam("traineeName") String traineeName,
                                                                    @RequestParam("trainingName") String trainingName) {


        List<TrainingDTO> result = trainingService.getTrainersTrainingList(username, from, to,
                traineeName, trainingName);

        return ResponseEntity.ok(result);
    }

    @PostMapping(value = "/add")
    @ResponseBody
    public ResponseEntity<Void> addTraining(@RequestBody TrainingDTO trainingDTO) {

        trainingService.addTraining(trainingDTO);
        return new ResponseEntity<>(HttpStatus.OK);


    }




}
