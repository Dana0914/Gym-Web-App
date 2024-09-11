package epam.com.gymapplication;


import epam.com.gymapplication.config.AppConfig;
import epam.com.gymapplication.model.*;
import epam.com.gymapplication.service.*;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.time.LocalDate;
import java.util.Optional;


public class App {
    public static void main( String[] args )  {
        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        TraineeService traineeService = context.getBean(TraineeService.class);
        UserService userService = context.getBean(UserService.class);
        TrainingService trainingService = context.getBean(TrainingService.class);
        TrainerService trainerService = context.getBean(TrainerService.class);
        TrainingTypeService trainingTypeService = context.getBean(TrainingTypeService.class);




        // create trainee profile
//        User user = new User();
//        user.setFirstName("Ben");
//        user.setActive(true);
//        user.setLastName("Thompson");
//
//
//        Trainee trainee = new Trainee();
//        trainee.setUserId(user.getId());
//        trainee.setAddress("street 01");
//        trainee.setDateOfBirth(LocalDate.of(1990, 1, 1));

        // deactivate trainee
//        traineeService.deactivateTrainee(7L);
//
//        // Create Trainee Profile
//        Trainee traineeProfile = traineeService.createTraineeProfile(trainee, user);
//
//        traineeService.saveTrainee(traineeProfile);




        // fetch by id
//        Optional<User> userServiceById = userService.findById(1L);
//        System.out.println(userServiceById);
//        Trainer trainerById = trainerService.findTrainerById(1L);
//        System.out.println(trainerById);
//        TrainingType trainingTypeServiceById = trainingTypeService.findById(1L);
//        System.out.println(trainingTypeServiceById);


//        // persist new entity
//        User user = new User();
//        user.setUsername("Ash.Moss");
//        user.setFirstName("Ash");
//        user.setLastName("Moss");
//        user.setPassword("5gjf9-6hc");
//        user.setActive(true);
//
//
//        userService.save(user);
//
//
//        Trainee trainee = new Trainee();
//        trainee.setUser(user);
//        trainee.setUserId(user.getId());
//        trainee.setAddress("street 6");
//        trainee.setDateOfBirth(LocalDate.of(2000, 5, 15));
//
//
//        //traineeService.saveTrainee(trainee);
//
//        Set<Trainee> trainees = new HashSet<>();
//        trainees.add(trainee);
//
//        //training type persist
//
//        TrainingType trainingType = new TrainingType();
//        trainingType.setTrainingTypeName("stretch");
//
//        trainingTypeService.save(trainingType);
//
//        // trainer entity persist
//        Trainer trainer = new Trainer();
//        trainer.setUser(user);
//        trainer.setSpecialization(1L);
//        trainer.setUserId(1L);
//        trainer.setTrainingType(trainingType);
//
//
//        trainerService.saveTrainer(trainer);




        // update user by existing id
//        User byId = userService.findById(4L);
//        byId.setFirstName("john");
//        byId.setLastName("doe");
//        byId.setActive(true);
//        byId.setPassword("45ydhp7");
//        byId.setUsername("john.doe");
//        userService.update(byId);














    }
}
