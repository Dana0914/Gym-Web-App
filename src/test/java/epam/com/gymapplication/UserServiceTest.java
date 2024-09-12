package epam.com.gymapplication;


import epam.com.gymapplication.dao.UserDAO;
import epam.com.gymapplication.model.User;
import epam.com.gymapplication.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserDAO userDAO;

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
        when(userDAO.findById(user.getId())).thenReturn(user);


        userService.save(user);

        User userServiceById = userService.findById(user.getId());

        verify(userDAO).save(user);
        Assertions.assertEquals(userServiceById, user);

    }

    @Test
    public void save_withInvalidValidData_returnsNull() {
        when(userDAO.findById(user.getId())).thenReturn(null);

        userService.save(user);

        User userServiceById = userService.findById(user.getId());

        verify(userDAO).save(user);

        Assertions.assertNull(userServiceById);

    }

    @Test
    public void update_withExistingEntity_updatesEntityDetails() {
        when(userDAO.findById(user.getId())).thenReturn(user);

        userService.save(user);

        User userServiceById = userService.findById(user.getId());


        user2.setId(user.getId());

        userService.update(user2);

        when(userDAO.findById(user2.getId())).thenReturn(user2);

        User updatedUserById = userService.findById(user2.getId());


        verify(userDAO).update(user2);
        verify(userDAO).save(user);

        Assertions.assertEquals(userServiceById, user);
        Assertions.assertEquals(updatedUserById, user2);
    }

    @Test
    public void update_withInvalidData_returnsNull() {
        when(userDAO.findById(user.getId())).thenReturn(user);

        userService.save(user);

        User userServiceById = userService.findById(user.getId());

        user2.setId(user.getId());

        userService.update(user2);

        when(userDAO.findById(15L)).thenReturn(null);

        User updatedUserById = userService.findById(15L);

        verify(userDAO).update(user2);
        verify(userDAO).save(user);

        Assertions.assertEquals(userServiceById, user);
        Assertions.assertNull(updatedUserById);
    }

    @Test
    public void findUserById_withExistingId_returnsEntity() {
        when(userDAO.findById(user.getId())).thenReturn(user);

        userService.save(user);

        User userServiceById = userService.findById(user.getId());

        verify(userDAO).findById(user.getId());
        verify(userDAO).save(user);

        Assertions.assertEquals(userServiceById, user);

    }

    @Test
    public void findUserById_withNonExistingId_returnsNull() {
        when(userDAO.findById(user.getId())).thenReturn(null);

        userService.save(user);

        User userServiceById = userService.findById(user.getId());


        verify(userDAO).findById(user.getId());
        verify(userDAO).save(user);

        Assertions.assertNull(userServiceById);

    }

    @Test
    public void deleteUser_withValidEntity_returnsValidEntity() {
        when(userDAO.findById(user.getId())).thenReturn(user);

        userService.save(user);

        userService.delete(user);

        when(userDAO.findById(user.getId())).thenReturn(null);

        User userServiceById = userService.findById(user.getId());

        verify(userDAO).save(user);
        verify(userDAO).delete(user);

        Assertions.assertNull(userServiceById);

    }

    @Test
    public void deleteUser_withInvalidData_returnsNull() {
        when(userDAO.findById(5L)).thenReturn(null);

        userService.save(user);

        User userServiceById = userService.findById(5L);

        userService.delete(user);

        verify(userDAO).delete(user);
        verify(userDAO).save(user);

        Assertions.assertNull(userServiceById);

    }

    @Test
    public void findByFirstName_withExistingData_returnsValidEntity() {
        when(userDAO.findByFirstName(user.getFirstName())).thenReturn(user);

        userService.save(user);

        User userServiceByFirstname = userService.findByFirstname(user.getFirstName());

        verify(userDAO).findByFirstName(user.getFirstName());
        verify(userDAO).save(user);

        Assertions.assertEquals(userServiceByFirstname, user);

    }

    @Test
    public void findByFirstName_withNonExistingData_returnsNull() {
        when(userDAO.findByFirstName("George")).thenReturn(null);

        userService.save(user);

        User userServiceByFirstname = userService.findByFirstname("George");

        verify(userDAO).findByFirstName("George");
        verify(userDAO).save(user);

        Assertions.assertNull(userServiceByFirstname);

    }

    @Test
    public void findByLastName_withExistingData_returnsValidEntity() {
        when(userDAO.findByLastName(user.getLastName())).thenReturn(user);

        userService.save(user);


        User userServiceByLastname = userService.findByLastname(user.getLastName());

        verify(userDAO).findByLastName(user.getLastName());
        verify(userDAO).save(user);

        Assertions.assertEquals(userServiceByLastname, user);
    }

    @Test
    public void findByLastName_withNonExistingData_returnsNull() {
        when(userDAO.findByLastName("Bush")).thenReturn(null);

        userService.save(user);

        User userServiceByLastname = userService.findByLastname("Bush");

        verify(userDAO).findByLastName("Bush");
        verify(userDAO).save(user);

        Assertions.assertNull(userServiceByLastname);
    }

    @Test
    public void findByUsername_withExistingData_returnsValidEntity() {
        when(userDAO.findByUsername(user.getUsername())).thenReturn(user);

        userService.save(user);


        User userServiceByUsername = userService.findByUsername(user.getUsername());

        verify(userDAO).findByUsername(user.getUsername());
        verify(userDAO).save(user);

        Assertions.assertEquals(userServiceByUsername, user);
    }

    @Test
    public void findByUsername_withNonExistingData_returnsNull() {
        when(userDAO.findByUsername("George.Bush")).thenReturn(null);

        userService.save(user);

        User userServiceByUsername = userService.findByUsername("George.Bush");

        verify(userDAO).findByUsername("George.Bush");
        verify(userDAO).save(user);

        Assertions.assertNull(userServiceByUsername);
    }

}
