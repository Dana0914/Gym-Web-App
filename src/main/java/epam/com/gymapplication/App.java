package epam.com.gymapplication;


import epam.com.gymapplication.config.AppConfig;
import epam.com.gymapplication.facade.Facade;
import epam.com.gymapplication.model.Trainee;
import epam.com.gymapplication.model.User;
import epam.com.gymapplication.service.TraineeService;
import epam.com.gymapplication.service.UserService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.time.LocalDate;
import java.util.Optional;


public class App {
    public static void main( String[] args ) {
        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);

//        StorageInitializer storageInitializer = context.getBean(StorageInitializer.class);
//        storageInitializer.initializeStorage();
        TraineeService traineeService = context.getBean(TraineeService.class);
        UserService userService = context.getBean(UserService.class);



        User user = new User();
        user.setUsername("smith.mccolins");
        user.setPassword("dg58u8");
        user.setFirstName("smith");
        user.setLastName("mccolins");
        user.setActive(true);


        userService.delete(user);




        Trainee trainee = new Trainee();

        trainee.setUserId(user.getId());
        trainee.setAddress("street 12");
        trainee.setDateOfBirth(LocalDate.of(2000, 5, 15));
        trainee.setUser(user);





        traineeService.deleteTrainee(trainee);








    }
}
