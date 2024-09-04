package epam.com.gymapplication;


import epam.com.gymapplication.config.AppConfig;
import epam.com.gymapplication.dao.impl.TrainingDAOImpl;
import epam.com.gymapplication.facade.Facade;
import epam.com.gymapplication.model.Trainee;
import epam.com.gymapplication.model.Training;
import epam.com.gymapplication.model.User;
import epam.com.gymapplication.postprocessor.StorageInitializer;
import epam.com.gymapplication.service.TraineeService;
import epam.com.gymapplication.service.TrainingService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.time.LocalDate;


public class App {
    public static void main( String[] args ) {
        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        StorageInitializer storageInitializer = context.getBean(StorageInitializer.class);
        storageInitializer.initializeStorage();

        User user = new User();
        user.setUsername("john.smith");
        user.setPassword("Gym");
        user.setFirstName("john");
        user.setLastName("smith");
        user.setId(1L);
        user.setActive(true);

        Trainee trainee = new Trainee();
        trainee.setId(1L);
        trainee.setUser(user);
        trainee.setAddress("street");
        trainee.setUserId(user.getId());
        trainee.setDateOfBirth(LocalDate.of(2001, 1, 1));

        Training training = new Training(null, null, null, null, null, null, null);




        TraineeService traineeService = context.getBean(TraineeService.class);
        TrainingService trainingService = context.getBean(TrainingService.class);


        //trainingService.saveTraining(training);

        traineeService.saveTrainee(trainee);
        System.out.println(traineeService.getAllTrainees());

        Facade facade = context.getBean(Facade.class);
        facade.saveTraining(training);


    }
}
