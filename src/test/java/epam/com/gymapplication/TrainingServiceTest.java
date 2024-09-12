package epam.com.gymapplication;

import epam.com.gymapplication.dao.TrainingDAO;
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

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;



@ExtendWith(MockitoExtension.class)
public class TrainingServiceTest {

    @Mock
    private TrainingDAO trainingDAO;

    @InjectMocks
    private TrainingService trainingService;

    private Training training;
    private Training training2;

    @BeforeEach
    public void setUp() {


        TrainingType trainingType = new TrainingType();
        trainingType.setId(1L);
        trainingType.setTrainingTypeName("stretch");

        training = new Training();
        training.setId(1L);
        training.setTrainingName("stretching");
        training.setTrainingDate(LocalDate.now());
        training.setTrainingType(trainingType);
        training.setTrainingDuration(45);


        training2 = new Training();
        training2.setId(2L);
        training2.setTrainingName("strength");
        training2.setTrainingDate(LocalDate.now());
        training2.setTrainingType(trainingType);
        training2.setTrainingDuration(45);

    }

    @Test
    public void save_withValidData_returnsValidEntity() {
        when(trainingDAO.findById(training.getId())).thenReturn(training);

        trainingService.saveTraining(training);

        Training trainingById = trainingService.findTrainingById(training.getId());


        verify(trainingDAO).save(training);

        Assertions.assertEquals(training, trainingById);


    }

    @Test
    public void update_withExistingEntity_updatesEntityDetails() {
        when(trainingDAO.findById(training.getId())).thenReturn(training);

        trainingService.saveTraining(training);

        Training trainingById = trainingService.findTrainingById(training.getId());

        training2.setId(training.getId());

        trainingService.updateTraining(training2);


        when(trainingDAO.findById(training2.getId())).thenReturn(training2);

        Training updateTrainingById = trainingService.findTrainingById(training2.getId());

        verify(trainingDAO).update(training2);
        verify(trainingDAO).save(training);

        Assertions.assertEquals(training, trainingById);
        Assertions.assertEquals(training2, updateTrainingById);

    }

    @Test
    public void findTrainingById_withExistingId_returnsEntity() {
        when(trainingDAO.findById(training.getId())).thenReturn(training);

        trainingService.saveTraining(training);


        Training trainingById = trainingService.findTrainingById(training.getId());


        verify(trainingDAO).findById(training.getId());
        verify(trainingDAO).save(training);

        Assertions.assertEquals(training, trainingById);

    }

    @Test
    public void findTrainingById_withNonExistingId_returnsNull() {
        when(trainingDAO.findById(4L)).thenReturn(null);

        trainingService.saveTraining(training);

        Training trainingServiceTrainingById = trainingService.findTrainingById(4L);

        verify(trainingDAO).findById(4L);
        verify(trainingDAO).save(training);

        Assertions.assertNull(trainingServiceTrainingById);

    }

    @Test
    public void deleteTraining_withValidId_returnsValidEntity() {
        when(trainingDAO.findById(training.getId())).thenReturn(training);

        trainingService.saveTraining(training);

        trainingService.deleteTraining(training);

        when(trainingDAO.findById(training.getId())).thenReturn(null);

        Training trainingServiceTrainingById = trainingService.findTrainingById(training.getId());

        verify(trainingDAO).delete(training);
        verify(trainingDAO).save(training);

        Assertions.assertNull(trainingServiceTrainingById);
    }

    @Test
    public void deleteTraining_withInvalidId_returnsNull() {
        when(trainingDAO.findById(5L)).thenReturn(null);

        trainingService.saveTraining(training);

        trainingService.deleteTraining(training);

        Training trainingServiceTrainingById = trainingService.findTrainingById(5L);

        verify(trainingDAO).delete(training);
        verify(trainingDAO).save(training);


        Assertions.assertNull(trainingServiceTrainingById);

    }


    @Test
    public void findByTrainingName_withExistingData_returnsValidEntity() {
        when(trainingDAO.findByTrainingName(training.getTrainingName())).thenReturn(training);

        trainingService.saveTraining(training);

        Training byTrainingName = trainingService.findByTrainingName(training.getTrainingName());


        verify(trainingDAO).findByTrainingName(training.getTrainingName());
        verify(trainingDAO).save(training);

        Assertions.assertEquals(training, byTrainingName);

    }

    @Test
    public void findByTrainingType_withExistingData_returnsValidEntity() {
        when(trainingDAO.findByTrainingType(training.getTrainingType().toString())).thenReturn(training);

        trainingService.saveTraining(training);

        Training byTrainingName = trainingService.findByTrainingType(training.getTrainingType().toString());


        verify(trainingDAO).save(training);
        verify(trainingDAO).findByTrainingType(training.getTrainingType().toString());

        Assertions.assertEquals(training, byTrainingName);


    }


    @Test
    public void findByTrainingName_withNonExistingData_returnsNull() {
        when(trainingDAO.findByTrainingName("null")).thenReturn(null);

        trainingService.saveTraining(training);

        Training result = trainingService.findByTrainingName("null");


        verify(trainingDAO).findByTrainingName("null");
        verify(trainingDAO).save(training);

        Assertions.assertNull(result);

    }
}

