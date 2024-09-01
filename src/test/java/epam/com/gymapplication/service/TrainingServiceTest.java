package epam.com.gymapplication.service;

import epam.com.gymapplication.config.AppConfig;
import epam.com.gymapplication.model.Training;
import epam.com.gymapplication.model.TrainingType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.util.Map;
import java.util.Optional;
import java.util.Set;


@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {AppConfig.class, TrainingService.class})
public class TrainingServiceTest {

    @Autowired
    private TrainingService trainingService;
    private Training training;



    @BeforeEach
    public void setUp() {
        training = new Training();
        training.setId(4L);
        training.setTraineeID(1L);
        training.setTrainerID(1L);
        training.setTrainingDate(LocalDate.now());
        training.setTrainingName("stretch");
        training.setTrainingType(TrainingType.STRETCHING);
        training.setTrainingDuration(45);

        // Add a trainee with ID 5L for the update test
        Training updatedTraining = new Training();
        updatedTraining.setId(5L);
        updatedTraining.setTrainerID(1L);
        updatedTraining.setTrainerID(1L);
        updatedTraining.setTrainingDate(LocalDate.now());
        updatedTraining.setTrainingName("stretch");
        updatedTraining.setTrainingType(TrainingType.STRETCHING);
        updatedTraining.setTrainingDuration(45);
        trainingService.saveTraining(updatedTraining);

    }

    @Test
    public void save_withValidData_returnsValidEntity() {
        trainingService.saveTraining(training);
        Assertions.assertNotNull(training);


        Training retrievedTrainer = trainingService.findTrainingById(4L).orElse(null);
        Assertions.assertNotNull(retrievedTrainer);


        Assertions.assertEquals(training.getId(), retrievedTrainer.getId());
        Assertions.assertEquals(training.getTrainerID(), retrievedTrainer.getTrainerID());
        Assertions.assertEquals(training.getTrainerID(), retrievedTrainer.getTrainerID());
        Assertions.assertEquals(training.getTrainingName(), retrievedTrainer.getTrainingName());
        Assertions.assertEquals(training.getTrainingType(), retrievedTrainer.getTrainingType());
        Assertions.assertEquals(training.getTrainingDuration(), retrievedTrainer.getTrainingDuration());
        Assertions.assertEquals(training.getTrainingDate(), retrievedTrainer.getTrainingDate());

    }

    @Test
    public void update_withExistingEntity_updatesEntityDetails() {
        Training existingTraining = trainingService.findTrainingById(5L).orElse(null);
        Assertions.assertNotNull(existingTraining);

        trainingService.updateTraining(existingTraining);

        Training retrievedTraining = trainingService.findTrainingById(5L).orElse(null);
        Assertions.assertNotNull(retrievedTraining);


        Assertions.assertEquals(existingTraining.getId(), retrievedTraining.getId());
        Assertions.assertEquals(existingTraining.getTrainerID(), retrievedTraining.getTrainerID());
        Assertions.assertEquals(existingTraining.getTraineeID(), retrievedTraining.getTraineeID());
        Assertions.assertEquals(existingTraining.getTrainingName(), retrievedTraining.getTrainingName());
        Assertions.assertEquals(existingTraining.getTrainingType(), retrievedTraining.getTrainingType());
        Assertions.assertEquals(existingTraining.getTrainingDuration(), retrievedTraining.getTrainingDuration());
        Assertions.assertEquals(existingTraining.getTrainingDate(), retrievedTraining.getTrainingDate());

    }

    @Test
    public void deleteById_withValidId_returnsValidEntity() {
        Training retrievedTraining = trainingService.findTrainingById(5L).orElse(null);
        Assertions.assertNotNull(retrievedTraining);

        trainingService.deleteTrainingById(retrievedTraining.getId());

        Assertions.assertEquals(trainingService.findTrainingById(5L), Optional.empty());


    }

    @Test
    public void findById_withExistingId_returnsEntity() {
        Training retrievedTraining = trainingService.findTrainingById(4L).orElse(null);
        Assertions.assertNotNull(retrievedTraining);

        Assertions.assertEquals(retrievedTraining.getId(), training.getId());
        Assertions.assertEquals(retrievedTraining.getTrainerID(), training.getTrainerID());
        Assertions.assertEquals(retrievedTraining.getTraineeID(), training.getTraineeID());
        Assertions.assertEquals(retrievedTraining.getTrainingDate(), training.getTrainingDate());
        Assertions.assertEquals(retrievedTraining.getTrainingType(), training.getTrainingType());
        Assertions.assertEquals(retrievedTraining.getTrainingName(), training.getTrainingName());
        Assertions.assertEquals(retrievedTraining.getTrainingDuration(), training.getTrainingDuration());



    }

    @Test
    public void findAll_withExistingData_returnsValidEntities() {
        Set<Map.Entry<Long, Training>> all = trainingService.getAllTrainings();

        Assertions.assertNotNull(all);
        Assertions.assertFalse(all.isEmpty());

    }

    @Test
    public void findByTrainingType_withExistingData_returnValidEntity() {
        Optional<Training> trainingType = trainingService.findByTrainingType(training.getTrainingType());

        Assertions.assertNotNull(trainingType);
        Assertions.assertTrue(trainingType.isPresent());
    }

    @Test
    public void findingByTrainingName_withExistingData_returnValidEntity() {
        Optional<Training> trainingName = trainingService.findByTrainingName(training.getTrainingName());

        Assertions.assertNotNull(trainingName);
        Assertions.assertTrue(trainingName.isPresent());
    }

}
