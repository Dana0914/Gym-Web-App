package epam.com.gymapplication.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import epam.com.gymapplication.dao.TraineeDAO;
import epam.com.gymapplication.dao.TrainerDAO;
import epam.com.gymapplication.dao.UserDAO;
import epam.com.gymapplication.dao.impl.TraineeDAOImpl;
import epam.com.gymapplication.dao.impl.TrainerDAOImpl;
import epam.com.gymapplication.dao.impl.UserDAOImpl;
import epam.com.gymapplication.facade.Facade;
import epam.com.gymapplication.profile.PasswordGenerator;
import epam.com.gymapplication.profile.UserProfileService;
import epam.com.gymapplication.service.TraineeService;
import epam.com.gymapplication.service.TrainerService;
import epam.com.gymapplication.service.TrainingService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;



@Configuration
@ComponentScan(basePackages = "epam.com.gymapplication")

public class AppConfig {


    @Bean
    public ObjectMapper getObjectMapper() {
        return new ObjectMapper();

    }


//    @Bean
//    public StorageInitializer storageInitializer() {
//        return new StorageInitializer();
//    }

    @Bean
    public PasswordGenerator passwordGenerator() {
        return new PasswordGenerator();
    }

    @Bean
    public UserProfileService userProfileUtils() {
        return new UserProfileService();
    }

    @Bean
    public Facade facade(TraineeService traineeService,
                         TrainerService trainerService,
                         TrainingService trainingService) {
        return new Facade(traineeService, trainerService, trainingService);
    }

    @Bean
    public TraineeDAO traineeDAO() {
        return new TraineeDAOImpl();
    }

    @Bean
    public TrainerDAO trainerDAO() {
        return new TrainerDAOImpl();
    }

    @Bean
    public UserDAO userDAO() {
        return new UserDAOImpl();
    }


}
