package epam.com.gymapplication.service;

import epam.com.gymapplication.dao.TraineeRepository;
import epam.com.gymapplication.dao.TrainerRepository;
import epam.com.gymapplication.dao.TrainingRepository;
import epam.com.gymapplication.dao.TrainingTypeRepository;
import epam.com.gymapplication.dto.TrainingDTO;
import epam.com.gymapplication.entity.Trainee;
import epam.com.gymapplication.entity.Trainer;
import epam.com.gymapplication.entity.Training;
import epam.com.gymapplication.entity.TrainingType;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDate;



@Service
public class TrainingService {
    private static final Logger logger = LoggerFactory.getLogger(TrainingService.class);

    private final TrainingRepository trainingRepository;
    private final TraineeRepository traineeRepository;
    private final TrainerRepository trainerRepository;
    private final TrainingTypeRepository trainingTypeRepository;

    public TrainingService(TrainingRepository trainingRepository, TraineeRepository traineeRepository,
                           TrainerRepository trainerRepository, TrainingTypeRepository trainingTypeRepository) {
        this.trainingRepository = trainingRepository;
        this.traineeRepository = traineeRepository;
        this.trainerRepository = trainerRepository;
        this.trainingTypeRepository = trainingTypeRepository;
    }


    public void addTraining(TrainingDTO trainingDTO) {

        String traineeUsername = trainingDTO.getTraineeUsername();
        String trainerUsername = trainingDTO.getTrainerUsername();
        String trainingName = trainingDTO.getTrainingName();
        LocalDate trainingDate = trainingDTO.getTrainingDate();
        Integer trainingDuration = trainingDTO.getTrainingDuration();

        Trainee traineeByUsername = traineeRepository
                .findByUsername(traineeUsername)
                .orElseThrow(() ->
                new EntityNotFoundException("Trainee not found by username"));

        logger.info("Found trainer by username {}", trainerUsername);

        Trainer trainerByUsername = trainerRepository
                .findByUsername(trainerUsername)
                .orElseThrow(() ->
                new EntityNotFoundException("Trainer not found by username"));

        logger.info("Found trainer by username {}", trainerByUsername);

        Training trainingByName = trainingRepository
                .findByTrainingName(trainingName)
                .orElseThrow(() ->
                new EntityNotFoundException("Training not found by name"));

        logger.info("Found training by username {}", trainingByName);

        TrainingType trainingType = trainingTypeRepository
                .findById(trainingByName.getId())
                .orElseThrow(() ->
                new EntityNotFoundException("Training type not found by id"));
        logger.info("Found training type by id {}", trainingType);



        Training training = new Training();
        training.setTrainingDuration(trainingDuration);
        training.setTrainingDate(trainingDate);
        training.setTrainingType(trainingType);
        training.setTrainer(trainerByUsername);
        training.setTrainee(traineeByUsername);
        training.setTrainingName(trainingName);

        trainingRepository.save(training);


    }


    public void saveTraining(Training training) {
        trainingRepository.save(training);
        logger.info("Training saved");

    }

    public void deleteById(Long id)  {
        trainingRepository.deleteById(id);
        logger.info("Training deleted");

    }

    public Training findTrainingById(Long id) {
        logger.info("Found training by id {} ", id);
        return trainingRepository.findById(id).orElseThrow(()
                -> new EntityNotFoundException("Entity not found by id " + id));
    }


    public Training findByTrainingName(String trainingName) {
        logger.info("Training name found {} ", trainingName);
        return trainingRepository.findByTrainingName(trainingName).orElseThrow(() ->
                new EntityNotFoundException("Entity not found  by trainingName " + trainingName));
    }

    public Training findByTrainingType(String trainingType) {
        logger.info("Training type found {} ", trainingType);
        return trainingRepository.findByTrainingType(trainingType).orElseThrow(() ->
                new EntityNotFoundException("Entity not found by trainingType " + trainingType)) ;
    }
}
