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

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class TraineeServiceTest {


    @Mock
    private TraineeDAOImpl traineeDAOImpl;

    @InjectMocks
    private TraineeService traineeService;

    private Trainee trainee;
    private Trainee trainee2;

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


    }

    @Test
    public void save_withValidData_returnsValidEntity() {
        traineeService.saveTrainee(trainee);

        verify(traineeDAOImpl).save(trainee);

        when(traineeDAOImpl.findById(trainee.getId())).thenReturn(Optional.of(trainee));

        Optional<Trainee> traineeById = traineeService.findTraineeById(trainee.getId());


        Assertions.assertEquals(traineeById.get().getUser(), trainee.getUser());
        Assertions.assertEquals(traineeById.get().getAddress(), trainee.getAddress());
        Assertions.assertEquals(traineeById.get().getDateOfBirth(), trainee.getDateOfBirth());
        Assertions.assertEquals(traineeById.get().getId(), trainee.getId());
        Assertions.assertEquals(traineeById.get().getUser().getId(), trainee.getUser().getId());

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


        verify(traineeDAOImpl).save(trainee);
        verify(traineeDAOImpl).update(trainee2);



        Assertions.assertEquals(updatedTraineeById.get().getUser(), trainee2.getUser());
        Assertions.assertEquals(updatedTraineeById.get().getAddress(), trainee2.getAddress());
        Assertions.assertEquals(updatedTraineeById.get().getId(), trainee2.getId());
        Assertions.assertEquals(updatedTraineeById.get().getDateOfBirth(), trainee2.getDateOfBirth());

    }

    @Test
    public void findTraineeById_withExistingId_returnsEntity() {
        traineeService.saveTrainee(trainee);

        when(traineeDAOImpl.findById(trainee.getId())).thenReturn(Optional.of(trainee));

        Optional<Trainee> traineeById = traineeService.findTraineeById(trainee.getId());

        Assertions.assertTrue(traineeById.isPresent());

        verify(traineeDAOImpl).findById(trainee.getId());
        verify(traineeDAOImpl).save(trainee);

        Assertions.assertEquals(traineeById.get().getId(), trainee.getId());
        Assertions.assertEquals(traineeById.get().getUser(), trainee.getUser());
        Assertions.assertEquals(traineeById.get().getAddress(), trainee.getAddress());
        Assertions.assertEquals(traineeById.get().getDateOfBirth(), trainee.getDateOfBirth());
    }

    @Test
    public void deleteTraineeById_withValidId_returnsValidEntity() {
        traineeService.saveTrainee(trainee);

        when(traineeDAOImpl.findById(trainee.getId())).thenReturn(Optional.of(trainee));

        traineeService.deleteTraineeById(trainee.getId());

        when(traineeDAOImpl.findById(trainee.getId())).thenReturn(Optional.empty());

        verify(traineeDAOImpl).deleteById(trainee.getId());

        Assertions.assertEquals(traineeService.findTraineeById(trainee.getId()), Optional.empty());

    }

    @Test
    public void findAllTrainees_withExistingData_returnsValidEntities() {
        traineeService.saveTrainee(trainee);
        traineeService.saveTrainee(trainee2);

        Set<Map.Entry<Long, Trainee>> mockTraineeSet = new HashSet<>();
        mockTraineeSet.add(new AbstractMap.SimpleEntry<>(trainee.getId(), trainee));
        mockTraineeSet.add(new AbstractMap.SimpleEntry<>(trainee2.getId(), trainee2));

        when(traineeDAOImpl.findAll()).thenReturn(mockTraineeSet);

        Set<Map.Entry<Long, Trainee>> result = traineeDAOImpl.findAll();

        verify(traineeDAOImpl).findAll();
        verify(traineeDAOImpl).save(trainee);
        verify(traineeDAOImpl).save(trainee2);

        Assertions.assertTrue(result.contains(new AbstractMap.SimpleEntry<>(trainee.getId(), trainee)));
        Assertions.assertTrue(result.contains(new AbstractMap.SimpleEntry<>(trainee2.getId(), trainee2)));

    }

    @Test
    public void findByFirstName_withExistingData_returnsValidEntity() {
        traineeService.saveTrainee(trainee);

        when(traineeDAOImpl.findByFirstName(trainee.getUser().getFirstName())).thenReturn(Optional.of(trainee));

        Optional<Trainee> firstName = traineeService.findTraineeByFirstName(trainee.getUser().getFirstName());

        Assertions.assertTrue(firstName.isPresent());

        verify(traineeDAOImpl).save(trainee);
        verify(traineeDAOImpl).findByFirstName(trainee.getUser().getFirstName());

        Assertions.assertEquals(traineeService.findTraineeByFirstName(trainee.getUser().getFirstName()), Optional.of(trainee));
    }

    @Test
    public void findByLastName_withExistingData_returnsValidEntity() {
        traineeService.saveTrainee(trainee);

        when(traineeDAOImpl.findByLastName(trainee.getUser().getLastName())).thenReturn(Optional.of(trainee));

        Optional<Trainee> lastName = traineeService.findByLastName(trainee.getUser().getLastName());

        Assertions.assertTrue(lastName.isPresent());

        verify(traineeDAOImpl).save(trainee);
        verify(traineeDAOImpl).findByLastName(trainee.getUser().getLastName());

        Assertions.assertEquals(traineeService.findByLastName(trainee.getUser().getLastName()), Optional.of(trainee));
    }
}





