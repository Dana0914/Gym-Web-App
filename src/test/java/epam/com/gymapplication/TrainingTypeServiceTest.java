package epam.com.gymapplication;

import epam.com.gymapplication.dao.TrainingTypeRepository;
import epam.com.gymapplication.entity.TrainingType;
import epam.com.gymapplication.service.TrainingTypeService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class TrainingTypeServiceTest {

    @Mock
    private TrainingTypeRepository trainingTypeRepository;

    @InjectMocks
    private TrainingTypeService trainingTypeService;

    private TrainingType trainingType;
    private TrainingType secondTrainingType;


    @BeforeEach
    public void setUp() {
        trainingType = new TrainingType();
        trainingType.setId(1L);
        trainingType.setTrainingTypeName("stretch");

        secondTrainingType = new TrainingType();
        secondTrainingType.setId(2L);
        secondTrainingType.setTrainingTypeName("strength");

    }

    @Test
    public void save_withValidData_returnsValidEntity() {
        when(trainingTypeRepository.findById(trainingType.getId())).thenReturn(Optional.of(trainingType));


        trainingTypeService.save(trainingType);

        TrainingType trainingTypeServiceById = trainingTypeService.findById(trainingType.getId());

        verify(trainingTypeRepository).save(trainingType);

        Assertions.assertEquals(trainingTypeServiceById, trainingType);

    }

    @Test
    public void save_withInvalidValidData_returnsNull() {
        when(trainingTypeRepository.findById(trainingType.getId())).thenReturn(Optional.empty());

        trainingTypeService.save(trainingType);

        Optional<TrainingType> retrievedTrainingType = trainingTypeRepository.findById(trainingType.getId());

        verify(trainingTypeRepository).save(trainingType);

        Assertions.assertTrue(retrievedTrainingType.isEmpty());
    }



    @Test
    public void findTrainingTypeById_withExistingId_returnsEntity() {
        when(trainingTypeRepository.findById(trainingType.getId())).thenReturn(Optional.of(trainingType));

        TrainingType trainingTypeServiceById = trainingTypeService.findById(trainingType.getId());

        verify(trainingTypeRepository).findById(trainingType.getId());

        Assertions.assertEquals(trainingTypeServiceById, trainingType);

    }

    @Test
    public void findTrainingTypeById_withNonExistingId_returnsNull() {
        when(trainingTypeRepository.findById(trainingType.getId())).thenReturn(Optional.empty());

        Optional<TrainingType> trainingTypeServiceById = trainingTypeRepository.findById(trainingType.getId());

        verify(trainingTypeRepository).findById(trainingType.getId());

        Assertions.assertTrue(trainingTypeServiceById.isEmpty());

    }

    @Test
    public void deleteTrainingType_withValidEntity_deletesValidEntity() {
        when(trainingTypeRepository.findById(5L)).thenReturn(Optional.ofNullable(trainingType));

        trainingTypeRepository.deleteById(5L);

        verify(trainingTypeRepository).deleteById(5L);

        TrainingType trainingTypeById = trainingTypeService.findById(5L);

        Assertions.assertEquals(trainingTypeById, trainingType);

    }

    @Test
    public void deleteTrainingType_withInvalidId_returnsEmpty() {
        when(trainingTypeRepository.findById(5L)).thenReturn(Optional.empty());

        trainingTypeService.deleteById(5L);

        verify(trainingTypeRepository).deleteById(5L);

        Optional<TrainingType> trainingTypeById = trainingTypeRepository.findById(5L);

        Assertions.assertTrue(trainingTypeById.isEmpty());

    }

    @Test
    public void findByTrainingTypeName_withExistingData_returnsValidEntity() {
        when(trainingTypeRepository.findByTrainingTypeName(trainingType.getTrainingTypeName())).thenReturn(Optional.ofNullable(trainingType));

        TrainingType trainingTypeName = trainingTypeService.findByTrainingTypeName(trainingType.getTrainingTypeName());

        verify(trainingTypeRepository).findByTrainingTypeName(trainingTypeName.getTrainingTypeName());

        Assertions.assertEquals(trainingTypeName, trainingType);

    }

    @Test
    public void findByTrainingTypeName_withNonExistingData_returnsEmpty() {
        when(trainingTypeRepository.findByTrainingTypeName("push up")).thenReturn(Optional.empty());

        Optional<TrainingType> trainingTypeName = trainingTypeRepository.findByTrainingTypeName("push up");

        verify(trainingTypeRepository).findByTrainingTypeName("push up");

        Assertions.assertTrue(trainingTypeName.isEmpty());

    }








}
