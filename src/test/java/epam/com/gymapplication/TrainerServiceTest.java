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
        trainer.setSpecialization("aerobics");

        trainer2 = new Trainer();
        trainer2.setId(2L);
        trainer2.setUserId(2L);
        trainer2.setUser(user);
        trainer2.setSpecialization("aerobics");


    }

    @Test
    public void save_withValidData_returnsValidEntity() {
        trainerService.saveTrainer(trainer);

        verify(trainerDAOImpl).save(trainer);

        when(trainerDAOImpl.findById(trainer.getId())).thenReturn(Optional.of(trainer));

        Optional<Trainer> trainerById = trainerService.findTrainerById(trainer.getId());


        Assertions.assertEquals(trainerById.get().getUser(), trainer.getUser());
        Assertions.assertEquals(trainerById.get().getId(), trainer.getId());
        Assertions.assertEquals(trainerById.get().getUser().getId(), trainer.getUser().getId());
        Assertions.assertEquals(trainerById.get().getSpecialization(), trainer.getSpecialization());


    }

    @Test
    public void update_withExistingEntity_updatesEntityDetails() {
        trainerService.saveTrainer(trainer);


        when(trainerDAOImpl.findById(trainer.getId())).thenReturn(Optional.of(trainer));


        Optional<Trainer> trainerById = trainerService.findTrainerById(trainer.getId());
        Assertions.assertTrue(trainerById.isPresent());


        trainer2.setId(trainer.getId());

        trainerService.updateTrainer(trainer2);


        when(trainerDAOImpl.findById(trainer2.getId())).thenReturn(Optional.of(trainer2));

        Optional<Trainer> updatedTrainerById = trainerService.findTrainerById(trainer2.getId());

        Assertions.assertTrue(updatedTrainerById.isPresent());


        verify(trainerDAOImpl).save(trainer);
        verify(trainerDAOImpl).update(trainer2);


        Assertions.assertEquals(updatedTrainerById.get().getUserId(), trainer2.getUserId());
        Assertions.assertEquals(updatedTrainerById.get().getUser(), trainer2.getUser());
        Assertions.assertEquals(updatedTrainerById.get().getId(), trainer2.getId());
        Assertions.assertEquals(updatedTrainerById.get().getSpecialization(), trainer2.getSpecialization());



    }

    @Test
    public void findTrainerById_withExistingId_returnsEntity() {
        trainerService.saveTrainer(trainer);

        when(trainerDAOImpl.findById(trainer.getId())).thenReturn(Optional.of(trainer));

        Optional<Trainer> trainerById = trainerService.findTrainerById(trainer.getId());

        Assertions.assertTrue(trainerById.isPresent());

        verify(trainerDAOImpl).findById(trainer.getId());
        verify(trainerDAOImpl).save(trainer);

        Assertions.assertEquals(trainerById.get().getId(), trainer.getId());
        Assertions.assertEquals(trainerById.get().getUser(), trainer.getUser());
        Assertions.assertEquals(trainerById.get().getSpecialization(), trainer.getSpecialization());
    }

    @Test
    public void findTrainerById_withNonExistingId_returnsOptionalEmpty() {
        trainerService.saveTrainer(trainer);

        when(trainerDAOImpl.findById(4L)).thenReturn(Optional.empty());

        Optional<Trainer> trainerById = trainerService.findTrainerById(4L);

        Assertions.assertFalse(trainerById.isPresent());

        verify(trainerDAOImpl).findById(4L);

        Assertions.assertEquals(trainerById, Optional.empty());
    }

    @Test
    public void deleteTrainerById_withValidId_returnsValidEntity() {
        trainerService.saveTrainer(trainer);

        when(trainerDAOImpl.findById(trainer.getId())).thenReturn(Optional.of(trainer));

        trainerService.deleteTrainerById(trainer.getId());

        when(trainerDAOImpl.findById(trainer.getId())).thenReturn(Optional.empty());

        verify(trainerDAOImpl).deleteById(trainer.getId());

        Assertions.assertEquals(trainerService.findTrainerById(trainer.getId()), Optional.empty());

    }

    @Test
    public void deleteTrainerById_withInvalidId_returnsOptionalEmpty() {
        trainerService.saveTrainer(trainer);

        when(trainerDAOImpl.findById(5L)).thenReturn(Optional.empty());

        trainerService.deleteTrainerById(5L);


        verify(trainerDAOImpl).deleteById(5L);

        Assertions.assertEquals(trainerService.findTrainerById(5L), Optional.empty());

    }

    @Test
    public void findAllTrainers_withExistingData_returnsValidEntities() {
        Set<Map.Entry<Long, Trainer>> mockTrainerSet = new HashSet<>();
        mockTrainerSet.add(new AbstractMap.SimpleEntry<>(trainer.getId(), trainer));
        mockTrainerSet.add(new AbstractMap.SimpleEntry<>(trainer2.getId(), trainer2));

        when(trainerDAOImpl.findAll()).thenReturn(mockTrainerSet);


        Set<Map.Entry<Long, Trainer>> result = trainerDAOImpl.findAll();


        verify(trainerDAOImpl).findAll();

        Assertions.assertTrue(result.contains(new AbstractMap.SimpleEntry<>(trainer.getId(), trainer)));
        Assertions.assertTrue(result.contains(new AbstractMap.SimpleEntry<>(trainer2.getId(), trainer2)));


    }

    @Test
    public void findByFirstName_withExistingData_returnsValidEntity() {
        trainerService.saveTrainer(trainer);

        when(trainerDAOImpl.findByFirstName(trainer.getUser().getFirstName())).thenReturn(Optional.of(trainer));

        Optional<Trainer> firstName = trainerService.findByFirstName(trainer.getUser().getFirstName());

        Assertions.assertTrue(firstName.isPresent());

        verify(trainerDAOImpl).save(trainer);
        verify(trainerDAOImpl).findByFirstName(trainer.getUser().getFirstName());

        Assertions.assertEquals(trainerService.findByFirstName(trainer.getUser().getFirstName()), Optional.of(trainer));
    }

    @Test
    public void findByLastName_withExistingData_returnsValidEntity() {
        trainerService.saveTrainer(trainer);

        when(trainerDAOImpl.findByLastName(trainer.getUser().getLastName())).thenReturn(Optional.of(trainer));

        Optional<Trainer> lastName = trainerService.findByLastName(trainer.getUser().getLastName());

        Assertions.assertTrue(lastName.isPresent());

        verify(trainerDAOImpl).save(trainer);
        verify(trainerDAOImpl).findByLastName(trainer.getUser().getLastName());

        Assertions.assertEquals(trainerService.findByLastName(trainer.getUser().getLastName()), Optional.of(trainer));
    }

    @Test
    public void findByFirstName_withNonExistingData_returnsOptionalEmpty() {
        trainerService.saveTrainer(trainer);


        when(trainerDAOImpl.findByFirstName("George")).thenReturn(Optional.empty());


        Optional<Trainer> result = trainerService.findByFirstName("George");


        Assertions.assertFalse(result.isPresent());

        verify(trainerDAOImpl).findByFirstName("George");

        Assertions.assertEquals(trainerService.findByFirstName("George"), Optional.empty());

    }

    @Test
    public void findByLastName_withNonExistingData_returnsOptionalEmpty() {
        trainerService.saveTrainer(trainer);


        when(trainerDAOImpl.findByLastName("Bush")).thenReturn(Optional.empty());


        Optional<Trainer> result = trainerService.findByLastName("Bush");


        Assertions.assertFalse(result.isPresent());


        verify(trainerDAOImpl).findByLastName("Bush");

        Assertions.assertEquals(trainerService.findByLastName("Bush"), Optional.empty());

    }


}
