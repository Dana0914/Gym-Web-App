package epam.com.gymapplication.facade;

import epam.com.gymapplication.config.AppConfig;
import epam.com.gymapplication.model.*;
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
@ContextConfiguration(classes = {AppConfig.class, Facade.class})
public class FacadeTest {

    @Autowired
    private Facade facade;


    private Trainee trainee;
    private Trainer trainer;
    private Training training;


    @BeforeEach
    public void setUp() {
        User user = new User();
        user.setUsername("Rose.Tul");
        user.setPassword("k57)-3fh");
        user.setFirstName("Rose");
        user.setLastName("Tul");

        trainee = new Trainee();
        trainee.setId(4L);
        trainee.setUserId(4L);
        trainee.setDateOfBirth(LocalDate.of(2000, 1, 1));
        trainee.setAddress("Minnesota street 25");
        trainee.setUser(user);
        facade.saveTrainee(trainee);


        trainer = new Trainer();
        trainer.setId(5L);
        trainer.setUserId(5L);
        trainer.setSpecialization("muscles");
        trainer.setUser(user);
        facade.saveTrainer(trainer);


        training = new Training();
        training.setId(6L);
        training.setTrainerID(5L);
        training.setTraineeID(4L);
        training.setTrainingDuration(45);
        training.setTrainingType(TrainingType.STRENGTH);
        training.setTrainingName("strength");
        training.setTrainingDate(LocalDate.now());
        facade.saveTraining(training);


    }

    @Test
    public void saveTrainee_withValidData_returnsValidEntity() {
        facade.saveTrainee(trainee);
        Assertions.assertNotNull(trainee);

        Trainee retrievedTrainee = facade.getTraineeById(4L).orElse(null);
        Assertions.assertNotNull(retrievedTrainee);


        Assertions.assertEquals(trainee.getId(), retrievedTrainee.getId());
        Assertions.assertEquals(trainee.getUserId(), retrievedTrainee.getUserId());
        Assertions.assertEquals(trainee.getDateOfBirth(), retrievedTrainee.getDateOfBirth());
        Assertions.assertEquals(trainee.getAddress(), retrievedTrainee.getAddress());
        Assertions.assertEquals(trainee.getUser().getFirstName(), retrievedTrainee.getUser().getFirstName());
        Assertions.assertEquals(trainee.getUser().getLastName(), retrievedTrainee.getUser().getLastName());
        Assertions.assertEquals(trainee.getUser().getUsername(), retrievedTrainee.getUser().getUsername());
        Assertions.assertEquals(trainee.getUser().getFirstName(), retrievedTrainee.getUser().getFirstName());
    }

    @Test
    public void saveTrainer_withValidData_returnsValidEntity() {
        facade.saveTrainer(trainer);
        Assertions.assertNotNull(trainer);


        Trainer retrievedTrainer = facade.getTrainerById(5L).orElse(null);
        Assertions.assertNotNull(retrievedTrainer);


        Assertions.assertEquals(trainer.getId(), retrievedTrainer.getId());
        Assertions.assertEquals(trainer.getUserId(), retrievedTrainer.getUserId());
        Assertions.assertEquals(trainer.getSpecialization(), retrievedTrainer.getSpecialization());
        Assertions.assertEquals(trainer.getUser().getFirstName(), retrievedTrainer.getUser().getFirstName());
        Assertions.assertEquals(trainer.getUser().getLastName(), retrievedTrainer.getUser().getLastName());
        Assertions.assertEquals(trainer.getUser().getUsername(), retrievedTrainer.getUser().getUsername());
        Assertions.assertEquals(trainer.getUser().getPassword(), retrievedTrainer.getUser().getPassword());
    }

    @Test
    public void saveTraining_withValidData_returnsValidEntity() {
        facade.saveTraining(training);
        Assertions.assertNotNull(training);

        Training retrievedTraining = facade.getTrainingById(6L).orElse(null);
        Assertions.assertNotNull(retrievedTraining);


        Assertions.assertEquals(training.getId(), retrievedTraining.getId());
        Assertions.assertEquals(training.getTrainerID(), retrievedTraining.getTrainerID());
        Assertions.assertEquals(training.getTraineeID(), retrievedTraining.getTraineeID());
        Assertions.assertEquals(training.getTrainingDuration(), retrievedTraining.getTrainingDuration());
        Assertions.assertEquals(training.getTrainingType(), retrievedTraining.getTrainingType());
        Assertions.assertEquals(training.getTrainingName(), retrievedTraining.getTrainingName());
        Assertions.assertEquals(training.getTrainingDate(), retrievedTraining.getTrainingDate());
    }

