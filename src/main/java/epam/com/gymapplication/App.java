package epam.com.gymapplication;


import epam.com.gymapplication.config.AppConfig;
import epam.com.gymapplication.config.JpaConfig;
import epam.com.gymapplication.config.MyWebInitializer;
import epam.com.gymapplication.config.WebConfig;
import epam.com.gymapplication.controller.TraineeController;
import epam.com.gymapplication.controller.TrainerController;
import epam.com.gymapplication.service.*;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletRegistration;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.startup.Tomcat;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;


import java.io.File;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class App  {
    public static void main( String[] args ) throws LifecycleException {
        //ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class,
               // WebConfig.class, MyWebInitializer.class, JpaConfig.class);
        AnnotationConfigWebApplicationContext cxt = new AnnotationConfigWebApplicationContext();
        cxt.register(WebConfig.class);
        cxt.register(JpaConfig.class);
        cxt.register(TraineeController.class);
        cxt.register(TrainerController.class);

//        ServletContext servletContext = cxt.getServletContext();
//        servletContext.setInitParameter("contextClass", "org.apache.catalina.startup.ContextInitializer");
//        servletContext.setInitParameter("contextConfigLocation", "classpath:applicationContext.xml");
//        servletContext.setInitParameter("applicationContextConfigLocation", "classpath:application-context.xml");
//        servletContext.setInitParameter("application", "application");
//        servletContext.setInitParameter("contextPath", "/");
//        servletContext.setInitParameter("resourceBase", "./");
//        servletContext.setInitParameter("webapp", "./webapp");
//        servletContext.setInitParameter("javax.faces.application.context.path", "./");
//        servletContext.setInitParameter("javax.faces.resource.path", "./");
//        servletContext.setInitParameter("javax.faces.resource.packages", "./");
//
//        MyWebInitializer webInitializer = new MyWebInitializer();
//        webInitializer.onStartup(servletContext);

//        TraineeService traineeService = context.getBean(TraineeService.class);
//        UserService userService = context.getBean(UserService.class);
//        TrainingService trainingService = context.getBean(TrainingService.class);
//        TrainerService trainerService = context.getBean(TrainerService.class);
//        TrainingTypeService trainingTypeService = context.getBean(TrainingTypeService.class);

        Tomcat tomcat = new Tomcat();
        tomcat.setPort(8080);
        tomcat.getConnector();
        tomcat.addWebapp("/", new File("src/main/webapp").getAbsolutePath());
        tomcat.start();
        tomcat.getServer().await();





        // update trainees trainers list by username
//        Trainee traineeById = traineeService.findTraineeById(1L);
//        Set<Trainer> trainers = traineeById.getTrainers();
//        Trainee traineeByIdToUpdate = traineeService.findTraineeById(2L);
//        trainerService.updateTraineesTrainerList(trainers, traineeByIdToUpdate.getUser().getUsername());

        // find trainer list not assigned to trainee by trainee username
//        Trainee traineeById = traineeService.findTraineeById(2L);
//        List<Trainer> trainerNotAssignedToTrainee = trainerService.findTrainerNotAssignedToTrainee(traineeById.getUser().getUsername());
//        System.out.println(trainerNotAssignedToTrainee);


        // Assigned trainees to trainer and viceversa
//        Trainee traineeById = traineeService.findTraineeById(1L);
//        Trainer trainerById = trainerService.findTrainerById(1L);
//        Set<Trainee> trainees = trainerById.getTrainees();
//        System.out.println(trainees);

//        trainerService.assignTraineeToTrainer(traineeById.getId(), trainerById.getId());




        // find training list by trainee criteria
//        Trainee traineeById = traineeService.findTraineeById(1L);
//        Training trainingById = trainingService.findTrainingById(1L);
//        TrainingType trainingTypeServiceById = trainingTypeService.findById(1L);
//        Training trainingListByTraineeCriteria = trainingService.findTrainingListByTraineeCriteria(traineeById.getUser().getUsername(),
//                trainingById.getTrainingDate(), trainingById.getTrainingDate(),
//                trainingTypeServiceById.getTrainingTypeName());
//        System.out.println(trainingListByTraineeCriteria);

//        User byId = userService.findById(1L);
//        TrainingType trainingTypeServiceById = trainingTypeService.findById(1L);
//
//        Trainer trainer = new Trainer();
//        trainer.setUser(byId);
//        trainer.setUserId(byId.getId());
//        trainer.setTrainingType(trainingTypeServiceById);
//        trainer.setSpecialization(trainingTypeServiceById.getId());
//
//        trainerService.saveTrainer(trainer);

         //saving training type

//        TrainingType trainingType = new TrainingType();
//        trainingType.setTrainingTypeName("stretch");
//        trainingTypeService.save(trainingType);

        // find training type by id
//        TrainingType trainingTypeServiceById = trainingTypeService.findById(1L);
//
//         //find trainee by id
//        Trainee traineeById = traineeService.findTraineeById(1L);
//
//         //find trainer by id
//        Trainer trainerById = trainerService.findTrainerById(1L);

         //save training
//        Training training = new Training();
//        training.setTrainingName("aerobics");
//        training.setTrainingDate(LocalDate.of(2024, 9, 20));
//        training.setTrainingDuration(45);
//        training.setTrainee(traineeById);
//        training.setTrainingType(trainingTypeServiceById);
//        training.setTrainer(trainerById);
//        trainingService.saveTraining(training);
//        trainingService.addTraining(traineeById, trainerById, trainingTypeServiceById);


        // generate new password

//        Trainee traineeById = traineeService.findTraineeById(2L);
//        String password = traineeById.getUser().getPassword();
//        Trainee trainee = traineeService.passwordChange(traineeById.getId(), password);
//        System.out.println(trainee);


        // update trainee profile by id, firstname, lastname
//        traineeService.updateTraineeProfile(2L, "Derek", "Cassini");

        // deleting trainee profile by username
//        traineeService.deleteTraineeProfileByUsername("Ben.Thompson");


        // authenticate trainee profile
//        Trainee byUsername = traineeService.findByUsername("Ben.Thompson");
//        String password = byUsername.getUser().getPassword();
//        boolean authenticatedTraineeProfile = traineeService.authenticateTraineeProfile(byUsername.getUser().getUsername(), password);
//        System.out.println(authenticatedTraineeProfile);

        // find trainee profile by username
//        Trainee traineeProfileByUsername = traineeService.findTraineeProfileByUsername("Ben.Thompson");
//        System.out.println(traineeProfileByUsername);


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
        // Create Trainee Profile
//        Trainee traineeProfile = traineeService.createTraineeProfile(trainee, user);






        // fetch by id
//        Optional<User> userServiceById = userService.findById(1L);
//        System.out.println(userServiceById);
//        Trainer trainerById = trainerService.findTrainerById(1L);
//        System.out.println(trainerById);
//        TrainingType trainingTypeServiceById = trainingTypeService.findById(1L);
//        System.out.println(trainingTypeServiceById);


        // persist new entity
//        User user = new User();
//        user.setUsername("Sara.Johnson");
//        user.setFirstName("Sara");
//        user.setLastName("Johnson");
//        user.setPassword("hj7-86^&");
//        user.setActive(true);
//
//
//        userService.save(user);
//
//
//        Trainee trainee = new Trainee();
//        trainee.setUser(user);
//        trainee.setAddress("street 10");
//        trainee.setDateOfBirth(LocalDate.of(2000, 4, 4));
//
//        traineeService.saveTrainee(trainee);



        //training type persist

//        TrainingType trainingType = new TrainingType();
//        trainingType.setTrainingTypeName("stretch");
//
//        trainingTypeService.save(trainingType);

        // trainer entity persist

//        Training trainingById = trainingService.findTrainingById(1L);
//        User userServiceById = userService.findById(2L);
//        Trainer trainer = new Trainer();
//        trainer.setUser(userServiceById);
//        trainer.setTrainingType(trainingById.getTrainingType());
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
