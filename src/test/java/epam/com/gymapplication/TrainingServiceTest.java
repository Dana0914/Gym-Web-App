package epam.com.gymapplication;

import epam.com.gymapplication.dao.impl.TrainingDAOImpl;
import epam.com.gymapplication.model.*;
import epam.com.gymapplication.service.TrainingService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.*;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class TrainingServiceTest {
    @Mock
    private TrainingDAOImpl trainingDAOImpl;

    @InjectMocks
    private TrainingService trainingService;

    private Training training;
    private Training training2;

    @BeforeEach
    public void setUp() {

        User user = new User();
        user.setId(1L);
        user.setUsername("John.Doe");
        user.setPassword("8&hs-@.");
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setActive(true);

        training = new Training();
        training.setId(1L);
        training.setTraineeID(1L);
        training.setTrainerID(3L);
        training.setTrainingName("stretching");
        training.setTrainingDate(LocalDate.now());
        training.setTrainingType(TrainingType.STRETCHING);
        training.setTrainingDuration(45);


        training2 = new Training();
        training2.setId(2L);
        training.setTraineeID(1L);
        training.setTrainerID(4L);
        training.setTrainingName("strength");
        training.setTrainingDate(LocalDate.now());
        training.setTrainingType(TrainingType.STRENGTH);
        training.setTrainingDuration(45);

    }
    @Test
    public void save_withValidData_returnsValidEntity() {
        trainingService.saveTraining(training);

        verify(trainingDAOImpl).save(training);

        when(trainingDAOImpl.findById(training.getId())).thenReturn(Optional.of(training));

        Optional<Training> trainingById = trainingService.findTrainingById(training.getId());

        Assertions.assertTrue(trainingById.isPresent());

        Assertions.assertEquals(training.getId(), trainingById.get().getId());
        Assertions.assertEquals(training.getTraineeID(), trainingById.get().getTraineeID());
        Assertions.assertEquals(training.getTrainerID(), trainingById.get().getTrainerID());
        Assertions.assertEquals(training.getTrainingName(), trainingById.get().getTrainingName());
        Assertions.assertEquals(training.getTrainingDate(), trainingById.get().getTrainingDate());
        Assertions.assertEquals(training.getTrainingType(), trainingById.get().getTrainingType());
        Assertions.assertEquals(training.getTrainingDuration(), trainingById.get().getTrainingDuration());
        Assertions.assertEquals(training.getTrainingType(), trainingById.get().getTrainingType());
        Assertions.assertEquals(training.getTrainingDuration(), trainingById.get().getTrainingDuration());


    }

    @Test
    public void update_withExistingEntity_updatesEntityDetails() {
        trainingService.saveTraining(training);

        verify(trainingDAOImpl).save(training);

        when(trainingDAOImpl.findById(training.getId())).thenReturn(Optional.of(training));

        Optional<Training> trainingById = trainingService.findTrainingById(training.getId());

        Assertions.assertTrue(trainingById.isPresent());

        training2.setId(training.getId());

        trainingService.updateTraining(training2);

        verify(trainingDAOImpl).update(training2);



        when(trainingDAOImpl.findById(training2.getId())).thenReturn(Optional.of(training2));

        Optional<Training> updateTrainingById = trainingService.findTrainingById(training2.getId());

        Assertions.assertTrue(updateTrainingById.isPresent());



        Assertions.assertEquals(training2.getId(), updateTrainingById.get().getId());
        Assertions.assertEquals(training2.getTraineeID(), updateTrainingById.get().getTraineeID());
        Assertions.assertEquals(training2.getTrainerID(), updateTrainingById.get().getTrainerID());
        Assertions.assertEquals(training2.getTrainingName(), updateTrainingById.get().getTrainingName());
        Assertions.assertEquals(training2.getTrainingDate(), updateTrainingById.get().getTrainingDate());
        Assertions.assertEquals(training2.getTrainingType(), updateTrainingById.get().getTrainingType());
        Assertions.assertEquals(training2.getTrainingDuration(), updateTrainingById.get().getTrainingDuration());


    }

    @Test
    public void findTrainingById_withExistingId_returnsEntity() {
        trainingService.saveTraining(training);

        verify(trainingDAOImpl).save(training);

        when(trainingDAOImpl.findById(training.getId())).thenReturn(Optional.of(training));

        Optional<Training> trainingById = trainingService.findTrainingById(training.getId());

        Assertions.assertTrue(trainingById.isPresent());

        verify(trainingDAOImpl).findById(training.getId());

        Assertions.assertEquals(training.getId(), trainingById.get().getId());
        Assertions.assertEquals(training.getTraineeID(), trainingById.get().getTraineeID());
        Assertions.assertEquals(training.getTrainerID(), trainingById.get().getTrainerID());
        Assertions.assertEquals(training.getTrainingName(), trainingById.get().getTrainingName());
        Assertions.assertEquals(training.getTrainingDate(), trainingById.get().getTrainingDate());
        Assertions.assertEquals(training.getTrainingType(), trainingById.get().getTrainingType());
        Assertions.assertEquals(training.getTrainingDuration(), trainingById.get().getTrainingDuration());

    }

    @Test
    public void findTrainingById_withNonExistingId_returnsOptionalEmpty() {
        trainingService.saveTraining(training);

        when(trainingDAOImpl.findById(4L)).thenReturn(Optional.empty());

        Optional<Training> trainingById = trainingService.findTrainingById(4L);

        Assertions.assertFalse(trainingById.isPresent());

        verify(trainingDAOImpl).findById(4L);


        Assertions.assertEquals(trainingById, Optional.empty());
    }

    @Test
    public void deleteTrainingById_withValidId_returnsValidEntity() {
        trainingService.saveTraining(training);

        verify(trainingDAOImpl).save(training);

        when(trainingDAOImpl.findById(training.getId())).thenReturn(Optional.of(training));

        trainingService.deleteTrainingById(training.getId());

        when(trainingDAOImpl.findById(training.getId())).thenReturn(Optional.empty());

        verify(trainingDAOImpl).deleteById(training.getId());

        Assertions.assertEquals(trainingService.findTrainingById(training.getId()), Optional.empty());

    }

    @Test
    public void deleteTrainingById_withInvalidId_returnsOptionalEmpty() {
        trainingService.saveTraining(training);

        when(trainingDAOImpl.findById(5L)).thenReturn(Optional.empty());

        trainingService.deleteTrainingById(5L);


        verify(trainingDAOImpl).deleteById(5L);

        Assertions.assertEquals(trainingService.findTrainingById(5L), Optional.empty());

    }



    @Test
    public void findByTrainingName_withExistingData_returnsValidEntity() {
        trainingService.saveTraining(training);

        verify(trainingDAOImpl).save(training);

        when(trainingDAOImpl.findByTrainingName(training.getTrainingName())).thenReturn(Optional.of(training));

        Optional<Training> byTrainingName = trainingService.findByTrainingName(training.getTrainingName());
        Assertions.assertTrue(byTrainingName.isPresent());

        verify(trainingDAOImpl).findByTrainingName(training.getTrainingName());

        Assertions.assertEquals(training.getId(), byTrainingName.get().getId());
        Assertions.assertEquals(training.getTrainerID(), byTrainingName.get().getTrainerID());
        Assertions.assertEquals(training.getTrainerID(), byTrainingName.get().getTrainerID());
        Assertions.assertEquals(training.getTrainingName(), byTrainingName.get().getTrainingName());
        Assertions.assertEquals(training.getTrainingDate(), byTrainingName.get().getTrainingDate());
        Assertions.assertEquals(training.getTrainingType(), byTrainingName.get().getTrainingType());
        Assertions.assertEquals(training.getTrainingDuration(), byTrainingName.get().getTrainingDuration());

    }

    @Test
    public void findByTrainingType_withExistingData_returnsValidEntity() {
        trainingService.saveTraining(training);

        verify(trainingDAOImpl).save(training);

        when(trainingDAOImpl.findByTrainingType(training.getTrainingType())).thenReturn(Optional.of(training));

        Optional<Training> byTrainingName = trainingService.findByTrainingType(training.getTrainingType());
        Assertions.assertTrue(byTrainingName.isPresent());

        verify(trainingDAOImpl).findByTrainingType(training.getTrainingType());

        Assertions.assertEquals(training.getId(), byTrainingName.get().getId());
        Assertions.assertEquals(training.getTrainerID(), byTrainingName.get().getTrainerID());
        Assertions.assertEquals(training.getTrainerID(), byTrainingName.get().getTrainerID());
        Assertions.assertEquals(training.getTrainingName(), byTrainingName.get().getTrainingName());
        Assertions.assertEquals(training.getTrainingDate(), byTrainingName.get().getTrainingDate());
        Assertions.assertEquals(training.getTrainingType(), byTrainingName.get().getTrainingType());
        Assertions.assertEquals(training.getTrainingDuration(), byTrainingName.get().getTrainingDuration());

    }


    @Test
    public void findByTrainingName_withNonExistingData_returnsOptionalEmpty() {
        trainingService.saveTraining(training);


        when(trainingDAOImpl.findByTrainingName("null")).thenReturn(Optional.empty());


        Optional<Training> result = trainingService.findByTrainingName("null");


        Assertions.assertFalse(result.isPresent());


        verify(trainingDAOImpl).findByTrainingName("null");

        Assertions.assertEquals(trainingService.findByTrainingName("null"), Optional.empty());

    }
}
