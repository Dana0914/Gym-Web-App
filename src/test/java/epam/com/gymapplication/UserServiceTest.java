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
    private User secondUser;

    @BeforeEach
    public void setUp() {

        user = new User();
        user.setId(1L);
        user.setUsername("John.Doe");
        user.setPassword("8&hs-@.");
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setActive(true);

        secondUser = new User();
        secondUser.setId(2L);
        secondUser.setUsername("Raul.Gutierrez");
        secondUser.setPassword("586glb(-");
        secondUser.setFirstName("Raul");
        secondUser.setLastName("Gutierrez");
        secondUser.setActive(true);

    }

    @Test
    public void save_withValidData_returnsValidEntity() {
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));

        userService.save(user);

        User userServiceById = userService.findById(user.getId());

        verify(userRepository).save(user);

        Assertions.assertEquals(userServiceById, user);

    }

    @Test
    public void save_withInvalidValidData_returnsEmptyEntity() {
        when(userRepository.findById(user.getId())).thenReturn(Optional.empty());

        userService.save(user);

        Optional<User> userServiceById = userRepository.findById(user.getId());

        verify(userRepository).save(user);

        Assertions.assertTrue(userServiceById.isEmpty());

    }



    @Test
    public void findUserById_withExistingId_returnsEntity() {
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));

        User userServiceById = userService.findById(user.getId());

        verify(userRepository).findById(user.getId());

        Assertions.assertEquals(userServiceById, user);

    }

    @Test
    public void findUserById_withNonExistingId_returnsNull() {
        when(userRepository.findById(user.getId())).thenReturn(Optional.empty());

        Optional<User> userServiceById = userRepository.findById(user.getId());

        verify(userRepository).findById(user.getId());

        Assertions.assertTrue(userServiceById.isEmpty());

    }

    @Test
    public void deleteUser_withValidEntity_returnsValidEntity() {
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));

        userService.deleteById(user.getId());

        verify(userRepository).deleteById(user.getId());

        when(userRepository.findById(user.getId())).thenReturn(Optional.empty());


        Optional<User> deletedUser = userRepository.findById(user.getId());
        Assertions.assertTrue(deletedUser.isEmpty());

    }

    @Test
    public void deleteUser_withInvalidData_returnsEmptyEntity() {
        when(userRepository.findById(5L)).thenReturn(Optional.empty());

        userService.deleteById(5L);

        verify(userRepository).deleteById(5L);

        Optional<User> userById = userRepository.findById(5L);

        Assertions.assertTrue(userById.isEmpty());

    }

    @Test
    public void findByFirstName_withExistingData_returnsValidEntity() {
        when(userRepository.findByFirstName(user.getFirstName())).thenReturn(Optional.ofNullable(user));

        User userServiceByFirstname = userService.findByFirstname(user.getFirstName());
        verify(userRepository).findByFirstName(user.getFirstName());

        Assertions.assertEquals(userServiceByFirstname, user);

    }

    @Test
    public void findByFirstName_withNonExistingData_returnsNull() {
        when(userRepository.findByFirstName("George")).thenReturn(Optional.empty());

        Optional<User> userServiceByFirstname = userRepository.findByFirstName("George");

        verify(userRepository).findByFirstName("George");

        Assertions.assertTrue(userServiceByFirstname.isEmpty());




    }

    @Test
    public void findByLastName_withExistingData_returnsValidEntity() {
        when(userRepository.findByLastName(user.getLastName())).thenReturn(Optional.ofNullable(user));

        User userServiceByLastname = userService.findByLastname(user.getLastName());

        verify(userRepository).findByLastName(user.getLastName());
        Assertions.assertEquals(userServiceByLastname, user);
    }

    @Test
    public void findByLastName_withNonExistingData_returnsNull() {
        when(userRepository.findByLastName("Bush")).thenReturn(Optional.empty());

        Optional<User> userServiceByLastname = userRepository.findByLastName("Bush");

        verify(userRepository).findByLastName("Bush");

        Assertions.assertTrue(userServiceByLastname.isEmpty());
    }

    @Test
    public void findByUsername_withExistingData_returnsValidEntity() {
        when(userRepository.findByUsername(user.getUsername())).thenReturn(Optional.ofNullable(user));

        User userServiceByUsername = userService.findByUsername(user.getUsername());

        verify(userRepository).findByUsername(user.getUsername());

        Assertions.assertEquals(userServiceByUsername, user);
    }

    @Test
    public void findByUsername_withNonExistingData_returnsEmptyEntity() {
        when(userRepository.findByUsername("George.Bush")).thenReturn(Optional.empty());

        Optional<User> userServiceByUsername = userRepository.findByUsername("George.Bush");

        verify(userRepository).findByUsername("George.Bush");

        Assertions.assertTrue(userServiceByUsername.isEmpty());
    }

}
