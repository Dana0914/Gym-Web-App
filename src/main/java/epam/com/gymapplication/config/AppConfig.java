package epam.com.gymapplication.config;

import epam.com.gymapplication.profile.PasswordGenerator;
import epam.com.gymapplication.profile.UserProfileService;
import epam.com.gymapplication.service.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;



@Configuration
@ComponentScan(basePackages = "epam.com.gymapplication")
public class AppConfig {

    @Bean
    public RestCallLoggingInterceptor restCallLoggingInterceptor() {
        return new RestCallLoggingInterceptor();
    }



    @Bean
    public PersistenceExceptionTranslationPostProcessor exceptionTranslation() {
        return new PersistenceExceptionTranslationPostProcessor();
    }

    @Bean
    public PasswordGenerator passwordGenerator() {
        return new PasswordGenerator();
    }

    @Bean
    public UserProfileService userProfileService() {
        return new UserProfileService();
    }

    @Bean
    public TraineeService traineeService() {
        return new TraineeService();
    }

    @Bean
    public TrainerService trainerService() {
        return new TrainerService();
    }

    @Bean
    public TrainingService trainingService() {
        return new TrainingService();
    }

    @Bean
    public TrainingTypeService trainingTypeService() {
        return new TrainingTypeService();
    }

    @Bean
    public UserService userService() {
        return new UserService();
    }









}
