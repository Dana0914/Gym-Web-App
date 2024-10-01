package epam.com.gymapplication;


import epam.com.gymapplication.dao.TrainerRepository;
import epam.com.gymapplication.entity.Trainer;
import epam.com.gymapplication.entity.TrainingType;
import epam.com.gymapplication.entity.User;
import epam.com.gymapplication.service.TrainerService;
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
public class TrainerServiceTest {

    @Mock
    private TrainerRepository trainerRepository;

    @InjectMocks
    private TrainerService trainerService;

    private Trainer trainer;
    private Trainer secondTrainer;

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

        secondTrainer = new Trainer();
        secondTrainer.setId(2L);
        secondTrainer.setUser(user);
        secondTrainer.setTrainingType(trainingType);


    }

    @Test
    public void save_withValidData_returnsValidEntity() {
        when(trainerRepository.findById(trainer.getId())).thenReturn(Optional.of(trainer));

        trainerService.saveTrainer(trainer);

        Trainer trainerById = trainerService.findTrainerById(trainer.getId());

        verify(trainerRepository).save(trainer);

        Assertions.assertEquals(trainerById, trainer);


    }


    @Test
    public void findTrainerById_withExistingId_returnsEntity() {
        when(trainerRepository.findById(trainer.getId())).thenReturn(Optional.of(trainer));

        Trainer trainerById = trainerService.findTrainerById(trainer.getId());

        verify(trainerRepository).findById(trainer.getId());

        Assertions.assertEquals(trainerById, trainer);

    }

    @Test
    public void findTrainerById_withNonExistingId_returnsEmpty() {
        when(trainerRepository.findById(4L)).thenReturn(Optional.empty());

        Optional<Trainer> trainerById = trainerRepository.findById(4L);

        verify(trainerRepository).findById(4L);


        Assertions.assertTrue(trainerById.isEmpty());


    }

    @Test
    public void deleteTrainer_withValidId_returnsValidEntity() {
        when(trainerRepository.findById(trainer.getId())).thenReturn(Optional.of(trainer));

        trainerService.deleteById(trainer.getId());

        verify(trainerRepository).deleteById(trainer.getId());


        when(trainerRepository.findById(trainer.getId())).thenReturn(Optional.empty());


        Optional<Trainer> deletedTrainer = trainerRepository.findById(trainer.getId());
        Assertions.assertTrue(deletedTrainer.isEmpty());

    }

    @Test
    public void deleteTrainer_withInvalidId_returnsEmptyOptional() {
        when(trainerRepository.findById(5L)).thenReturn(Optional.empty());

        trainerService.deleteById(5L);

        verify(trainerRepository).deleteById(5L);

        Optional<Trainer> trainerById = trainerRepository.findById(5L);

        Assertions.assertTrue(trainerById.isEmpty());

    }


    @Test
    public void findByFirstName_withExistingData_returnsValidEntity() {
        when(trainerRepository.findByFirstName(trainer.getUser().getFirstName())).thenReturn(Optional.ofNullable(trainer));

        Trainer trainerByFirstname = trainerService.findByFirstName(trainer.getUser().getFirstName());

        verify(trainerRepository).findByFirstName(trainer.getUser().getFirstName());

        Assertions.assertEquals(trainerByFirstname, trainer);
    }

    @Test
    public void findByLastName_withExistingData_returnsValidEntity() {
        when(trainerRepository.findByLastName(trainer.getUser().getLastName())).thenReturn(Optional.ofNullable(trainer));

        Trainer lastName = trainerService.findByLastName(trainer.getUser().getLastName());

        verify(trainerRepository).findByLastName(trainer.getUser().getLastName());

        Assertions.assertEquals(lastName, trainer);
    }

    @Test
    public void findByFirstName_withNonExistingData_returnsNull() {
        when(trainerRepository.findByFirstName("George")).thenReturn(Optional.empty());

        Optional<Trainer> trainerByFirstName = trainerRepository.findByFirstName("George");

        verify(trainerRepository).findByFirstName("George");


        Assertions.assertTrue(trainerByFirstName.isEmpty());



    }

    @Test
    public void findByLastName_withNonExistingData_returnsNull() {
        when(trainerRepository.findByLastName("Bush")).thenReturn(Optional.empty());


        Optional<Trainer> trainerServiceByLastName = trainerRepository.findByLastName("Bush");

        verify(trainerRepository).findByLastName("Bush");


        Assertions.assertTrue(trainerServiceByLastName.isEmpty());

    }

}



