package epam.com.gymapplication.profile;


import epam.com.gymapplication.dao.TraineeDAO;
import epam.com.gymapplication.dao.TrainerDAO;
import epam.com.gymapplication.model.Trainee;
import epam.com.gymapplication.model.Trainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class UserProfileService {

    private static int counter = 1;
    private static int serialNumber;

    @Autowired
    private TraineeDAO traineeDAO;

    @Autowired
    private TrainerDAO trainerDAO;



    public String concatenateUsername(String firstName, String lastName) {
        String firstLetterToUpperCase = firstName.substring(0, 1).toUpperCase() + firstName.substring(1).toLowerCase();
        String lastLetterToUpperCase = lastName.substring(0, 1).toUpperCase() + lastName.substring(1).toLowerCase();
        String username = firstLetterToUpperCase + "." + lastLetterToUpperCase;
        String baseName = username;
        String duplicateUsername = handleDuplicateUsername(baseName);
        return duplicateUsername;
    }

    private String handleDuplicateUsername(String username) {
        for (Trainee trainee : traineeDAO.findAll()) {
            if (trainee.getUser().getUsername().equals(username)) {
                username += serialNumber;
                counter++;
            }
        }

        serialNumber = counter;
        for (Trainer trainer : trainerDAO.findAll()) {
            if (trainer.getUser().getUsername().equals(username)) {
                username += serialNumber;
                counter++;
            }
        }

        serialNumber = counter;
        return username;

    }

}
