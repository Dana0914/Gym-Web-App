package epam.com.gymapplication;

import epam.com.gymapplication.dao.TraineeRepository;
import epam.com.gymapplication.entity.Trainee;
import epam.com.gymapplication.entity.User;
import epam.com.gymapplication.service.TraineeService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;


import static org.mockito.Mockito.*;



@ExtendWith(MockitoExtension.class)
public class TraineeServiceTest {


    @Mock
    private TraineeRepository traineeRepository;

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
        when(traineeRepository.findById(trainee.getId())).thenReturn(Optional.of(trainee));


        traineeService.saveTrainee(trainee);

        Trainee traineeById = traineeService.findTraineeById(trainee.getId());


        verify(traineeRepository).save(trainee);
        Assertions.assertEquals(traineeById, trainee);

    }

    @Test
    public void save_withInvalidValidData_returnsNull() {
        when(traineeRepository.findById(trainee.getId())).thenReturn(Optional.empty());

        traineeService.saveTrainee(trainee);

        Trainee traineeById = traineeService.findTraineeById(trainee.getId());


        verify(traineeRepository).save(trainee);

        Assertions.assertNull(traineeById);


    }

//    @Test
//    public void update_withExistingEntity_updatesEntityDetails() {
//        when(traineeRepository.findById(trainee.getId())).thenReturn(Optional.ofNullable(trainee));
//
//        traineeService.saveTrainee(trainee);
//
//        Trainee traineeById = traineeService.findTraineeById(trainee.getId());
//
//        trainee2.setId(trainee.getId());
//
//        traineeService.updateTrainee(trainee2);
//
//        when(traineeRepository.findById(trainee2.getId())).thenReturn(Optional.ofNullable(trainee2));
//
//        Trainee updatedTraineeById = traineeService.findTraineeById(trainee2.getId());
//
//        verify(traineeRepository).updateTrainee(trainee2);
//        verify(traineeRepository).save(trainee);
//
//        Assertions.assertEquals(traineeById, trainee);
//        Assertions.assertEquals(updatedTraineeById, trainee2);
//    }

//    @Test
//    public void update_withInvalidData_returnsNull() {
//        when(traineeRepository.findById(trainee.getId())).thenReturn(Optional.ofNullable(trainee));
//
//        traineeService.saveTrainee(trainee);
//
//        Trainee traineeById = traineeService.findTraineeById(trainee.getId());
//
//        trainee2.setId(trainee.getId());
//
//        traineeService.updateTrainee(trainee2);
//
//        when(traineeRepository.findById(15L)).thenReturn(Optional.empty());
//
//        Trainee updatedTraineeById = traineeService.findTraineeById(15L);
//
//        verify(traineeRepository).updateTrainee(trainee2);
//        verify(traineeRepository).save(trainee);
//
//        Assertions.assertEquals(traineeById, trainee);
//        Assertions.assertNull(updatedTraineeById);
//    }


    @Test
    public void findTraineeById_withExistingId_returnsEntity() {
        when(traineeRepository.findById(trainee.getId())).thenReturn(Optional.ofNullable(trainee));

        traineeService.saveTrainee(trainee);

        Trainee traineeById = traineeService.findTraineeById(trainee.getId());


        verify(traineeRepository).findById(trainee.getId());
        verify(traineeRepository).save(trainee);

        Assertions.assertEquals(traineeById, trainee);

    }

    @Test
    public void findTraineeById_withNonExistingId_returnsNull() {
        when(traineeRepository.findById(trainee.getId())).thenReturn(Optional.empty());

        traineeService.saveTrainee(trainee);

        Trainee traineeById = traineeService.findTraineeById(trainee.getId());

        verify(traineeRepository).findById(trainee.getId());
        verify(traineeRepository).save(trainee);

        Assertions.assertNull(traineeById);

    }

    @Test
    public void deleteTrainee_withValidId_returnsValidEntity() {
        when(traineeRepository.findById(trainee.getId())).thenReturn(Optional.ofNullable(trainee));

        traineeService.saveTrainee(trainee);

        traineeService.deleteTraineeById(trainee.getId());

        when(traineeRepository.findById(trainee.getId())).thenReturn(Optional.empty());

        Trainee traineeById = traineeService.findTraineeById(trainee.getId());

        verify(traineeRepository).save(trainee);
        verify(traineeRepository).delete(trainee);

        Assertions.assertNull(traineeById);

    }

    @Test
    public void deleteTrainee_withInvalidId_returnsNull() {
        when(traineeRepository.findById(5L)).thenReturn(Optional.empty());

        traineeService.saveTrainee(trainee);

        Trainee traineeById = traineeService.findTraineeById(5L);

        traineeService.deleteTraineeById(trainee.getId());

        verify(traineeRepository).delete(trainee);
        verify(traineeRepository).save(trainee);

        Assertions.assertNull(traineeById);

    }



    @Test
    public void findByFirstName_withExistingData_returnsValidEntity() {
        when(traineeRepository.findByFirstName(trainee.getUser().getFirstName())).thenReturn(Optional.of(trainee));

        traineeService.saveTrainee(trainee);

        Trainee traineeByFirstname = traineeService.findByFirstName(trainee.getUser().getFirstName());


        verify(traineeRepository).findByFirstName(trainee.getUser().getFirstName());
        verify(traineeRepository).save(trainee);

        Assertions.assertEquals(traineeByFirstname, trainee);

    }

    @Test
    public void findByFirstName_withNonExistingData_returnsNull() {
        when(traineeRepository.findByFirstName("George")).thenReturn(Optional.empty());

        traineeService.saveTrainee(trainee);

        Trainee traineeByFirstName = traineeService.findByFirstName("George");

        verify(traineeRepository).findByFirstName("George");
        verify(traineeRepository).save(trainee);

        Assertions.assertNull(traineeByFirstName);

    }

    @Test
    public void findByLastName_withExistingData_returnsValidEntity() {
        when(traineeRepository.findByLastName(trainee.getUser().getLastName())).thenReturn(Optional.of(trainee));

        traineeService.saveTrainee(trainee);

        Trainee traineeByLastname = traineeService.findByLastName(trainee.getUser().getLastName());


        verify(traineeRepository).findByLastName(trainee.getUser().getLastName());
        verify(traineeRepository).save(trainee);

        Assertions.assertEquals(traineeByLastname, trainee);
    }

    @Test
    public void findByLastName_withNonExistingData_returnsNull() {
        when(traineeRepository.findByLastName("Bush")).thenReturn(Optional.empty());

        traineeService.saveTrainee(trainee);

        Trainee traineeServiceByLastName = traineeService.findByLastName("Bush");

        verify(traineeRepository).findByLastName("Bush");
        verify(traineeRepository).save(trainee);

        Assertions.assertNull(traineeServiceByLastName);
    }




}







