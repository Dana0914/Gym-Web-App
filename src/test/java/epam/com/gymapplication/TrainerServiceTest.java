package epam.com.gymapplication;


import epam.com.gymapplication.dao.TrainerDAO;
import epam.com.gymapplication.model.Trainer;
import epam.com.gymapplication.model.TrainingType;
import epam.com.gymapplication.model.User;
import epam.com.gymapplication.service.TrainerService;
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
public class TrainerServiceTest {

    @Mock
    private TrainerDAO trainerDAO;

    @InjectMocks
    private TrainerService trainerService;

    private Trainer trainer;
    private Trainer trainer2;

    @BeforeEach
    public void setUp() {

        User user = new User();
        user.setId(1L);
        user.setUsername("John.Doe");
        user.setPassword("8&hs-@.");
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setActive(true);

        TrainingType trainingType = new TrainingType();
        trainingType.setId(1L);
        trainingType.setTrainingTypeName("aerobics");


        trainer = new Trainer();
        trainer.setId(1L);
        trainer.setUser(user);
        trainer.setTrainingType(trainingType);

        trainer2 = new Trainer();
        trainer2.setId(2L);
        trainer2.setUser(user);
        trainer2.setTrainingType(trainingType);


    }

    @Test
    public void save_withValidData_returnsValidEntity() {
        when(trainerDAO.findById(trainer.getId())).thenReturn(trainer);

        trainerService.saveTrainer(trainer);

        Trainer trainerById = trainerService.findTrainerById(trainer.getId());

        verify(trainerDAO).save(trainer);

        Assertions.assertEquals(trainerById, trainer);


    }

    @Test
    public void update_withExistingEntity_updatesEntityDetails() {
        when(trainerDAO.findById(trainer.getId())).thenReturn(trainer);

        trainerService.saveTrainer(trainer);

        Trainer trainerById = trainerService.findTrainerById(trainer.getId());

        trainer2.setId(trainer.getId());

        trainerService.updateTrainer(trainer2);

        when(trainerDAO.findById(trainer2.getId())).thenReturn(trainer2);

        Trainer updatedTrainerById = trainerService.findTrainerById(trainer2.getId());

        verify(trainerDAO).update(trainer2);
        verify(trainerDAO).save(trainer);


        Assertions.assertEquals(trainerById, trainer);
        Assertions.assertEquals(updatedTrainerById, trainer2);


    }

    @Test
    public void findTrainerById_withExistingId_returnsEntity() {
        when(trainerDAO.findById(trainer.getId())).thenReturn(trainer);

        trainerService.saveTrainer(trainer);

        Trainer trainerById = trainerService.findTrainerById(trainer.getId());


        verify(trainerDAO).findById(trainer.getId());
        verify(trainerDAO).save(trainer);

        Assertions.assertEquals(trainerById, trainer);

    }

    @Test
    public void findTrainerById_withNonExistingId_returnsNull() {
        when(trainerDAO.findById(4L)).thenReturn(null);

        trainerService.saveTrainer(trainer);

        Trainer trainerById = trainerService.findTrainerById(4L);

        verify(trainerDAO).findById(4L);
        verify(trainerDAO).save(trainer);

        Assertions.assertNull(trainerById);


    }

    @Test
    public void deleteTrainer_withValidId_returnsValidEntity() {
        when(trainerDAO.findById(trainer.getId())).thenReturn(trainer);

        trainerService.saveTrainer(trainer);

        trainerService.deleteTrainer(trainer);

        Trainer trainerById = trainerService.findTrainerById(trainer.getId());

        verify(trainerDAO).delete(trainer);
        verify(trainerDAO).save(trainer);

        Assertions.assertEquals(trainer, trainerById);

    }

    @Test
    public void deleteTrainer_withInvalidId_returnsNull() {
        when(trainerDAO.findById(5L)).thenReturn(null);

        trainerService.saveTrainer(trainer);

        trainerService.deleteTrainer(trainer);

        Trainer trainerById = trainerService.findTrainerById(5L);

        verify(trainerDAO).delete(trainer);
        verify(trainerDAO).save(trainer);

        Assertions.assertNull(trainerById);

    }


    @Test
    public void findByFirstName_withExistingData_returnsValidEntity() {
        when(trainerDAO.findByFirstName(trainer.getUser().getFirstName())).thenReturn(trainer);

        trainerService.saveTrainer(trainer);

        Trainer firstName = trainerService.findByFirstName(trainer.getUser().getFirstName());


        verify(trainerDAO).findByFirstName(trainer.getUser().getFirstName());
        verify(trainerDAO).save(trainer);

        Assertions.assertEquals(firstName, trainer);
    }

    @Test
    public void findByLastName_withExistingData_returnsValidEntity() {
        when(trainerDAO.findByLastName(trainer.getUser().getLastName())).thenReturn(trainer);

        trainerService.saveTrainer(trainer);

        Trainer lastName = trainerService.findByLastName(trainer.getUser().getLastName());


        verify(trainerDAO).findByLastName(trainer.getUser().getLastName());
        verify(trainerDAO).save(trainer);

        Assertions.assertEquals(lastName, trainer);
    }

    @Test
    public void findByFirstName_withNonExistingData_returnsNull() {
        when(trainerDAO.findByFirstName("George")).thenReturn(null);

        trainerService.saveTrainer(trainer);

        Trainer trainerServiceByFirstName = trainerService.findByFirstName("George");

        verify(trainerDAO).findByFirstName("George");
        verify(trainerDAO).save(trainer);

        Assertions.assertNull(trainerServiceByFirstName);


    }

    @Test
    public void findByLastName_withNonExistingData_returnsNull() {
        when(trainerDAO.findByLastName("Bush")).thenReturn(null);

        trainerService.saveTrainer(trainer);

        Trainer trainerServiceByLastName = trainerService.findByLastName("Bush");

        verify(trainerDAO).findByLastName("Bush");
        verify(trainerDAO).save(trainer);

        Assertions.assertNull(trainerServiceByLastName);


    }

}



