package epam.com.gymapplication.dao;

import epam.com.gymapplication.config.AppConfig;
import epam.com.gymapplication.dao.impl.TraineeDAOImpl;
import epam.com.gymapplication.dao.impl.TrainerDAOImpl;
import epam.com.gymapplication.model.Trainer;
import epam.com.gymapplication.model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;


import java.util.Map;
import java.util.Optional;
import java.util.Set;


@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {AppConfig.class, TrainerDAOImpl.class})
public class TrainerDAOImplTest {
    @Autowired
    private TrainerDAOImpl trainerDAO;

    private Trainer trainer;


    @BeforeEach
    public void setUp() {
        User user = new User();
        user.setUsername("Rose.Tul");
        user.setPassword("k57)-3fh");
        user.setFirstName("Rose");
        user.setLastName("Tul");

        trainer = new Trainer();
        trainer.setId(4L);
        trainer.setUserId(4L);
        trainer.setSpecialization("muscles");
        trainer.setUser(user);

        // Add a trainee with ID 5L for the update test
        Trainer updatedTrainer = new Trainer();
        updatedTrainer.setId(5L);
        updatedTrainer.setUserId(5L);
        updatedTrainer.setSpecialization("muscles");
        updatedTrainer.setUser(user);
        trainerDAO.save(updatedTrainer);

    }

    @Test
    public void save_withValidData_returnsValidEntity() {
        trainerDAO.save(trainer);
        Assertions.assertNotNull(trainer);


        Trainer retrievedTrainer = trainerDAO.findById(trainer.getId()).orElse(null);
        Assertions.assertNotNull(retrievedTrainer);


        Assertions.assertEquals(trainer.getId(), retrievedTrainer.getId());
        Assertions.assertEquals(trainer.getUserId(), retrievedTrainer.getUserId());
        Assertions.assertEquals(trainer.getSpecialization(), retrievedTrainer.getSpecialization());
        Assertions.assertEquals(trainer.getUser().getFirstName(), retrievedTrainer.getUser().getFirstName());
        Assertions.assertEquals(trainer.getUser().getLastName(), retrievedTrainer.getUser().getLastName());
        Assertions.assertEquals(trainer.getUser().getUsername(), retrievedTrainer.getUser().getUsername());
        Assertions.assertEquals(trainer.getUser().getFirstName(), retrievedTrainer.getUser().getFirstName());
    }

    @Test
    public void update_withExistingEntity_updatesEntityDetails() {
        Trainer existingTrainer = trainerDAO.findById(5L).orElse(null);
        Assertions.assertNotNull(existingTrainer);

        trainerDAO.update(existingTrainer);
        Assertions.assertNotNull(existingTrainer);

        Trainer retrievedTrainer = trainerDAO.findById(5L).orElse(null);
        Assertions.assertNotNull(retrievedTrainer);


        Assertions.assertEquals(existingTrainer.getId(), retrievedTrainer.getId());
        Assertions.assertEquals(existingTrainer.getUserId(), retrievedTrainer.getUserId());
        Assertions.assertEquals(existingTrainer.getSpecialization(), retrievedTrainer.getSpecialization());
        Assertions.assertEquals(existingTrainer.getUser().getFirstName(), retrievedTrainer.getUser().getFirstName());
        Assertions.assertEquals(existingTrainer.getUser().getLastName(), retrievedTrainer.getUser().getLastName());
        Assertions.assertEquals(existingTrainer.getUser().getUsername(), retrievedTrainer.getUser().getUsername());
        Assertions.assertEquals(existingTrainer.getUser().getPassword(), retrievedTrainer.getUser().getPassword());

    }

    @Test
    public void deleteById_withValidId_returnsValidEntity() {
        Trainer retrievedTrainer = trainerDAO.findById(5L).get();
        Assertions.assertNotNull(retrievedTrainer);

        trainerDAO.deleteById(retrievedTrainer.getId());
        Assertions.assertEquals(trainerDAO.findById(5L), Optional.empty());


    }

    @Test
    public void findById_withExistingId_returnsEntity() {
        Trainer retrievedTrainer = trainerDAO.findById(4L).orElse(null);
        Assertions.assertNotNull(retrievedTrainer);



        Assertions.assertEquals(retrievedTrainer.getId(), trainer.getId());
        Assertions.assertEquals(retrievedTrainer.getUser(), trainer.getUser());
        Assertions.assertEquals(retrievedTrainer.getUserId(), trainer.getUserId());
        Assertions.assertEquals(retrievedTrainer.getSpecialization(), trainer.getSpecialization());
    }

    @Test
    public void findAll_withExistingData_returnsValidEntities() {
        Set<Map.Entry<Long, Trainer>> all = trainerDAO.findAll();

        Assertions.assertNotNull(all);
        Assertions.assertFalse(all.isEmpty());

    }

    @Test
    public void findByFirstName_withExistingData_returnsValidEntity() {
        Optional<Trainer> firstName = trainerDAO.findByFirstName("Rose");

        Assertions.assertNotNull(firstName);
        Assertions.assertTrue(firstName.isPresent());

    }

    @Test
    public void findByLastName_withExistingData_returnsValidEntity() {
        Optional<Trainer> lastName = trainerDAO.findByLastName("Tul");

        Assertions.assertNotNull(lastName);
        Assertions.assertTrue(lastName.isPresent());
    }
}
