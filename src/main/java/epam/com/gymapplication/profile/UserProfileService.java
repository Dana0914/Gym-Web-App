package epam.com.gymapplication.profile;

import epam.com.gymapplication.model.Trainee;
import epam.com.gymapplication.model.Trainer;

import java.util.Map;

public class UserProfileService {
    private static int counter = 1;
    private static int serialNumber;
    private final Map<Long, Trainee> traineeStorage;
    private final Map<Long, Trainer> trainerStorage;


    public UserProfileService(Map<Long, Trainee> traineeStorage,
                            Map<Long, Trainer> trainerStorage) {
        this.traineeStorage = traineeStorage;
        this.trainerStorage = trainerStorage;
    }


    public String concatenateUsername(String firstName, String lastName) {
        String firstLetterToUpperCase = firstName.substring(0, 1).toUpperCase() + firstName.substring(1).toLowerCase();
        String lastLetterToUpperCase = lastName.substring(0, 1).toUpperCase() + lastName.substring(1).toLowerCase();
        String username = firstLetterToUpperCase + "." + lastLetterToUpperCase;
        String baseName = username;
        String duplicateUsername = handleDuplicateUsername(baseName);
        return duplicateUsername;
    }

    private String handleDuplicateUsername(String username) {
        for (Trainee trainee : traineeStorage.values()) {
            if (trainee.getUser().getUsername().equals(username)) {
                username += serialNumber;
                counter++;
            }
        }
        serialNumber = counter;
        for (Trainer trainer : trainerStorage.values()) {
            if (trainer.getUser().getUsername().equals(username)) {
                username += serialNumber;
                counter++;
            }
        }
        serialNumber = counter;
        return username;

    }

}
