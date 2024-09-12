package epam.com.gymapplication;

import epam.com.gymapplication.dao.TrainingTypeDAO;
import epam.com.gymapplication.model.TrainingType;
import epam.com.gymapplication.service.TrainingTypeService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class TrainingTypeServiceTest {

    @Mock
    private TrainingTypeDAO trainingTypeDAO;

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
        when(trainingTypeDAO.findById(trainingType.getId())).thenReturn(trainingType);


        trainingTypeService.save(trainingType);

        TrainingType trainingTypeServiceById = trainingTypeService.findById(trainingType.getId());


        verify(trainingTypeDAO).save(trainingType);

        Assertions.assertEquals(trainingTypeServiceById, trainingType);

    }

    @Test
    public void save_withInvalidValidData_returnsNull() {
        when(trainingTypeDAO.findById(trainingType.getId())).thenReturn(null);


        trainingTypeService.save(trainingType);


        TrainingType retrievedTrainingType = trainingTypeService.findById(trainingType.getId());

        verify(trainingTypeDAO).save(trainingType);


        Assertions.assertNull(retrievedTrainingType);
    }

    @Test
    public void update_withExistingEntity_updatesEntityDetails() {
        when(trainingTypeDAO.findById(trainingType.getId())).thenReturn(trainingType);

        trainingTypeService.save(trainingType);

        TrainingType trainingTypeServiceById = trainingTypeService.findById(trainingType.getId());

        trainingType2.setId(trainingType.getId());

        trainingTypeService.update(trainingType2);

        when(trainingTypeDAO.findById(trainingType2.getId())).thenReturn(trainingType2);

        TrainingType updateTypeServiceById = trainingTypeService.findById(trainingType2.getId());


        verify(trainingTypeDAO).update(trainingType2);
        verify(trainingTypeDAO).save(trainingType);

        Assertions.assertEquals(trainingTypeServiceById, trainingType);
        Assertions.assertEquals(updateTypeServiceById, trainingType2);
    }

    @Test
    public void update_withInvalidData_returnsNull() {
        when(trainingTypeDAO.findById(trainingType.getId())).thenReturn(trainingType);

        trainingTypeService.save(trainingType);

        TrainingType trainingTypeServiceById = trainingTypeService.findById(trainingType.getId());

        trainingType2.setId(trainingType.getId());

        trainingTypeService.update(trainingType2);


        when(trainingTypeDAO.findById(15L)).thenReturn(null);

        TrainingType updatedTraineeById = trainingTypeService.findById(15L);


        verify(trainingTypeDAO).update(trainingType2);
        verify(trainingTypeDAO).save(trainingType);

        Assertions.assertEquals(trainingTypeServiceById, trainingType);
        Assertions.assertNull(updatedTraineeById);
    }

    @Test
    public void findTrainingTypeById_withExistingId_returnsEntity() {
        when(trainingTypeDAO.findById(trainingType.getId())).thenReturn(trainingType);

        trainingTypeService.save(trainingType);

        TrainingType trainingTypeServiceById = trainingTypeService.findById(trainingType.getId());


        verify(trainingTypeDAO).findById(trainingType.getId());
        verify(trainingTypeDAO).save(trainingType);

        Assertions.assertEquals(trainingTypeServiceById, trainingType);

    }

    @Test
    public void findTrainingTypeById_withNonExistingId_returnsNull() {
        when(trainingTypeDAO.findById(trainingType.getId())).thenReturn(null);

        trainingTypeService.save(trainingType);

        TrainingType trainingTypeServiceById = trainingTypeService.findById(trainingType.getId());


        verify(trainingTypeDAO).findById(trainingType.getId());
        verify(trainingTypeDAO).save(trainingType);

        Assertions.assertNull(trainingTypeServiceById);

    }

    @Test
    public void deleteTrainingType_withValidEntity_deletesValidEntity() {
        when(trainingTypeDAO.findById(trainingType.getId())).thenReturn(trainingType);

        trainingTypeService.save(trainingType);

        trainingTypeService.delete(trainingType);

        when(trainingTypeDAO.findById(trainingType.getId())).thenReturn(null);

        TrainingType trainingTypeServiceById = trainingTypeService.findById(trainingType.getId());


        verify(trainingTypeDAO).save(trainingType);
        verify(trainingTypeDAO).delete(trainingType);

        Assertions.assertNull(trainingTypeServiceById);

    }

    @Test
    public void deleteTrainingType_withInvalidId_returnsNull() {
        when(trainingTypeDAO.findById(5L)).thenReturn(null);

        trainingTypeService.save(trainingType);

        TrainingType trainingTypeServiceById = trainingTypeService.findById(5L);


        trainingTypeService.delete(trainingType);

        verify(trainingTypeDAO).delete(trainingType);
        verify(trainingTypeDAO).save(trainingType);

        Assertions.assertNull(trainingTypeServiceById);

    }

    @Test
    public void findByTrainingTypeName_withExistingData_returnsValidEntity() {
        when(trainingTypeDAO.findByTrainingTypeName(trainingType.getTrainingTypeName())).thenReturn(trainingType);

        trainingTypeService.save(trainingType);

        TrainingType trainingTypeName = trainingTypeService.findByTrainingTypeName(trainingType.getTrainingTypeName());

        verify(trainingTypeDAO).findByTrainingTypeName(trainingTypeName.getTrainingTypeName());
        verify(trainingTypeDAO).save(trainingType);

        Assertions.assertEquals(trainingTypeName, trainingType);

    }

    @Test
    public void findByTrainingTypeName_withNonExistingData_returnsNull() {
        when(trainingTypeDAO.findByTrainingTypeName("push up")).thenReturn(null);

        trainingTypeService.save(trainingType);

        TrainingType trainingTypeName = trainingTypeService.findByTrainingTypeName("push up");

        verify(trainingTypeDAO).findByTrainingTypeName("push up");
        verify(trainingTypeDAO).save(trainingType);

        Assertions.assertNull(trainingTypeName);

    }








}
