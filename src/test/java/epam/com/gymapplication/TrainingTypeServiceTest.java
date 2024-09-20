package epam.com.gymapplication;

import epam.com.gymapplication.dao.TrainingTypeRepository;
import epam.com.gymapplication.model.TrainingType;
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
    private TrainingType trainingType2;


    @BeforeEach
    public void setUp() {
        trainingType = new TrainingType();
        trainingType.setId(1L);
        trainingType.setTrainingTypeName("stretch");

        trainingType2 = new TrainingType();
        trainingType2.setId(2L);
        trainingType2.setTrainingTypeName("strength");

    }

    @Test
    public void save_withValidData_returnsValidEntity() {
        when(trainingTypeRepository.findById(trainingType.getId())).thenReturn(Optional.ofNullable(trainingType));


        trainingTypeService.save(trainingType);

        TrainingType trainingTypeServiceById = trainingTypeService.findById(trainingType.getId());


        verify(trainingTypeRepository).save(trainingType);

        Assertions.assertEquals(trainingTypeServiceById, trainingType);

    }

    @Test
    public void save_withInvalidValidData_returnsNull() {
        when(trainingTypeRepository.findById(trainingType.getId())).thenReturn(null);


        trainingTypeService.save(trainingType);


        TrainingType retrievedTrainingType = trainingTypeService.findById(trainingType.getId());

        verify(trainingTypeRepository).save(trainingType);


        Assertions.assertNull(retrievedTrainingType);
    }

    @Test
    public void update_withExistingEntity_updatesEntityDetails() {
        when(trainingTypeRepository.findById(trainingType.getId())).thenReturn(Optional.ofNullable(trainingType));

        trainingTypeService.save(trainingType);

        TrainingType trainingTypeServiceById = trainingTypeService.findById(trainingType.getId());

        trainingType2.setId(trainingType.getId());

        trainingTypeService.update(trainingType2);

        when(trainingTypeRepository.findById(trainingType2.getId())).thenReturn(Optional.ofNullable(trainingType2));

        TrainingType updateTypeServiceById = trainingTypeService.findById(trainingType2.getId());


        verify(trainingTypeRepository).update(trainingType2);
        verify(trainingTypeRepository).save(trainingType);

        Assertions.assertEquals(trainingTypeServiceById, trainingType);
        Assertions.assertEquals(updateTypeServiceById, trainingType2);
    }

    @Test
    public void update_withInvalidData_returnsNull() {
        when(trainingTypeRepository.findById(trainingType.getId())).thenReturn(Optional.ofNullable(trainingType));

        trainingTypeService.save(trainingType);

        TrainingType trainingTypeServiceById = trainingTypeService.findById(trainingType.getId());

        trainingType2.setId(trainingType.getId());

        trainingTypeService.update(trainingType2);


        when(trainingTypeRepository.findById(15L)).thenReturn(null);

        TrainingType updatedTraineeById = trainingTypeService.findById(15L);


        verify(trainingTypeRepository).update(trainingType2);
        verify(trainingTypeRepository).save(trainingType);

        Assertions.assertEquals(trainingTypeServiceById, trainingType);
        Assertions.assertNull(updatedTraineeById);
    }

    @Test
    public void findTrainingTypeById_withExistingId_returnsEntity() {
        when(trainingTypeRepository.findById(trainingType.getId())).thenReturn(Optional.ofNullable(trainingType));

        trainingTypeService.save(trainingType);

        TrainingType trainingTypeServiceById = trainingTypeService.findById(trainingType.getId());


        verify(trainingTypeRepository).findById(trainingType.getId());
        verify(trainingTypeRepository).save(trainingType);

        Assertions.assertEquals(trainingTypeServiceById, trainingType);

    }

    @Test
    public void findTrainingTypeById_withNonExistingId_returnsNull() {
        when(trainingTypeRepository.findById(trainingType.getId())).thenReturn(null);

        trainingTypeService.save(trainingType);

        TrainingType trainingTypeServiceById = trainingTypeService.findById(trainingType.getId());


        verify(trainingTypeRepository).findById(trainingType.getId());
        verify(trainingTypeRepository).save(trainingType);

        Assertions.assertNull(trainingTypeServiceById);

    }

    @Test
    public void deleteTrainingType_withValidEntity_deletesValidEntity() {
        when(trainingTypeRepository.findById(trainingType.getId())).thenReturn(Optional.ofNullable(trainingType));

        trainingTypeService.save(trainingType);

        trainingTypeService.deleteById(trainingType.getId());

        when(trainingTypeRepository.findById(trainingType.getId())).thenReturn(null);

        TrainingType trainingTypeServiceById = trainingTypeService.findById(trainingType.getId());


        verify(trainingTypeRepository).save(trainingType);
        verify(trainingTypeRepository).deleteById(trainingType.getId());

        Assertions.assertNull(trainingTypeServiceById);

    }

    @Test
    public void deleteTrainingType_withInvalidId_returnsNull() {
        when(trainingTypeRepository.findById(5L)).thenReturn(null);

        trainingTypeService.save(trainingType);

        TrainingType trainingTypeServiceById = trainingTypeService.findById(5L);


        trainingTypeService.deleteById(trainingType.getId());

        verify(trainingTypeRepository).delete(trainingType);
        verify(trainingTypeRepository).save(trainingType);

        Assertions.assertNull(trainingTypeServiceById);

    }

    @Test
    public void findByTrainingTypeName_withExistingData_returnsValidEntity() {
        when(trainingTypeRepository.findByTrainingTypeName(trainingType.getTrainingTypeName())).thenReturn(trainingType);

        trainingTypeService.save(trainingType);

        TrainingType trainingTypeName = trainingTypeService.findByTrainingTypeName(trainingType.getTrainingTypeName()).orElseThrow();

        verify(trainingTypeRepository).findByTrainingTypeName(trainingTypeName.getTrainingTypeName());
        verify(trainingTypeRepository).save(trainingType);

        Assertions.assertEquals(trainingTypeName, trainingType);

    }

    @Test
    public void findByTrainingTypeName_withNonExistingData_returnsNull() {
        when(trainingTypeRepository.findByTrainingTypeName("push up")).thenReturn(null);

        trainingTypeService.save(trainingType);

        TrainingType trainingTypeName = trainingTypeService.findByTrainingTypeName("push up").orElseThrow();

        verify(trainingTypeRepository).findByTrainingTypeName("push up");
        verify(trainingTypeRepository).save(trainingType);

        //Assertions.assertNull(trainingTypeName);

    }








}
