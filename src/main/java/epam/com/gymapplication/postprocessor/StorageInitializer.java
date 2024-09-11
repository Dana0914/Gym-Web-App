package epam.com.gymapplication.postprocessor;

//import com.fasterxml.jackson.core.type.TypeReference;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import epam.com.gymapplication.dao.TraineeDAO;
//import epam.com.gymapplication.dao.TrainerDAO;
//import epam.com.gymapplication.dao.TrainingDAO;
//import epam.com.gymapplication.dao.UserDAO;
//import epam.com.gymapplication.model.*;
//import epam.com.gymapplication.profile.PasswordGenerator;
//import epam.com.gymapplication.profile.UserProfileService;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.PropertySource;
//import org.springframework.core.io.Resource;
//import org.springframework.core.io.ResourceLoader;
//import org.springframework.stereotype.Component;
//
//import jakarta.annotation.PostConstruct;
//import jakarta.annotation.PreDestroy;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.io.FileNotFoundException;
//import java.util.List;
//
//
//@Component
//@PropertySource("${classpath:init-data.json}")
//public class StorageInitializer {
//
//    private static final Logger logger = LoggerFactory.getLogger(StorageInitializer.class);
//
//    @Autowired
//    private TraineeDAO traineeDAO;
//
//    @Autowired
//    private TrainerDAO trainerDAO;
//
//    @Autowired
//    private TrainingDAO trainingDAO;
//
//    @Autowired
//    private UserDAO userDAO;
//
//
//    @Autowired
//    private ResourceLoader resourceLoader;
//
//    @Autowired
//    private ObjectMapper objectMapper;
//
//    @Autowired
//    private PasswordGenerator passwordGenerator;
//
//
//    @Autowired
//    private UserProfileService userProfileService;
//
//    @Value("${classpath:init-data.json}")
//    private String file;
//
//
//
//    @PostConstruct
//    public void initializeStorage() {
//        Resource resource;
//        try {
//            resource = resourceLoader.getResource(file);
//            List<UserBase> users = objectMapper.readValue(resource.getInputStream(), new TypeReference<>() {
//            });
//
//            if (resource.exists()) {
//                for (UserBase user : users) {
//                    if (user instanceof Trainee) {
//
//                        String username = ((Trainee) user).getUser().getUsername();
//                        String firstName = ((Trainee) user).getUser().getFirstName();
//                        String lastName = ((Trainee) user).getUser().getLastName();
//                        String password = ((Trainee) user).getUser().getPassword();
//                        boolean active = ((Trainee) user).getUser().isActive();
//
//                        userDAO.save(new User(firstName, lastName, username, password, active));
//
//                        String traineeUsername = userProfileService.concatenateUsername(
//                                ((Trainee) user).getUser().getFirstName(),
//                                ((Trainee) user).getUser().getLastName());
//
//                        ((Trainee) user).getUser().setUsername(traineeUsername);
//                        ((Trainee) user).getUser().setPassword(passwordGenerator.generatePassword());
//
//
//                        traineeDAO.save((Trainee) user);
//                        logger.info("Added trainee successfully to storage ");
//
//
//                    } else if (user instanceof Trainer) {
//                        String trainerUsername = userProfileService.concatenateUsername(
//                                ((Trainer) user).getUser().getFirstName(),
//                                ((Trainer) user).getUser().getLastName());
//
//
//                        ((Trainer) user).getUser().setUsername(trainerUsername);
//                        ((Trainer) user).getUser().setPassword(passwordGenerator.generatePassword());
//
//                        trainerDAO.save((Trainer) user);
//                        logger.info("Added trainer successfully to storage ");
//
//
//                    } else if (user instanceof Training) {
//                        trainingDAO.save((Training) user);
//                        logger.info("Added training successfully to storage");
//
//                    }
//                }
//
//            } else {
//                throw new FileNotFoundException("File not found, " +  file);
//            }
//        } catch (Exception e) {
//            logger.error("An exception occurred while opening the file", e);
//        }
//    }
//
//    @PreDestroy
//    public void close() {
//        System.out.println("Shutting down the storage");
//    }

