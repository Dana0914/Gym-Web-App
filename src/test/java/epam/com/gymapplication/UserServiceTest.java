package epam.com.gymapplication;


import epam.com.gymapplication.dao.UserRepository;
import epam.com.gymapplication.entity.User;
import epam.com.gymapplication.service.UserService;
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
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;


    private User user;
    private User user2;

    @BeforeEach
    public void setUp() {

        user = new User();
        user.setId(1L);
        user.setUsername("John.Doe");
        user.setPassword("8&hs-@.");
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setActive(true);

        user2 = new User();
        user2.setId(2L);
        user2.setUsername("Raul.Gutierrez");
        user2.setPassword("586glb(-");
        user2.setFirstName("Raul");
        user2.setLastName("Gutierrez");
        user2.setActive(true);

    }

    @Test
    public void save_withValidData_returnsValidEntity() {
        when(userRepository.findById(user.getId())).thenReturn(Optional.ofNullable(user));


        userService.save(user);

        User userServiceById = userService.findById(user.getId());

        verify(userRepository).save(user);
        Assertions.assertEquals(userServiceById, user);

    }

    @Test
    public void save_withInvalidValidData_returnsNull() {
        when(userRepository.findById(user.getId())).thenReturn(null);

        userService.save(user);

        User userServiceById = userService.findById(user.getId());

        verify(userRepository).save(user);

        Assertions.assertNull(userServiceById);

    }

//    @Test
//    public void update_withExistingEntity_updatesEntityDetails() {
//        when(userRepository.findById(user.getId())).thenReturn(Optional.ofNullable(user));
//
//        userService.save(user);
//
//        User userServiceById = userService.findById(user.getId());
//
//
//        user2.setId(user.getId());
//
//        userService.update(user2);
//
//        when(userRepository.findById(user2.getId())).thenReturn(Optional.ofNullable(user2));
//
//        User updatedUserById = userService.findById(user2.getId());
//
//
//        verify(userRepository).update(user2);
//        verify(userRepository).save(user);
//
//        Assertions.assertEquals(userServiceById, user);
//        Assertions.assertEquals(updatedUserById, user2);
//    }

//    @Test
//    public void update_withInvalidData_returnsNull() {
//        when(userRepository.findById(user.getId())).thenReturn(Optional.ofNullable(user));
//
//        userService.save(user);
//
//        User userServiceById = userService.findById(user.getId());
//
//        user2.setId(user.getId());
//
//        userService.update(user2);
//
//        when(userRepository.findById(15L)).thenReturn(null);
//
//        User updatedUserById = userService.findById(15L);
//
//        verify(userRepository).update(user2);
//        verify(userRepository).save(user);
//
//        Assertions.assertEquals(userServiceById, user);
//        Assertions.assertNull(updatedUserById);
//    }

    @Test
    public void findUserById_withExistingId_returnsEntity() {
        when(userRepository.findById(user.getId())).thenReturn(Optional.ofNullable(user));

        userService.save(user);

        User userServiceById = userService.findById(user.getId());

        verify(userRepository).findById(user.getId());
        verify(userRepository).save(user);

        Assertions.assertEquals(userServiceById, user);

    }

    @Test
    public void findUserById_withNonExistingId_returnsNull() {
        when(userRepository.findById(user.getId())).thenReturn(null);

        userService.save(user);

        User userServiceById = userService.findById(user.getId());


        verify(userRepository).findById(user.getId());
        verify(userRepository).save(user);

        Assertions.assertNull(userServiceById);

    }

    @Test
    public void deleteUser_withValidEntity_returnsValidEntity() {
        when(userRepository.findById(user.getId())).thenReturn(Optional.ofNullable(user));

        userService.save(user);

        userService.deleteById(user.getId());

        when(userRepository.findById(user.getId())).thenReturn(null);

        User userServiceById = userService.findById(user.getId());

        verify(userRepository).save(user);
        verify(userRepository).delete(user);

        Assertions.assertNull(userServiceById);

    }

    @Test
    public void deleteUser_withInvalidData_returnsNull() {
        when(userRepository.findById(5L)).thenReturn(null);

        userService.save(user);

        User userServiceById = userService.findById(5L);

        userService.deleteById(user.getId());

        verify(userRepository).delete(user);
        verify(userRepository).save(user);

        Assertions.assertNull(userServiceById);

    }

//    @Test
//    public void findByFirstName_withExistingData_returnsValidEntity() {
//        when(userRepository.findByFirstName(user.getFirstName())).thenReturn(user);
//
//        userService.save(user);
//
//        User userServiceByFirstname = userService.findByFirstname(user.getFirstName()).orElseThrow();
//
//        verify(userRepository).findByFirstName(user.getFirstName());
//        verify(userRepository).save(user);
//
//        Assertions.assertEquals(userServiceByFirstname, user);
//
//    }

//    @Test
//    public void findByFirstName_withNonExistingData_returnsNull() {
//        when(userRepository.findByFirstName("George")).thenReturn(null);
//
//        userService.save(user);
//
//        User userServiceByFirstname = userService.findByFirstname("George").orElseThrow();
//
//        verify(userRepository).findByFirstName("George");
//        verify(userRepository).save(user);
//
//        //Assertions.assertNull(userServiceByFirstname);
//
//    }

//    @Test
//    public void findByLastName_withExistingData_returnsValidEntity() {
//        when(userRepository.findByLastName(user.getLastName())).thenReturn(user);
//
//        userService.save(user);
//
//
//        User userServiceByLastname = userService.findByLastname(user.getLastName()).orElseThrow();
//
//        verify(userRepository).findByLastName(user.getLastName());
//        verify(userRepository).save(user);
//
//        Assertions.assertEquals(userServiceByLastname, user);
//    }

//    @Test
//    public void findByLastName_withNonExistingData_returnsNull() {
//        when(userRepository.findByLastName("Bush")).thenReturn(null);
//
//        userService.save(user);
//
//        User userServiceByLastname = userService.findByLastname("Bush").orElseThrow();
//
//        verify(userRepository).findByLastName("Bush");
//        verify(userRepository).save(user);
//
//        Assertions.assertNull(userServiceByLastname);
//    }

//    @Test
//    public void findByUsername_withExistingData_returnsValidEntity() {
//        when(userRepository.findByUsername(user.getUsername())).thenReturn(user);
//
//        userService.save(user);
//
//
//        User userServiceByUsername = userService.findByUsername(user.getUsername()).orElseThrow();
//
//        verify(userRepository).findByUsername(user.getUsername());
//        verify(userRepository).save(user);
//
//        Assertions.assertEquals(userServiceByUsername, user);
//    }

//    @Test
//    public void findByUsername_withNonExistingData_returnsNull() {
//        when(userRepository.findByUsername("George.Bush")).thenReturn(null);
//
//        userService.save(user);
//
//        User userServiceByUsername = userService.findByUsername("George.Bush").orElseThrow();
//
//        verify(userRepository).findByUsername("George.Bush");
//        verify(userRepository).save(user);
//
//        Assertions.assertNull(userServiceByUsername);
//    }

}
