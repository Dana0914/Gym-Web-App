package epam.com.gymapplication;

import epam.com.gymapplication.dao.TrainingRepository;
import epam.com.gymapplication.entity.*;
import epam.com.gymapplication.service.TrainingService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;



@ExtendWith(MockitoExtension.class)
public class TrainingServiceTest {

    @Mock
    private TrainingRepository trainingRepository;

    @InjectMocks
    private TrainingService trainingService;

    private Training training;
    private Training secondTraining;

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


        secondTraining = new Training();
        secondTraining.setId(2L);
        secondTraining.setTrainingName("strength");
        secondTraining.setTrainingDate(LocalDate.now());
        secondTraining.setTrainingType(trainingType);
        secondTraining.setTrainingDuration(45);

    }

    @Test
    public void save_withValidData_returnsValidEntity() {
        when(trainingRepository.findById(training.getId())).thenReturn(Optional.of(training));

        trainingService.saveTraining(training);

        Training trainingById = trainingService.findTrainingById(training.getId());


        verify(trainingRepository).save(training);

        Assertions.assertEquals(training, trainingById);


    }


    @Test
    public void findTrainingById_withExistingId_returnsEntity() {
        when(trainingRepository.findById(training.getId())).thenReturn(Optional.of(training));

        Training trainingById = trainingService.findTrainingById(training.getId());

        verify(trainingRepository).findById(training.getId());

        Assertions.assertEquals(training, trainingById);

    }

    @Test
    public void findTrainingById_withNonExistingId_returnsEmptyOptional() {
        when(trainingRepository.findById(4L)).thenReturn(Optional.empty());

        Optional<Training> trainingServiceTrainingById = trainingRepository.findById(4L);

        verify(trainingRepository).findById(4L);

        Assertions.assertTrue(trainingServiceTrainingById.isEmpty());

    }

    @Test
    public void deleteTraining_withValidId_returnsValidEntity() {
        when(trainingRepository.findById(training.getId())).thenReturn(Optional.of(training));

        trainingService.deleteById(training.getId());

        verify(trainingRepository).deleteById(training.getId());

        when(trainingRepository.findById(training.getId())).thenReturn(Optional.empty());

        Optional<Training> deletedTraining = trainingRepository.findById(training.getId());
        Assertions.assertTrue(deletedTraining.isEmpty());
    }

    @Test
    public void deleteTraining_withInvalidId_returnsNull() {
        when(trainingRepository.findById(5L)).thenReturn(Optional.empty());

        trainingService.deleteById(5L);

        verify(trainingRepository).deleteById(5L);

        Optional<Training> trainingById = trainingRepository.findById(5L);

        Assertions.assertTrue(trainingById.isEmpty());

    }


    @Test
    public void findByTrainingName_withExistingData_returnsValidEntity() {
        when(trainingRepository.findByTrainingName(training.getTrainingName())).thenReturn(Optional.ofNullable(training));


        Training byTrainingName = trainingService.findByTrainingName(training.getTrainingName());

        verify(trainingRepository).findByTrainingName(training.getTrainingName());

        Assertions.assertEquals(training, byTrainingName);

    }

    @Test
    public void findByTrainingType_withExistingData_returnsValidEntity() {
        when(trainingRepository.findByTrainingType(training.getTrainingType().getTrainingTypeName())).thenReturn(Optional.of(training));

        Training byTrainingName = trainingService.findByTrainingType(training.getTrainingType().getTrainingTypeName());

        verify(trainingRepository).findByTrainingType(training.getTrainingType().getTrainingTypeName());

        Assertions.assertEquals(training, byTrainingName);


    }


    @Test
    public void findByTrainingName_withNonExistingData_returnsNull() {
        when(trainingRepository.findByTrainingName("null")).thenReturn(Optional.empty());

        Optional<Training> trainingByName = trainingRepository.findByTrainingName("null");

        verify(trainingRepository).findByTrainingName("null");

        Assertions.assertTrue(trainingByName.isEmpty());

    }
}

