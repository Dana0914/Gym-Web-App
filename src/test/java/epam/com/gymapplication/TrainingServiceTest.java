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


        TrainingType trainingType = new TrainingType();
        trainingType.setTrainingTypeName("stretch");

        training = new Training();
        training.setTraineeID(1L);
        training.setTrainerID(3L);
        training.setTrainingName("stretching");
        training.setTrainingDate(LocalDate.now());
        training.setTrainingType(trainingType);
        training.setTrainingDuration(45);


        training2 = new Training();
        training2.setTraineeID(1L);
        training2.setTrainerID(4L);
        training2.setTrainingName("strength");
        training2.setTrainingDate(LocalDate.now());
        training2.setTrainingType(trainingType);
        training2.setTrainingDuration(45);

    }

    @Test
    public void save_withValidData_returnsValidEntity() {
        trainingService.saveTraining(training);


        when(trainingDAOImpl.findById(training.getId())).thenReturn(Optional.of(training));

        Optional<Training> trainingById = trainingService.findTrainingById(training.getId());

        Assertions.assertTrue(trainingById.isPresent());

        verify(trainingDAOImpl).save(training);

        Assertions.assertEquals(training, trainingById.get());


    }

    @Test
    public void update_withExistingEntity_updatesEntityDetails() {
        trainingService.saveTraining(training);

        when(trainingDAOImpl.findById(training.getId())).thenReturn(Optional.of(training));

        Optional<Training> trainingById = trainingService.findTrainingById(training.getId());

        Assertions.assertTrue(trainingById.isPresent());

        training2.setId(training.getId());

        trainingService.updateTraining(training2);

        verify(trainingDAOImpl).update(training2);


        when(trainingDAOImpl.findById(training2.getId())).thenReturn(Optional.of(training2));

        Optional<Training> updateTrainingById = trainingService.findTrainingById(training2.getId());

        Assertions.assertTrue(updateTrainingById.isPresent());
        Assertions.assertEquals(training2, updateTrainingById.get());

    }

    @Test
    public void findTrainingById_withExistingId_returnsEntity() {
        trainingService.saveTraining(training);


        when(trainingDAOImpl.findById(training.getId())).thenReturn(Optional.of(training));

        Optional<Training> trainingById = trainingService.findTrainingById(training.getId());

        Assertions.assertTrue(trainingById.isPresent());

        verify(trainingDAOImpl).findById(training.getId());

        Assertions.assertEquals(training, trainingById.get());

    }

    @Test
    public void findTrainingById_withNonExistingId_returnsOptionalEmpty() {
        trainingService.saveTraining(training);

        when(trainingDAOImpl.findById(4L)).thenReturn(Optional.empty());

        Assertions.assertThrows(NoSuchElementException.class, () -> {
            trainingService.findTrainingById(4L).orElseThrow();
        });

        verify(trainingDAOImpl).findById(4L);
    }

    @Test
    public void deleteTrainingById_withValidId_returnsValidEntity() {
        trainingService.saveTraining(training);


        when(trainingDAOImpl.findById(training.getId())).thenReturn(Optional.of(training));

        trainingService.deleteTraining(training);

        when(trainingDAOImpl.findById(training.getId())).thenReturn(Optional.empty());

        verify(trainingDAOImpl).delete(training);

        Assertions.assertThrows(NoSuchElementException.class, () -> {
            trainingService.findTrainingById(training.getId()).orElseThrow();
        });

    }

    @Test
    public void deleteTrainingById_withInvalidId_returnsOptionalEmpty() {
        trainingService.saveTraining(training);

        when(trainingDAOImpl.findById(5L)).thenReturn(Optional.empty());

        trainingService.deleteTraining(training);

        verify(trainingDAOImpl).delete(training);

        Assertions.assertThrows(NoSuchElementException.class, () -> {
            trainingService.findTrainingById(5L).orElseThrow();
        });

    }


    @Test
    public void findByTrainingName_withExistingData_returnsValidEntity() {
        trainingService.saveTraining(training);


        when(trainingDAOImpl.findByTrainingName(training.getTrainingName())).thenReturn(Optional.of(training));

        Optional<Training> byTrainingName = trainingService.findByTrainingName(training.getTrainingName());
        Assertions.assertTrue(byTrainingName.isPresent());

        verify(trainingDAOImpl).findByTrainingName(training.getTrainingName());

        Assertions.assertEquals(training, byTrainingName.get());

    }

    @Test
    public void findByTrainingType_withExistingData_returnsValidEntity() {
        trainingService.saveTraining(training);

        verify(trainingDAOImpl).save(training);

        when(trainingDAOImpl.findByTrainingType(training.getTrainingType().toString())).thenReturn(Optional.of(training));

        Optional<Training> byTrainingName = trainingService.findByTrainingType(training.getTrainingType().toString());

        Assertions.assertTrue(byTrainingName.isPresent());

        verify(trainingDAOImpl).findByTrainingType(training.getTrainingType().toString());

        Assertions.assertEquals(training, byTrainingName.get());


    }


    @Test
    public void findByTrainingName_withNonExistingData_returnsOptionalEmpty() {
        trainingService.saveTraining(training);


        when(trainingDAOImpl.findByTrainingName("null")).thenReturn(Optional.empty());


        Optional<Training> result = trainingService.findByTrainingName("null");


        Assertions.assertFalse(result.isPresent());


        verify(trainingDAOImpl).findByTrainingName("null");

        Assertions.assertEquals(result, Optional.empty());

    }
}

