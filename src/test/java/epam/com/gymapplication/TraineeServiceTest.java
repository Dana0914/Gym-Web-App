package epam.com.gymapplication;

import epam.com.gymapplication.dao.TraineeRepository;
import epam.com.gymapplication.entity.Trainee;
import epam.com.gymapplication.entity.User;
import epam.com.gymapplication.service.TraineeService;
import jakarta.persistence.EntityNotFoundException;
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
    private Trainee secondTrainee;
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


        secondTrainee = new Trainee();
        secondTrainee.setId(2L);
        secondTrainee.setAddress("Edinburgh street 4");
        secondTrainee.setDateOfBirth(LocalDate.of(2000, 8, 15));
        secondTrainee.setUser(user);

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


        verify(traineeRepository).save(trainee);

        Assertions.assertThrows(EntityNotFoundException.class,
                () -> traineeService.findTraineeById(trainee.getId()));


    }



    @Test
    public void findTraineeById_withExistingId_returnsEntity() {
        when(traineeRepository.findById(trainee.getId())).thenReturn(Optional.of(trainee));

        traineeService.saveTrainee(trainee);

        Trainee traineeById = traineeService.findTraineeById(trainee.getId());


        verify(traineeRepository).findById(trainee.getId());
        verify(traineeRepository).save(trainee);

        Assertions.assertEquals(traineeById, trainee);

    }

    @Test
    public void findTraineeById_withNonExistingId_returnsEmpty() {
        when(traineeRepository.findById(trainee.getId())).thenReturn(Optional.empty());

        Optional<Trainee> traineeById = traineeRepository.findById(trainee.getId());

        Assertions.assertTrue(traineeById.isEmpty());

    }

    @Test
    public void deleteTrainee_withValidId_returnsValidEntity() {
        when(traineeRepository.findById(trainee.getId())).thenReturn(Optional.of(trainee));

        traineeService.deleteTraineeById(trainee.getId());

        verify(traineeRepository).deleteById(trainee.getId());


        when(traineeRepository.findById(trainee.getId())).thenReturn(Optional.empty());


        Optional<Trainee> deletedTrainee = traineeRepository.findById(trainee.getId());
        Assertions.assertTrue(deletedTrainee.isEmpty());

    }

    @Test
    public void deleteTrainee_withInvalidId_returnsEmpty() {
        when(traineeRepository.findById(5L)).thenReturn(Optional.empty());

        traineeService.deleteTraineeById(5L);

        verify(traineeRepository).deleteById(5L);

        Optional<Trainee> traineeById = traineeRepository.findById(5L);

        Assertions.assertTrue(traineeById.isEmpty());

    }



    @Test
    public void findByFirstName_withExistingData_returnsValidEntity() {
        when(traineeRepository.findByFirstName(trainee.getUser().getFirstName())).thenReturn(Optional.of(trainee));

        Trainee traineeByFirstname = traineeService.findByFirstName(trainee.getUser().getFirstName());

        verify(traineeRepository).findByFirstName(trainee.getUser().getFirstName());

        Assertions.assertEquals(traineeByFirstname, trainee);

    }

    @Test
    public void findByFirstName_withNonExistingData_returnsEmpty() {
        when(traineeRepository.findByFirstName("George")).thenReturn(Optional.empty());

        Optional<Trainee> traineeByFirstName = traineeRepository.findByFirstName("George");

        verify(traineeRepository).findByFirstName("George");


        Assertions.assertTrue(traineeByFirstName.isEmpty());

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


        Optional<Trainee> traineeServiceByLastName = traineeRepository.findByLastName("Bush");

        verify(traineeRepository).findByLastName("Bush");


        Assertions.assertTrue(traineeServiceByLastName.isEmpty());
    }




}