    @Test
    public void updateTrainee_withExistingEntity_updatesEntityDetails() {
        Trainee existingTrainee = facade.getTraineeById(4L).orElse(null);
        Assertions.assertNotNull(existingTrainee);

        facade.updateTrainee(existingTrainee);

        Trainee retrievedTrainee = facade.getTraineeById(4L).orElse(null);
        Assertions.assertNotNull(retrievedTrainee);


        Assertions.assertEquals(existingTrainee.getId(), retrievedTrainee.getId());
        Assertions.assertEquals(existingTrainee.getUserId(), retrievedTrainee.getUserId());
        Assertions.assertEquals(existingTrainee.getDateOfBirth(), retrievedTrainee.getDateOfBirth());
        Assertions.assertEquals(existingTrainee.getAddress(), retrievedTrainee.getAddress());
        Assertions.assertEquals(existingTrainee.getUser().getFirstName(), retrievedTrainee.getUser().getFirstName());
        Assertions.assertEquals(existingTrainee.getUser().getLastName(), retrievedTrainee.getUser().getLastName());
        Assertions.assertEquals(existingTrainee.getUser().getUsername(), retrievedTrainee.getUser().getUsername());
        Assertions.assertEquals(existingTrainee.getUser().getPassword(), retrievedTrainee.getUser().getPassword());
    }
    @Test
    public void updateTrainer_withExistingEntity_updatesEntityDetails() {
        Trainer existingTrainer = facade.getTrainerById(5L).orElse(null);
        Assertions.assertNotNull(existingTrainer);

        facade.updateTrainer(existingTrainer);
        Assertions.assertNotNull(existingTrainer);

        Trainer retrievedTrainer = facade.getTrainerById(5L).orElse(null);
        Assertions.assertNotNull(retrievedTrainer);


        Assertions.assertEquals(existingTrainer.getId(), retrievedTrainer.getId());
        Assertions.assertEquals(existingTrainer.getUserId(), retrievedTrainer.getUserId());
        Assertions.assertEquals(existingTrainer.getSpecialization(), retrievedTrainer.getSpecialization());
        Assertions.assertEquals(existingTrainer.getUser().getFirstName(), retrievedTrainer.getUser().getFirstName());
        Assertions.assertEquals(existingTrainer.getUser().getLastName(), retrievedTrainer.getUser().getLastName());
        Assertions.assertEquals(existingTrainer.getUser().getUsername(), retrievedTrainer.getUser().getUsername());
        Assertions.assertEquals(existingTrainer.getUser().getPassword(), retrievedTrainer.getUser().getPassword());

    }

