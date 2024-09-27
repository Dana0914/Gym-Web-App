package epam.com.gymapplication.controller;

import epam.com.gymapplication.dto.TraineeDTO;
import epam.com.gymapplication.dto.TrainerDTO;
import epam.com.gymapplication.dto.TrainingDTO;
import epam.com.gymapplication.entity.Trainee;
import epam.com.gymapplication.entity.Trainer;
import epam.com.gymapplication.entity.Training;
import epam.com.gymapplication.service.TrainingService;
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

//    @PostMapping(value = "/create")
//    @ResponseBody
//    public ResponseEntity<?> addTraining(TraineeDTO traineeDTO, TrainerDTO trainerDTO, TrainingDTO trainingDTO) {
//
//        trainingService.addTraining(traineeDTO, trainerDTO, trainingDTO);
//        return ResponseEntity.ok().body("Training added");
//
//    }




}
