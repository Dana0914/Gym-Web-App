package epam.com.gymapplication.controller;

import epam.com.gymapplication.dto.AddTraining;
import epam.com.gymapplication.dto.GetTraineeTrainingList;
import epam.com.gymapplication.dto.GetTrainerTrainingList;
import epam.com.gymapplication.dto.TrainingDTO;
import epam.com.gymapplication.service.TrainingService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
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
    public ResponseEntity<List<TrainingDTO>> getTraineeTrainingList(@Validated(GetTraineeTrainingList.class)
            @RequestParam("username") String username, @RequestParam("from") LocalDate from,
                                                                    @RequestParam("to") LocalDate to,
                                                                    @RequestParam("trainerName") String trainerName,
                                                                    @RequestParam("trainingType") String trainingType
                                                                    ) {

        List<TrainingDTO> result = trainingService.getTraineesTrainingList(username, from, to, trainerName, trainingType);

        return ResponseEntity.ok(result);

    }

    @GetMapping("/trainer/training/list")
    public ResponseEntity<List<TrainingDTO>> getTrainerTrainingList(@Validated({GetTraineeTrainingList.class,
    GetTrainerTrainingList.class})
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
    public ResponseEntity<Void> addTraining(@Validated(AddTraining.class)
            @RequestBody TrainingDTO trainingDTO) {

        trainingService.addTraining(trainingDTO);
        return new ResponseEntity<>(HttpStatus.OK);


    }




}
