package epam.com.gymapplication;


import epam.com.gymapplication.dao.impl.TrainerDAOImpl;
import epam.com.gymapplication.model.Trainee;
import epam.com.gymapplication.model.Trainer;
import epam.com.gymapplication.model.User;
import epam.com.gymapplication.service.TrainerService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;



@ExtendWith(MockitoExtension.class)
public class TrainerServiceTest {

    @Mock
    private TrainerDAOImpl trainerDAOImpl;

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

        trainer = new Trainer();
        trainer.setId(1L);
        trainer.setUserId(1L);
        trainer.setUser(user);
        trainer.setSpecialization(1L);

        trainer2 = new Trainer();
        trainer2.setId(2L);
        trainer2.setUserId(2L);
        trainer2.setUser(user);
        trainer2.setSpecialization(1L);


    }

    @Test
    public void save_withValidData_returnsValidEntity() {
        trainerService.saveTrainer(trainer);


        when(trainerDAOImpl.findById(trainer.getId())).thenReturn(trainer);

        Trainer trainerById = trainerService.findTrainerById(trainer.getId());

        verify(trainerDAOImpl).save(trainer);


        Assertions.assertEquals(trainerById, trainer);


    }

    @Test
    public void update_withExistingEntity_updatesEntityDetails() {
        trainerService.saveTrainer(trainer);


        when(trainerDAOImpl.findById(trainer.getId())).thenReturn(trainer);


        Trainer trainerById = trainerService.findTrainerById(trainer.getId());


        trainer2.setId(trainer.getId());

        trainerService.updateTrainer(trainer2);


        when(trainerDAOImpl.findById(trainer2.getId())).thenReturn(trainer2);

        Trainer updatedTrainerById = trainerService.findTrainerById(trainer2.getId());


        verify(trainerDAOImpl).update(trainer2);


        Assertions.assertEquals(updatedTrainerById, trainer2);


    }

    @Test
    public void findTrainerById_withExistingId_returnsEntity() {
        trainerService.saveTrainer(trainer);

        when(trainerDAOImpl.findById(trainer.getId())).thenReturn(trainer);

        Trainer trainerById = trainerService.findTrainerById(trainer.getId());


        verify(trainerDAOImpl).findById(trainer.getId());

        Assertions.assertEquals(trainerById, trainer);

    }

    @Test
    public void findTrainerById_withNonExistingId_returnsOptionalEmpty() {
        trainerService.saveTrainer(trainer);

        when(trainerDAOImpl.findById(4L)).thenReturn(null);


        Assertions.assertThrows(NoSuchElementException.class, () -> {
            trainerService.findTrainerById(4L);
        });

        verify(trainerDAOImpl).findById(4L);

    }

    @Test
    public void deleteTrainerById_withValidId_returnsValidEntity() {
        trainerService.saveTrainer(trainer);

        when(trainerDAOImpl.findById(trainer.getId())).thenReturn(trainer);

        trainerService.deleteTrainer(trainer);

        verify(trainerDAOImpl).delete(trainer);

        when(trainerDAOImpl.findById(trainer.getId())).thenReturn(null);

        Assertions.assertThrows(NoSuchElementException.class, () -> {
            trainerService.findTrainerById(trainer.getId());
        });

    }

    @Test
    public void deleteTrainerById_withInvalidId_returnsOptionalEmpty() {
        trainerService.saveTrainer(trainer);

        when(trainerDAOImpl.findById(5L)).thenReturn(null);

        trainerService.deleteTrainer(trainer);

        verify(trainerDAOImpl).delete(trainer);

        Assertions.assertThrows(NoSuchElementException.class, () -> {
            trainerService.findTrainerById(5L);
        });

    }


    @Test
    public void findByFirstName_withExistingData_returnsValidEntity() {
        trainerService.saveTrainer(trainer);

        when(trainerDAOImpl.findByFirstName(trainer.getUser().getFirstName())).thenReturn(Optional.of(trainer));

        Optional<Trainer> firstName = trainerService.findByFirstName(trainer.getUser().getFirstName());

        Assertions.assertTrue(firstName.isPresent());


        verify(trainerDAOImpl).findByFirstName(trainer.getUser().getFirstName());

        Assertions.assertEquals(firstName, Optional.of(trainer));
    }

    @Test
    public void findByLastName_withExistingData_returnsValidEntity() {
        trainerService.saveTrainer(trainer);

        when(trainerDAOImpl.findByLastName(trainer.getUser().getLastName())).thenReturn(Optional.of(trainer));

        Optional<Trainer> lastName = trainerService.findByLastName(trainer.getUser().getLastName());

        Assertions.assertTrue(lastName.isPresent());


        verify(trainerDAOImpl).findByLastName(trainer.getUser().getLastName());

        Assertions.assertEquals(lastName, Optional.of(trainer));
    }

    @Test
    public void findByFirstName_withNonExistingData_returnsOptionalEmpty() {
        trainerService.saveTrainer(trainer);

        when(trainerDAOImpl.findByFirstName("George")).thenReturn(Optional.empty());


        Assertions.assertThrows(NoSuchElementException.class, () -> {
            trainerService.findByFirstName("George").orElseThrow();
        });


    }

    @Test
    public void findByLastName_withNonExistingData_returnsOptionalEmpty() {
        trainerService.saveTrainer(trainer);


        when(trainerDAOImpl.findByLastName("Bush")).thenReturn(Optional.empty());


        Assertions.assertThrows(NoSuchElementException.class, () -> {
            trainerService.findByLastName("Bush").orElseThrow();
        });

    }
}



