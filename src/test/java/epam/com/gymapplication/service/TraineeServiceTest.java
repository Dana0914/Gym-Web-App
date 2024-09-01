package epam.com.gymapplication.service;


import epam.com.gymapplication.config.AppConfig;
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
@ContextConfiguration(classes = {AppConfig.class, TraineeServiceTest.class})
public class TraineeServiceTest {

    @Autowired
    private TraineeService traineeService;

    private Trainee trainee;


    @BeforeEach
    public void setUp() {
        User user = new User();
        user.setUsername("Rose.Tul");
        user.setPassword("k57)-3fh");
        user.setFirstName("Rose");
        user.setLastName("Tul");

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
        traineeService.saveTrainee(updatedTrainee);
    }

    @Test
    public void save_withValidData_returnsValidEntity() {
        traineeService.saveTrainee(trainee);
        Assertions.assertNotNull(trainee);



        Trainee retrievedTrainee = traineeService.findTraineeById(trainee.getId()).orElse(null);
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
        Trainee existingTrainee = traineeService.findTraineeById(5L).orElse(null);
        Assertions.assertNotNull(existingTrainee);

        traineeService.updateTrainee(existingTrainee);

        Trainee retrievedTrainee = traineeService.findTraineeById(5L).orElse(null);
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
        Trainee retrievedTrainee = traineeService.findTraineeById(5L).orElse(null);
        Assertions.assertNotNull(retrievedTrainee);

        traineeService.deleteTraineeById(retrievedTrainee.getId());
        Assertions.assertEquals(traineeService.findTraineeById(5L), Optional.empty());



    }

    @Test
    public void findById_withExistingId_returnsEntity() {
        Trainee retrievedTrainee = traineeService.findTraineeById(trainee.getId()).orElse(null);
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
        Set<Map.Entry<Long, Trainee>> all = traineeService.getAllTrainees();

        Assertions.assertNotNull(all);
        Assertions.assertFalse(all.isEmpty());

    }

    @Test
    public void findByFirstName_withExistingData_returnsValidEntity() {
        Optional<Trainee> firstName = traineeService.findTraineeByFirstName(trainee.getUser().getFirstName());

        Assertions.assertNotNull(firstName);
        Assertions.assertTrue(firstName.isPresent());

    }

    @Test
    public void findByLastName_withExistingData_returnsValidEntity() {
        Optional<Trainee> lastName = traineeService.findByLastName(trainee.getUser().getLastName());

        Assertions.assertNotNull(lastName);
        Assertions.assertTrue(lastName.isPresent());
    }


}
