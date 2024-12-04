package epam.com.gymapplication;

import epam.com.gymapplication.dao.TraineeRepository;
import epam.com.gymapplication.dao.UserRepository;
import epam.com.gymapplication.entity.Trainee;
import epam.com.gymapplication.entity.User;
import epam.com.gymapplication.profile.PasswordGenerator;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;


@SpringBootApplication
@EnableDiscoveryClient
public class App implements CommandLineRunner {
    private final UserRepository userRepository;
    private final PasswordGenerator passwordGenerator;
    private final PasswordEncoder passwordEncoder;
    private final TraineeRepository traineeRepository;

    public App(UserRepository userRepository, PasswordGenerator passwordGenerator, PasswordEncoder passwordEncoder, TraineeRepository traineeRepository) {
        this.userRepository = userRepository;
        this.passwordGenerator = passwordGenerator;
        this.passwordEncoder = passwordEncoder;
        this.traineeRepository = traineeRepository;
    }

    public static void main( String[] args ) {
        SpringApplication.run(App.class, args);

    }

    @Override
    public void run(String... args) throws Exception {
        User user = new User();
        user.setUsername("Emily.Brickman");
        user.setFirstName("Emily");
        user.setLastName("Brickman");
        user.setPassword(passwordEncoder.encode(passwordGenerator.generatePassword()));
        user.setActive(true);

        //userRepository.save(user);

        Trainee trainee = new Trainee();
        trainee.setUser(user);
        trainee.setAddress("Avenue 21");
        trainee.setDateOfBirth(LocalDate.of(1995, 1, 1));

        //traineeRepository.save(trainee);




    }
}
