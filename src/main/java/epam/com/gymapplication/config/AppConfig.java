package epam.com.gymapplication.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import epam.com.gymapplication.dao.impl.TraineeDAOImpl;
import epam.com.gymapplication.dao.impl.TrainerDAOImpl;
import epam.com.gymapplication.dao.impl.TrainingDAOImpl;
import epam.com.gymapplication.facade.Facade;
import epam.com.gymapplication.model.Trainee;
import epam.com.gymapplication.model.Trainer;
import epam.com.gymapplication.model.Training;
import epam.com.gymapplication.postprocessor.StorageInitializer;
import epam.com.gymapplication.profile.PasswordGenerator;
import epam.com.gymapplication.profile.UserProfileService;
import epam.com.gymapplication.service.TraineeService;
import epam.com.gymapplication.service.TrainerService;
import epam.com.gymapplication.service.TrainingService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


import java.util.HashMap;
import java.util.Map;

@Configuration
public class AppConfig {

    @Bean
    public Map<Long, Trainee> traineeStorage() {
        return new HashMap<>();
    }


    @Bean
    public ObjectMapper getObjectMapper() {
        return new ObjectMapper();

    }

    @Bean
    public Map<Long, Trainer> trainerStorage() {
        return new HashMap<>();
    }

    @Bean
    public Map<Long, Training> trainingStorage() {
        return new HashMap<>();
    }


    @Bean
    public StorageInitializer storageInit() {
        return new StorageInitializer();
    }

    @Bean
    public PasswordGenerator passwordGenerator() {
        return new PasswordGenerator();
    }

    @Bean
    public UserProfileService userProfileUtils(Map<Long, Trainee> traineeStorage,
                                               Map<Long, Trainer> trainerStorage) {
        return new UserProfileService(traineeStorage, trainerStorage);
    }

    @Bean
    public Facade facade(TraineeService traineeService,
                         TrainerService trainerService,
                         TrainingService trainingService) {
        return new Facade(traineeService, trainerService, trainingService);
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
    public TraineeDAOImpl traineeDAO() {
        return new TraineeDAOImpl();
    }
    @Bean
    public TrainingDAOImpl trainingDAO() {
        return new TrainingDAOImpl();
    }
    @Bean
    public TrainerDAOImpl trainerDAO() {
        return new TrainerDAOImpl();
    }

}
