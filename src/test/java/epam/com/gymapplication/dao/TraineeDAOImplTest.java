package epam.com.gymapplication.dao;


import epam.com.gymapplication.config.AppConfig;
import epam.com.gymapplication.dao.impl.TraineeDAOImpl;
import epam.com.gymapplication.dao.impl.TrainerDAOImpl;
import epam.com.gymapplication.model.Trainee;
import epam.com.gymapplication.model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.util.Map;
import java.util.Optional;
import java.util.Set;


@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {AppConfig.class, TrainerDAOImpl.class})
public class TraineeDAOImplTest {


    @Autowired
    private TraineeDAOImpl traineeDAO;

    private Trainee trainee;


    @BeforeEach
    public void setUp() {
        User user = new User();
        user.setId(4L);
        user.setUsername("Rose.Tul");
        user.setPassword("k57)-3fh");
        user.setFirstName("Rose");
        user.setLastName("Tul");
        user.setActive(true);

        trainee = new Trainee();
        trainee.setId(4L);
        trainee.setUserId(4L);
        trainee.setDateOfBirth(LocalDate.of(2000, 1, 1));
        trainee.setAddress("Minnesota street 25");
        trainee.setUser(user);

        // Add a trainee with ID 5L for the update
        Trainee updatedTrainee = new Trainee();
        updatedTrainee.setId(5L);
        updatedTrainee.setUserId(5L);
        updatedTrainee.setDateOfBirth(LocalDate.of(2000, 1, 1));
        updatedTrainee.setAddress("Minnesota street 25");
        updatedTrainee.setUser(user);
        traineeDAO.save(updatedTrainee);


    }

    @Test
    public void save_withValidData_returnsValidEntity() {
        traineeDAO.save(trainee);
        Assertions.assertNotNull(trainee);



        Trainee retrievedTrainee = traineeDAO.findById(trainee.getId()).orElse(null);
        Assertions.assertNotNull(retrievedTrainee);

        Assertions.assertEquals(trainee.getId(), retrievedTrainee.getId());
        Assertions.assertEquals(trainee.getUserId(), retrievedTrainee.getUserId());
        Assertions.assertEquals(trainee.getDateOfBirth(), retrievedTrainee.getDateOfBirth());
        Assertions.assertEquals(trainee.getAddress(), retrievedTrainee.getAddress());
        Assertions.assertEquals(trainee.getUser().getFirstName(), retrievedTrainee.getUser().getFirstName());
        Assertions.assertEquals(trainee.getUser().getLastName(), retrievedTrainee.getUser().getLastName());
        Assertions.assertEquals(trainee.getUser().getUsername(), retrievedTrainee.getUser().getUsername());
        Assertions.assertEquals(trainee.getUser().getFirstName(), retrievedTrainee.getUser().getFirstName());



    }

    @Test
    public void update_withExistingEntity_updatesEntityDetails() {
        Trainee existingTrainee = traineeDAO.findById(5L).orElse(null);
        Assertions.assertNotNull(existingTrainee);

        traineeDAO.update(existingTrainee);
        Assertions.assertNotNull(existingTrainee);

        Trainee retrievedTrainee = traineeDAO.findById(5L).orElse(null);
        Assertions.assertNotNull(retrievedTrainee);


        Assertions.assertEquals(existingTrainee.getId(), retrievedTrainee.getId());
        Assertions.assertEquals(existingTrainee.getUserId(), retrievedTrainee.getUserId());
        Assertions.assertEquals(existingTrainee.getDateOfBirth(), retrievedTrainee.getDateOfBirth());
        Assertions.assertEquals(existingTrainee.getAddress(), retrievedTrainee.getAddress());
        Assertions.assertEquals(existingTrainee.getUser().getFirstName(), retrievedTrainee.getUser().getFirstName());
        Assertions.assertEquals(existingTrainee.getUser().getLastName(), retrievedTrainee.getUser().getLastName());
        Assertions.assertEquals(existingTrainee.getUser().getUsername(), retrievedTrainee.getUser().getUsername());
        Assertions.assertEquals(existingTrainee.getUser().getPassword(), retrievedTrainee.getUser().getPassword());

    }

    @Test
    public void deleteById_withValidId_returnsValidEntity() {
        Trainee retrievedTrainee = traineeDAO.findById(5L).get();
        Assertions.assertNotNull(retrievedTrainee);

        traineeDAO.deleteById(retrievedTrainee.getId());
        Assertions.assertEquals(traineeDAO.findById(5L), Optional.empty());


    }

    @Test
    public void findById_withExistingId_returnsEntity() {
        Trainee retrievedTrainee = traineeDAO.findById(trainee.getId()).orElse(null);
        Assertions.assertNotNull(retrievedTrainee);

        Assertions.assertEquals(retrievedTrainee.getId(), trainee.getId());
        Assertions.assertEquals(retrievedTrainee.getUserId(), trainee.getUserId());
        Assertions.assertEquals(retrievedTrainee.getDateOfBirth(), trainee.getDateOfBirth());
        Assertions.assertEquals(retrievedTrainee.getAddress(), trainee.getAddress());
        Assertions.assertEquals(retrievedTrainee.getUser().getFirstName(), trainee.getUser().getFirstName());
        Assertions.assertEquals(retrievedTrainee.getUser().getLastName(), trainee.getUser().getLastName());
        Assertions.assertEquals(retrievedTrainee.getUser().getUsername(), trainee.getUser().getUsername());
        Assertions.assertEquals(retrievedTrainee.getUser().getPassword(), trainee.getUser().getPassword());



    }

    @Test
    public void findAll_withExistingData_returnsValidEntities() {
        Set<Map.Entry<Long, Trainee>> all = traineeDAO.findAll();

        Assertions.assertNotNull(all);
        Assertions.assertFalse(all.isEmpty());

    }

    @Test
    public void findByFirstName_withExistingData_returnsValidEntity() {
        Optional<Trainee> firstName = traineeDAO.findByFirstName("Rose");

        Assertions.assertNotNull(firstName);
        Assertions.assertTrue(firstName.isPresent());

    }

    @Test
    public void findByLastName_withExistingData_returnsValidEntity() {
        Optional<Trainee> lastName = traineeDAO.findByLastName("Tul");

        Assertions.assertNotNull(lastName);
        Assertions.assertTrue(lastName.isPresent());
    }
}
