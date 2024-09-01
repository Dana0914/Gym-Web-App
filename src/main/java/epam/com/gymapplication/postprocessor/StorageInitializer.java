package epam.com.gymapplication.postprocessor;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import epam.com.gymapplication.model.Trainee;
import epam.com.gymapplication.model.Trainer;
import epam.com.gymapplication.model.Training;
import epam.com.gymapplication.model.UserBase;
import epam.com.gymapplication.profile.PasswordGenerator;
import epam.com.gymapplication.profile.UserProfileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Map;


@Component
@PropertySource("${storage.init.file.path}")
public class StorageInitializer {
    private static final Logger logger = LoggerFactory.getLogger(StorageInitializer.class);

    private final Map<Long, Trainee> traineeStorage;
    private final Map<Long, Trainer> trainerStorage;
    private final Map<Long, Training> trainingStorage;

    private final ResourceLoader resourceLoader;

    private final ObjectMapper objectMapper;

    private final PasswordGenerator passwordGenerator;

    private final UserProfileService userProfileService;

    @Value("${classpath:init-data.json}")
    private String file;


    @Autowired
    public StorageInitializer(Map<Long, Trainee> traineeStorage,
                              Map<Long, Trainer> trainerStorage,
                              Map<Long, Training> trainingStorage,
                              ResourceLoader resourceLoader,
                              ObjectMapper objectMapper,
                              PasswordGenerator passwordGenerator,
                              UserProfileService userProfileService) {

        this.traineeStorage = traineeStorage;
        this.trainerStorage = trainerStorage;
        this.trainingStorage = trainingStorage;
        this.resourceLoader = resourceLoader;
        this.objectMapper = objectMapper;
        this.passwordGenerator = passwordGenerator;
        this.userProfileService = userProfileService;
    }

    @PostConstruct
    public void initializeStorage() {
        Resource resource;
        try {
            resource = resourceLoader.getResource(file);
            List<UserBase> users = objectMapper.readValue(resource.getInputStream(), new TypeReference<>() {
            });

            if (resource.exists()) {
                for (UserBase user : users) {
                    if (user instanceof Trainee) {

                        String traineeUsername = userProfileService.concatenateUsername(
                                ((Trainee) user).getUser().getFirstName(),
                                ((Trainee) user).getUser().getLastName());

                        ((Trainee) user).getUser().setUsername(traineeUsername);
                        ((Trainee) user).getUser().setPassword(passwordGenerator.generatePassword());

                        Trainee addedTrainee = traineeStorage.put(((Trainee) user).getId(), (Trainee) user);
                        logger.info("Added trainee successfully to storage {} ", addedTrainee);


                    } else if (user instanceof Trainer) {
                        String trainerUsername = userProfileService.concatenateUsername(
                                ((Trainer) user).getUser().getFirstName(),
                                ((Trainer) user).getUser().getLastName());


                        ((Trainer) user).getUser().setUsername(trainerUsername);
                        ((Trainer) user).getUser().setPassword(passwordGenerator.generatePassword());

                        Trainer addedTrainer = trainerStorage.put(((Trainer) user).getId(), (Trainer) user);
                        logger.info("Added trainer successfully to storage {} ", addedTrainer);


                    } else if (user instanceof Training) {
                        trainingStorage.put(((Training) user).getId(), (Training) user);
                        logger.info("Added training successfully to storage {} ", user);

                    }
                }

            } else {
                throw new FileNotFoundException("File not found, " +  file);
            }
        } catch (Exception e) {
            logger.error("An exception occurred while opening the file", e);
        }
    }

    @PreDestroy
    public void close() {
        System.out.println("Shutting down the storage");
    }
}
