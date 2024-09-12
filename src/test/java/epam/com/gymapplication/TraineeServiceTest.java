package epam.com.gymapplication;

import epam.com.gymapplication.dao.TraineeDAO;
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


import static org.mockito.Mockito.*;



@ExtendWith(MockitoExtension.class)
public class TraineeServiceTest {


    @Mock
    private TraineeDAO traineeDAO;

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
        trainee.setAddress("Edinburgh street 12");
        trainee.setDateOfBirth(LocalDate.of(2001, 4, 2));
        trainee.setUser(user);


        trainee2 = new Trainee();
        trainee2.setId(2L);
        trainee2.setAddress("Edinburgh street 4");
        trainee2.setDateOfBirth(LocalDate.of(2000, 8, 15));
        trainee2.setUser(user);

        invalidTrainee = new Trainee();
        invalidTrainee.setUser(user);
        invalidTrainee.setUser(null);
        invalidTrainee.setId(null);
        invalidTrainee.setAddress(null);
        invalidTrainee.setDateOfBirth(null);



    }

    @Test
    public void save_withValidData_returnsValidEntity() {
        when(traineeDAO.findById(trainee.getId())).thenReturn(trainee);


        traineeService.saveTrainee(trainee);

        Trainee traineeById = traineeService.findTraineeById(trainee.getId());


        verify(traineeDAO).save(trainee);
        Assertions.assertEquals(traineeById, trainee);

    }

    @Test
    public void save_withInvalidValidData_returnsNull() {
        when(traineeDAO.findById(trainee.getId())).thenReturn(null);

        traineeService.saveTrainee(trainee);

        Trainee traineeById = traineeService.findTraineeById(trainee.getId());


        verify(traineeDAO).save(trainee);

        Assertions.assertNull(traineeById);


    }

    @Test
    public void update_withExistingEntity_updatesEntityDetails() {
        when(traineeDAO.findById(trainee.getId())).thenReturn(trainee);

        traineeService.saveTrainee(trainee);

        Trainee traineeById = traineeService.findTraineeById(trainee.getId());

        trainee2.setId(trainee.getId());

        traineeService.updateTrainee(trainee2);

        when(traineeDAO.findById(trainee2.getId())).thenReturn(trainee2);

        Trainee updatedTraineeById = traineeService.findTraineeById(trainee2.getId());

        verify(traineeDAO).update(trainee2);
        verify(traineeDAO).save(trainee);

        Assertions.assertEquals(traineeById, trainee);
        Assertions.assertEquals(updatedTraineeById, trainee2);
    }

    @Test
    public void update_withInvalidData_returnsNull() {
        when(traineeDAO.findById(trainee.getId())).thenReturn(trainee);

        traineeService.saveTrainee(trainee);

        Trainee traineeById = traineeService.findTraineeById(trainee.getId());

        trainee2.setId(trainee.getId());

        traineeService.updateTrainee(trainee2);

        when(traineeDAO.findById(15L)).thenReturn(null);

        Trainee updatedTraineeById = traineeService.findTraineeById(15L);

        verify(traineeDAO).update(trainee2);
        verify(traineeDAO).save(trainee);

        Assertions.assertEquals(traineeById, trainee);
        Assertions.assertNull(updatedTraineeById);
    }


    @Test
    public void findTraineeById_withExistingId_returnsEntity() {
        when(traineeDAO.findById(trainee.getId())).thenReturn(trainee);

        traineeService.saveTrainee(trainee);

        Trainee traineeById = traineeService.findTraineeById(trainee.getId());


        verify(traineeDAO).findById(trainee.getId());
        verify(traineeDAO).save(trainee);

        Assertions.assertEquals(traineeById, trainee);

    }

    @Test
    public void findTraineeById_withNonExistingId_returnsNull() {
        when(traineeDAO.findById(trainee.getId())).thenReturn(null);

        traineeService.saveTrainee(trainee);

        Trainee traineeById = traineeService.findTraineeById(trainee.getId());

        verify(traineeDAO).findById(trainee.getId());
        verify(traineeDAO).save(trainee);

        Assertions.assertNull(traineeById);

    }

    @Test
    public void deleteTrainee_withValidId_returnsValidEntity() {
        when(traineeDAO.findById(trainee.getId())).thenReturn(trainee);

        traineeService.saveTrainee(trainee);

        traineeService.deleteTrainee(trainee);

        when(traineeDAO.findById(trainee.getId())).thenReturn(null);

        Trainee traineeById = traineeService.findTraineeById(trainee.getId());

        verify(traineeDAO).save(trainee);
        verify(traineeDAO).delete(trainee);

        Assertions.assertNull(traineeById);

    }

    @Test
    public void deleteTrainee_withInvalidId_returnsNull() {
        when(traineeDAO.findById(5L)).thenReturn(null);

        traineeService.saveTrainee(trainee);

        Trainee traineeById = traineeService.findTraineeById(5L);

        traineeService.deleteTrainee(trainee);

        verify(traineeDAO).delete(trainee);
        verify(traineeDAO).save(trainee);

        Assertions.assertNull(traineeById);

    }



    @Test
    public void findByFirstName_withExistingData_returnsValidEntity() {
        when(traineeDAO.findByFirstName(trainee.getUser().getFirstName())).thenReturn(trainee);

        traineeService.saveTrainee(trainee);

        Trainee firstName = traineeService.findTraineeByFirstName(trainee.getUser().getFirstName());


        verify(traineeDAO).findByFirstName(trainee.getUser().getFirstName());
        verify(traineeDAO).save(trainee);

        Assertions.assertEquals(firstName, trainee);

    }

    @Test
    public void findByFirstName_withNonExistingData_returnsNull() {
        when(traineeDAO.findByFirstName("George")).thenReturn(null);

        traineeService.saveTrainee(trainee);

        Trainee traineeByFirstName = traineeService.findTraineeByFirstName("George");

        verify(traineeDAO).findByFirstName("George");
        verify(traineeDAO).save(trainee);

        Assertions.assertNull(traineeByFirstName);

    }

    @Test
    public void findByLastName_withExistingData_returnsValidEntity() {
        when(traineeDAO.findByLastName(trainee.getUser().getLastName())).thenReturn(trainee);

        traineeService.saveTrainee(trainee);


        Trainee lastName = traineeService.findByLastName(trainee.getUser().getLastName());


        verify(traineeDAO).findByLastName(trainee.getUser().getLastName());
        verify(traineeDAO).save(trainee);

        Assertions.assertEquals(lastName, trainee);
    }

    @Test
    public void findByLastName_withNonExistingData_returnsNull() {
        when(traineeDAO.findByLastName("Bush")).thenReturn(null);

        traineeService.saveTrainee(trainee);

        Trainee traineeServiceByLastName = traineeService.findByLastName("Bush");

        verify(traineeDAO).findByLastName("Bush");
        verify(traineeDAO).save(trainee);

        Assertions.assertNull(traineeServiceByLastName);
    }




}







