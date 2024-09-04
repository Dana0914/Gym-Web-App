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
            logger.info("Trainee saved {}", trainee);
        } catch (Exception e) {
            logger.error("Exception in saving trainee ", e);
        }
    }

    public void saveTrainer(Trainer trainer) {
        try {
            trainerService.saveTrainer(trainer);
            logger.info("Trainer saved {}", trainer);
        } catch (Exception e) {
            logger.error("Exception in saving trainer", e);
        }
    }

    public void saveTraining(Training training) {
        try {
            trainingService.saveTraining(training);
            logger.info("Training saved {}", training);
        } catch (Exception e) {
            logger.error("Exception in saving training", e);
        }
    }

    public void updateTrainee(Trainee trainee) {
        try {
            traineeService.updateTrainee(trainee);
            logger.info("Trainee updated {}", trainee);
        } catch (Exception e) {
            logger.error("Exception in updating trainee", e);
        }
    }

    public void updateTrainer(Trainer trainer) {
        try {
            trainerService.updateTrainer(trainer);
            logger.info("Trainer updated {}", trainer);
        } catch (Exception e) {
            logger.error("Exception in updating trainer", e);
        }
    }

    public void updateTraining(Training training) {
        try {
            trainingService.updateTraining(training);
            logger.info("Training updated {}", training);
        } catch (Exception e) {
            logger.error("Exception in updating training", e);
        }
    }

    public void deleteTraineeById(Long id) {
        try {
            traineeService.deleteTraineeById(id);
            logger.info("Trainee deleted by its id {}", id);
        } catch (Exception e) {
            logger.error("Exception in deleting trainee by id", e);
        }
    }

    public void deleteTrainerById(Long id) {
        try {
            trainerService.deleteTrainerById(id);
            logger.info("Trainer deleted by its id {}", id);
        } catch (Exception e) {
            logger.error("Exception in deleting trainer by id", e);
        }
    }

    public void deleteTrainingById(Long id) {
        try {
            trainingService.deleteTrainingById(id);
            logger.info("Training deleted by its id {}", id);
        } catch (Exception e) {
            logger.error("Exception in deleting training by id", e);
        }
    }

    public Optional<Trainee> getTraineeById(Long id) {
        try {
            return traineeService.findTraineeById(id);
        } catch (Exception e) {
            logger.error("Exception in getting trainee by id", e);
        }
        return Optional.empty();


    }

    public Optional<Trainer> getTrainerById(Long id) {
        Optional<Trainer> trainerById = trainerService.findTrainerById(id);
        return Optional.of(trainerById).orElseThrow();
    }

    public Optional<Training> getTrainingById(Long id) {
        Optional<Training> trainingById = trainingService.findTrainingById(id);
        return Optional.of(trainingById).orElseThrow();
    }

    public Set<Map.Entry<Long, Trainee>> getAllTrainees() {
        return traineeService.getAllTrainees();
    }

    public Set<Map.Entry<Long, Trainer>> getAllTrainers() {
        return trainerService.getAllTrainers();
    }

    public Set<Map.Entry<Long, Training>> getAllTrainings() {
        return trainingService.getAllTrainings();
    }

    public Optional<Trainee> findTraineeByFirstName(String firstName) {
        try {
            return traineeService.findTraineeByFirstName(firstName);
        } catch (Exception e) {
            logger.error("Exception in finding trainee by its first name", e);
        }
        return Optional.empty();
    }

    public Optional<Trainee> findTraineeByLastName(String lastName) {
        try {
            return traineeService.findByLastName(lastName);
        } catch (Exception e) {
            logger.error("Exception in finding trainee by its last name", e);
        }
        return Optional.empty();
    }

    public Optional<Trainer> findTrainerByFirstName(String firstName) {
        try {
            return trainerService.findByFirstName(firstName);
        } catch (Exception e) {
            logger.error("Exception in finding trainer by its first name", e);
        }
        return Optional.empty();
    }

    public Optional<Trainer> findTrainerByLastName(String lastName) {
        try {
            return trainerService.findByLastName(lastName);
        } catch (Exception e) {
            logger.error("Exception in finding trainer by its last name", e);
        }
        return Optional.empty();
    }

    public Optional<Training> findByTrainingName(String trainingName) {
        try {
            return trainingService.findByTrainingName(trainingName);
        } catch (Exception e) {
            logger.error("Exception in finding training by its training name", e);
        }
        return Optional.empty();
    }

    public Optional<Trainer> findTrainerBySpecialization(String specialization) {
        try {
            return trainerService.findBySpecialization(specialization);
        } catch (Exception e) {
            logger.error("Exception in finding trainer by its specialization", e);
        }
        return Optional.empty();
    }

    public Optional<Training> findTrainingByTrainingType(TrainingType trainingType) {
        try {
            return trainingService.findByTrainingType(trainingType);
        } catch (Exception e) {
            logger.error("Exception in finding training by its training type", e);
        }
        return Optional.empty();

    }
}
