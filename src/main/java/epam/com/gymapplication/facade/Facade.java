package epam.com.gymapplication.facade;

import epam.com.gymapplication.model.Trainee;
import epam.com.gymapplication.model.Trainer;
import epam.com.gymapplication.model.Training;
import epam.com.gymapplication.model.TrainingType;
import epam.com.gymapplication.service.TraineeService;
import epam.com.gymapplication.service.TrainerService;
import epam.com.gymapplication.service.TrainingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Optional;
import java.util.Set;


@Component
public class Facade {
    private static final Logger logger = LoggerFactory.getLogger(Facade.class);

    private final TraineeService traineeService;
    private final TrainerService trainerService;
    private final TrainingService trainingService;

    @Autowired
    public Facade(TraineeService traineeService,
                  TrainerService trainerService,
                  TrainingService trainingService) {
        this.traineeService = traineeService;
        this.trainerService = trainerService;
        this.trainingService = trainingService;
    }

    public void saveTrainee(Trainee trainee) {
        try {
            traineeService.saveTrainee(trainee);
            logger.info("Trainee saved successfully");
        } catch (Exception e) {
            logger.error("Error in saving trainee ", e);
        }
    }

    public void saveTrainer(Trainer trainer) {
        try {
            trainerService.saveTrainer(trainer);
            logger.info("Trainer saved successfully");
        } catch (Exception e) {
            logger.error("Error in saving trainer", e);
        }
    }

    public void saveTraining(Training training) {
        try {
            trainingService.saveTraining(training);
            logger.info("Training saved successfully");
        } catch (Exception e) {
            logger.error("Error in saving training", e);
        }
    }

    public void updateTrainee(Trainee trainee) {
        try {
            traineeService.updateTrainee(trainee);
            logger.info("Trainee updated successfully");
        } catch (Exception e) {
            logger.error("Error in updating trainee", e);
        }
    }

    public void updateTrainer(Trainer trainer) {
        try {
            trainerService.updateTrainer(trainer);
            logger.info("Trainer updated successfully");
        } catch (Exception e) {
            logger.error("Error in updating trainer", e);
        }
    }

    public void updateTraining(Training training) {
        try {
            trainingService.updateTraining(training);
            logger.info("Training updated successfully");
        } catch (Exception e) {
            logger.error("Error in updating training", e);
        }
    }

    public void deleteTraineeById(Long id) {
        try {
            traineeService.deleteTraineeById(id);
            logger.info("Trainee deleted by its id successfully");
        } catch (Exception e) {
            logger.error("Error in deleting trainee by id", e);
        }
    }

    public void deleteTrainerById(Long id) {
        try {
            trainerService.deleteTrainerById(id);
            logger.info("Trainer deleted by its id successfully");
        } catch (Exception e) {
            logger.error("Error in deleting trainer by id", e);
        }
    }

    public void deleteTrainingById(Long id) {
        try {
            trainingService.deleteTrainingById(id);
            logger.info("Training deleted by its id successfully");
        } catch (Exception e) {
            logger.error("Error in deleting training by id", e);
        }
    }

    public Optional<Trainee> getTraineeById(Long id) {
        if (traineeService.findTraineeById(id).isPresent()) {
            logger.info("Trainee found by its id successfully");
            return traineeService.findTraineeById(id);
        }
        logger.info("Trainee not found");
        return Optional.empty();
    }

    public Optional<Trainer> getTrainerById(Long id) {
        if (trainerService.findTrainerById(id).isPresent()) {
            logger.info("Trainer found by its id successfully");
            return trainerService.findTrainerById(id);
        }
        logger.info("Trainer not found");
        return Optional.empty();
    }

    public Optional<Training> getTrainingById(Long id) {
        if (trainingService.findTrainingById(id).isPresent()) {
            logger.info("Training found by its id successfully");
            return trainingService.findTrainingById(id);
        }
        logger.info("Training not found");
        return Optional.empty();
    }

    public Set<Map.Entry<Long, Trainee>> getAllTrainees() {
        logger.info("Get all trainee entries successfully");
        return traineeService.getAllTrainees();
    }

    public Set<Map.Entry<Long, Trainer>> getAllTrainers() {
        logger.info("Get all trainer entries successfully");
        return trainerService.getAllTrainers();
    }

    public Set<Map.Entry<Long, Training>> getAllTrainings() {
        logger.info("Get all training entries successfully");
        return trainingService.getAllTrainings();
    }

    public Optional<Trainee> findTraineeByFirstName(String firstName) {
        if (traineeService.findTraineeByFirstName(firstName).isPresent()) {
            logger.info("Trainee found by its first name successfully");
            return traineeService.findTraineeByFirstName(firstName);
        }
        logger.info("Trainee not found");
        return Optional.empty();
    }

    public Optional<Trainee> findTraineeByLastName(String lastName) {
        if (traineeService.findByLastName(lastName).isPresent()) {
            logger.info("Trainee found by its last name successfully");
            return traineeService.findByLastName(lastName);
        }
        logger.info("Trainee not found");
        return Optional.empty();
    }

    public Optional<Trainer> findTrainerByFirstName(String firstName) {
        if (trainerService.findByFirstName(firstName).isPresent()) {
            logger.info("Trainer found by its first name successfully");
            return trainerService.findByFirstName(firstName);
        }
        logger.info("Trainer not found");
        return Optional.empty();
    }

    public Optional<Trainer> findTrainerByLastName(String lastName) {
        if (trainerService.findByLastName(lastName).isPresent()) {
            logger.info("Trainer found by its last name successfully");
            return trainerService.findByLastName(lastName);
        }
        logger.info("Trainer not found");
        return Optional.empty();
    }

    public Optional<Training> findByTrainingName(String trainingName) {
        if (trainingService.findByTrainingName(trainingName).isPresent()) {
            logger.info("Training found by its trainingName successfully");
            return trainingService.findByTrainingName(trainingName);
        }
        logger.info("Training not found");
        return Optional.empty();
    }

    public Optional<Trainer> findTrainerBySpecialization(String specialization) {
        if (trainerService.findBySpecialization(specialization).isPresent()) {
            logger.info("Trainer found by its specialization successfully");
            return trainerService.findBySpecialization(specialization);
        }
        logger.info("Trainer not found");
        return Optional.empty();
    }

    public Optional<Training> findTrainingByTrainingType(TrainingType trainingType) {
        if (trainingService.findByTrainingType(trainingType).isPresent()) {
            logger.info("Training found by its trainingType successfully");
            return trainingService.findByTrainingType(trainingType);
        }
        logger.info("Training not found");
        return Optional.empty();
    }
}
