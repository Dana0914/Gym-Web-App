package epam.com.gymapplication;

import epam.com.gymapplication.dao.impl.TraineeDAOImpl;
import epam.com.gymapplication.model.Trainee;
import epam.com.gymapplication.model.User;
import epam.com.gymapplication.service.TraineeService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.*;


import static org.mockito.Mockito.*;



@ExtendWith(MockitoExtension.class)
public class TraineeServiceTest {


    @Mock
    private TraineeDAOImpl traineeDAOImpl;

    @InjectMocks
    private TraineeService traineeService;

    private Trainee trainee;
    private Trainee trainee2;
    private Trainee invalidTrainee;

    @BeforeEach
    public void setUp() {

        User user = new User();
        user.setUsername("John.Doe");
        user.setPassword("8&hs-@.");
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setActive(true);

        trainee = new Trainee();
        trainee.setUserId(user.getId());
        trainee.setAddress("Edinburgh street 12");
        trainee.setDateOfBirth(LocalDate.of(2001, 4, 2));
        trainee.setUser(user);

        trainee2 = new Trainee();
        trainee2.setUserId(user.getId());
        trainee2.setAddress("Edinburgh street 4");
        trainee2.setDateOfBirth(LocalDate.of(2000, 8, 15));
        trainee2.setUser(user);

        invalidTrainee = new Trainee();
        invalidTrainee.setUserId(user.getId());
        invalidTrainee.setAddress(null);
        invalidTrainee.setDateOfBirth(null);
        invalidTrainee.setUser(null);


    }

    @Test
    public void save_withValidData_returnsValidEntity() {
        traineeService.saveTrainee(trainee);

        verify(traineeDAOImpl).save(trainee);

        when(traineeDAOImpl.findById(trainee.getId())).thenReturn(trainee);

        Trainee traineeById = traineeService.findTraineeById(trainee.getId());


        Assertions.assertEquals(traineeById, trainee);

    }

    @Test
    public void update_withExistingEntity_updatesEntityDetails() {
        traineeService.saveTrainee(trainee);

        when(traineeDAOImpl.findById(trainee.getId())).thenReturn(trainee);


        Trainee traineeById = traineeService.findTraineeById(trainee.getId());


        trainee2.setId(trainee.getId());

        traineeService.updateTrainee(trainee2);


        when(traineeDAOImpl.findById(trainee2.getId())).thenReturn(trainee2);

        Trainee updatedTraineeById = traineeService.findTraineeById(trainee2.getId());


        verify(traineeDAOImpl).update(trainee2);



        Assertions.assertEquals(updatedTraineeById, trainee2);


    }


    @Test
    public void findTraineeById_withExistingId_returnsEntity() {
        traineeService.saveTrainee(trainee);

        when(traineeDAOImpl.findById(trainee.getId())).thenReturn(trainee);

        Trainee traineeById = traineeService.findTraineeById(trainee.getId());



        verify(traineeDAOImpl).findById(trainee.getId());

        Assertions.assertEquals(traineeById, trainee);

    }

    @Test
    public void findTraineeById_withNonExistingId_returnsOptionalEmpty() {
        traineeService.saveTrainee(trainee);


        when(traineeDAOImpl.findById(4L)).thenReturn(null);


        Assertions.assertThrows(NoSuchElementException.class, () -> {
            traineeService.findTraineeById(4L);
        });

        verify(traineeDAOImpl).findById(4L);


    }

    @Test
    public void deleteTrainee_withValidId_returnsValidEntity() {
        traineeService.saveTrainee(trainee);

        when(traineeDAOImpl.findById(trainee.getId())).thenReturn(trainee);

        traineeService.deleteTrainee(trainee);

        verify(traineeDAOImpl).delete(trainee);

        when(traineeDAOImpl.findById(trainee.getId())).thenReturn(null);


        Assertions.assertThrows(NoSuchElementException.class, () -> {
            traineeService.findTraineeById(trainee.getId());
        });

    }



    @Test
    public void findByFirstName_withExistingData_returnsValidEntity() {
        traineeService.saveTrainee(trainee);

        when(traineeDAOImpl.findByFirstName(trainee.getUser().getFirstName())).thenReturn(null);

        Trainee firstName = traineeService.findTraineeByFirstName(trainee.getUser().getFirstName());


        verify(traineeDAOImpl).findByFirstName(trainee.getUser().getFirstName());

        Assertions.assertEquals(firstName, trainee);

    }

    @Test
    public void findByFirstName_withNonExistingData_returnsOptionalEmpty() {
        traineeService.saveTrainee(trainee);


        when(traineeDAOImpl.findByFirstName("George")).thenReturn(null);


        Assertions.assertThrows(NoSuchElementException.class, () -> {
            traineeService.findTraineeByFirstName("George");
        });

    }

    @Test
    public void findByLastName_withExistingData_returnsValidEntity() {
        traineeService.saveTrainee(trainee);

        when(traineeDAOImpl.findByLastName(trainee.getUser().getLastName())).thenReturn(null);

        Trainee lastName = traineeService.findByLastName(trainee.getUser().getLastName());



        verify(traineeDAOImpl).findByLastName(trainee.getUser().getLastName());

        Assertions.assertEquals(lastName, trainee);
    }

    @Test
    public void findByLastName_withNonExistingData_returnsOptionalEmpty() {
        traineeService.saveTrainee(trainee);

        when(traineeDAOImpl.findByLastName("Bush")).thenReturn(null);

        Assertions.assertThrows(NoSuchElementException.class, () -> {
            traineeService.findByLastName("Bush");
        });

    }
}







