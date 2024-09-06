package epam.com.gymapplication;

import epam.com.gymapplication.customexception.ServiceException;
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
        user.setId(1L);
        user.setUsername("John.Doe");
        user.setPassword("8&hs-@.");
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setActive(true);

        trainee = new Trainee();
        trainee.setId(1L);
        trainee.setUserId(1L);
        trainee.setAddress("Edinburgh street 12");
        trainee.setDateOfBirth(LocalDate.of(2001, 4, 2));
        trainee.setUser(user);

        trainee2 = new Trainee();
        trainee2.setId(2L);
        trainee2.setUserId(2L);
        trainee2.setAddress("Edinburgh street 4");
        trainee2.setDateOfBirth(LocalDate.of(2000, 8, 15));
        trainee2.setUser(user);

        invalidTrainee = new Trainee();
        invalidTrainee.setId(3L);
        invalidTrainee.setUserId(3L);
        invalidTrainee.setAddress(null);
        invalidTrainee.setDateOfBirth(null);
        invalidTrainee.setUser(null);


    }

    @Test
    public void save_withValidData_returnsValidEntity() {
        traineeService.saveTrainee(trainee);

        verify(traineeDAOImpl).save(trainee);

        when(traineeDAOImpl.findById(trainee.getId())).thenReturn(Optional.of(trainee));

        Optional<Trainee> traineeById = traineeService.findTraineeById(trainee.getId());


        Assertions.assertEquals(traineeById, Optional.of(trainee));

    }

    @Test
    public void update_withExistingEntity_updatesEntityDetails() {
        traineeService.saveTrainee(trainee);

        when(traineeDAOImpl.findById(trainee.getId())).thenReturn(Optional.of(trainee));


        Optional<Trainee> traineeById = traineeService.findTraineeById(trainee.getId());

        Assertions.assertTrue(traineeById.isPresent());

        trainee2.setId(trainee.getId());

        traineeService.updateTrainee(trainee2);


        when(traineeDAOImpl.findById(trainee2.getId())).thenReturn(Optional.of(trainee2));

        Optional<Trainee> updatedTraineeById = traineeService.findTraineeById(trainee2.getId());

        Assertions.assertTrue(updatedTraineeById.isPresent());


        verify(traineeDAOImpl).update(trainee2);



        Assertions.assertEquals(updatedTraineeById, Optional.of(trainee2));


    }


    @Test
    public void findTraineeById_withExistingId_returnsEntity() {
        traineeService.saveTrainee(trainee);

        when(traineeDAOImpl.findById(trainee.getId())).thenReturn(Optional.of(trainee));

        Optional<Trainee> traineeById = traineeService.findTraineeById(trainee.getId());

        Assertions.assertTrue(traineeById.isPresent());

        verify(traineeDAOImpl).findById(trainee.getId());

        Assertions.assertEquals(traineeById, Optional.of(trainee));

    }

    @Test
    public void findTraineeById_withNonExistingId_returnsOptionalEmpty() {
        traineeService.saveTrainee(trainee);


        when(traineeDAOImpl.findById(4L)).thenReturn(Optional.empty());


        Assertions.assertThrows(NoSuchElementException.class, () -> {
            traineeService.findTraineeById(4L).orElseThrow();
        });


        verify(traineeDAOImpl).findById(4L);


    }

    @Test
    public void deleteTraineeById_withValidId_returnsValidEntity() {
        traineeService.saveTrainee(trainee);

        when(traineeDAOImpl.findById(trainee.getId())).thenReturn(Optional.of(trainee));

        traineeService.deleteTraineeById(trainee.getId());

        verify(traineeDAOImpl).deleteById(trainee.getId());

        when(traineeDAOImpl.findById(trainee.getId())).thenReturn(Optional.empty());


        Assertions.assertThrows(NoSuchElementException.class, () -> {
            traineeService.findTraineeById(trainee.getId()).orElseThrow();
        });

    }

    @Test
    public void deleteTraineeById_withInvalidId_returnsOptionalEmpty() {
        traineeService.saveTrainee(trainee);

        when(traineeDAOImpl.findById(5L)).thenReturn(Optional.empty());

        traineeService.deleteTraineeById(5L);

        verify(traineeDAOImpl).deleteById(5L);

        Assertions.assertThrows(NoSuchElementException.class, () -> {
            traineeService.findTraineeById(5L).orElseThrow();
        });

    }


    @Test
    public void findByFirstName_withExistingData_returnsValidEntity() {
        traineeService.saveTrainee(trainee);

        when(traineeDAOImpl.findByFirstName(trainee.getUser().getFirstName())).thenReturn(Optional.of(trainee));

        Optional<Trainee> firstName = traineeService.findTraineeByFirstName(trainee.getUser().getFirstName());

        Assertions.assertTrue(firstName.isPresent());


        verify(traineeDAOImpl).findByFirstName(trainee.getUser().getFirstName());

        Assertions.assertEquals(firstName, Optional.of(trainee));

    }

    @Test
    public void findByFirstName_withNonExistingData_returnsOptionalEmpty() {
        traineeService.saveTrainee(trainee);


        when(traineeDAOImpl.findByFirstName("George")).thenReturn(Optional.empty());


        Assertions.assertThrows(NoSuchElementException.class, () -> {
            traineeService.findTraineeByFirstName("George").orElseThrow();
        });

    }

    @Test
    public void findByLastName_withExistingData_returnsValidEntity() {
        traineeService.saveTrainee(trainee);

        when(traineeDAOImpl.findByLastName(trainee.getUser().getLastName())).thenReturn(Optional.of(trainee));

        Optional<Trainee> lastName = traineeService.findByLastName(trainee.getUser().getLastName());

        Assertions.assertTrue(lastName.isPresent());


        verify(traineeDAOImpl).findByLastName(trainee.getUser().getLastName());

        Assertions.assertEquals(lastName, Optional.of(trainee));
    }

    @Test
    public void findByLastName_withNonExistingData_returnsOptionalEmpty() {
        traineeService.saveTrainee(trainee);

        when(traineeDAOImpl.findByLastName("Bush")).thenReturn(Optional.empty());

        Assertions.assertThrows(NoSuchElementException.class, () -> {
            traineeService.findByLastName("Bush").orElseThrow();
        });

    }
}