    @Test
    public void updateTraining_withExistingEntity_updatesEntityDetails() {
        Training existingTraining = facade.getTrainingById(6L).orElse(null);
        Assertions.assertNotNull(existingTraining);

        facade.updateTraining(existingTraining);

        Training retrievedTraining = facade.getTrainingById(6L).orElse(null);
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
    public void deleteTraineeById_withValidId_returnsValidEntity() {
        Trainee retrievedTrainee = facade.getTraineeById(4L).orElse(null);
        Assertions.assertNotNull(retrievedTrainee);

        facade.deleteTraineeById(retrievedTrainee.getId());
        Assertions.assertEquals(facade.getTraineeById(4L), Optional.empty());

    }

    @Test
    public void deleteTrainerById_withValidId_returnsValidEntity() {
        Trainer retrievedTrainer = facade.getTrainerById(5L).get();
        Assertions.assertNotNull(retrievedTrainer);

        facade.deleteTrainerById(retrievedTrainer.getId());
        Assertions.assertEquals(facade.getTrainerById(5L), Optional.empty());

    }

    @Test
    public void deleteTrainingById_withValidId_returnsValidEntity() {
        Training retrievedTraining = facade.getTrainingById(6L).orElse(null);
        Assertions.assertNotNull(retrievedTraining);

        facade.deleteTrainingById(retrievedTraining.getId());

        Assertions.assertEquals(facade.getTrainingById(6L), Optional.empty());

    }

    @Test
    public void findTraineeById_withExistingId_returnsEntity() {
        Trainee retrievedTrainee = facade.getTraineeById(trainee.getId()).orElse(null);
        Assertions.assertNotNull(retrievedTrainee);

        Assertions.assertEquals(retrievedTrainee.getId(), trainee.getId());
        Assertions.assertEquals(retrievedTrainee.getUserId(), trainee.getUserId());
        Assertions.assertEquals(retrievedTrainee.getDateOfBirth(), trainee.getDateOfBirth());
        Assertions.assertEquals(retrievedTrainee.getAddress(), trainee.getAddress());
        Assertions.assertEquals(retrievedTrainee.getUser().getFirstName(), trainee.getUser().getFirstName());
        Assertions.assertEquals(retrievedTrainee.getUser().getLastName(), trainee.getUser().getLastName());
        Assertions.assertEquals(retrievedTrainee.getUser().getUsername(), trainee.getUser().getUsername());
        Assertions.assertEquals(retrievedTrainee.getUser().getPassword(), trainee.getUser().getPassword());

    }

    @Test
    public void findTrainerById_withExistingId_returnsEntity() {
        Trainer retrievedTrainer = facade.getTrainerById(trainer.getId()).orElse(null);
        Assertions.assertNotNull(retrievedTrainer);


        Assertions.assertEquals(retrievedTrainer.getId(), trainer.getId());
        Assertions.assertEquals(retrievedTrainer.getUser(), trainer.getUser());
        Assertions.assertEquals(retrievedTrainer.getUserId(), trainer.getUserId());
        Assertions.assertEquals(retrievedTrainer.getSpecialization(), trainer.getSpecialization());
    }

    @Test
    public void findTrainingById_withExistingId_returnsEntity() {
        Training retrievedTraining = facade.getTrainingById(training.getId()).orElse(null);
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
    public void findAllTrainees_withExistingData_returnsValidEntities() {
        Set<Map.Entry<Long, Trainee>> all = facade.getAllTrainees();

        Assertions.assertNotNull(all);
        Assertions.assertFalse(all.isEmpty());

    }

    @Test
    public void findAllTrainers_withExistingData_returnsValidEntities() {
        Set<Map.Entry<Long, Trainer>> all = facade.getAllTrainers();

        Assertions.assertNotNull(all);
        Assertions.assertFalse(all.isEmpty());

    }

    @Test
    public void findAllTrainings_withExistingData_returnsValidEntities() {
        Set<Map.Entry<Long, Training>> all = facade.getAllTrainings();

        Assertions.assertNotNull(all);
        Assertions.assertFalse(all.isEmpty());

    }

    @Test
    public void findTraineeByFirstName_withExistingData_returnsValidEntity() {
        Optional<Trainee> firstName = facade.findTraineeByFirstName(trainee.getUser().getFirstName());

        Assertions.assertNotNull(firstName);
        Assertions.assertTrue(firstName.isPresent());

    }

    @Test
    public void findTrainerByFirstName_withExistingData_returnsValidEntity() {
        Optional<Trainer> firstName = facade.findTrainerByFirstName(trainer.getUser().getFirstName());

        Assertions.assertNotNull(firstName);
        Assertions.assertTrue(firstName.isPresent());

    }

    @Test
    public void findTraineeByLastName_withExistingData_returnsValidEntity() {
        Optional<Trainee> lastName = facade.findTraineeByLastName(trainee.getUser().getLastName());

        Assertions.assertNotNull(lastName);
        Assertions.assertTrue(lastName.isPresent());
    }

    @Test
    public void findTrainerByLastName_withExistingData_returnsValidEntity() {
        Optional<Trainer> lastName = facade.findTrainerByLastName(trainer.getUser().getLastName());

        Assertions.assertNotNull(lastName);
        Assertions.assertTrue(lastName.isPresent());
    }

    @Test
    public void findTrainingByTrainingType_withExistingData_returnValidEntity() {
        Optional<Training> trainingType = facade.findTrainingByTrainingType(training.getTrainingType());

        Assertions.assertNotNull(trainingType);
        Assertions.assertTrue(trainingType.isPresent());
    }

    @Test
    public void findTrainingByTrainingName_withExistingData_returnValidEntity() {
        Optional<Training> trainingName = facade.findByTrainingName(training.getTrainingName());

        Assertions.assertNotNull(trainingName);
        Assertions.assertTrue(trainingName.isPresent());
    }





}
