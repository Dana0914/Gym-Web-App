package epam.com.gymapplication.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class TraineeDTO {
    public interface ActivateDeactivateTrainee{}

    public interface DeleteTraineeProfile{}

    public interface GetTraineeProfile{}

    public interface UpdatedTraineeProfile{}

    public interface UpdatedTraineesTrainerList{}

    @NotNull(groups = {Login.class, ChangeLogin.class, GetTraineeProfile.class,
            UpdatedTraineeProfile.class, DeleteTraineeProfile.class,
    ActivateDeactivateTrainee.class})
    private String username;

    @NotBlank(groups = Login.class)
    private String password;

    @NotBlank(groups = {Registration.class, UpdatedTraineeProfile.class})
    private String firstname;

    @NotBlank(groups = {Registration.class, UpdatedTraineeProfile.class})
    private String lastname;


    private String address;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateOfBirth;

    @NotBlank(groups = {UpdatedTraineeProfile.class, ActivateDeactivateTrainee.class})
    @JsonProperty(value = "active")
    private Boolean isActive;

    @NotBlank(groups = {UpdatedTraineesTrainerList.class})
    private List<TrainerDTO> trainers;


    private List<TrainingDTO> trainings;

    @NotBlank(groups = ChangeLogin.class)
    private String oldPassword;

    @NotBlank(groups = ChangeLogin.class)
    private String newPassword;


    public TraineeDTO() {

    }
    public TraineeDTO(String firstname, String lastname, String address,
                      LocalDate dateOfBirth, Boolean isActive,
                      List<TrainerDTO> trainers, String username, String password) {

        this.firstname = firstname;
        this.lastname = lastname;
        this.address = address;
        this.dateOfBirth = dateOfBirth;
        this.isActive = isActive;
        this.trainers = trainers;

        this.username = username;
        this.password = password;
    }
    public String getFirstname() {
        return firstname;
    }
    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }
    public String getLastname() {
        return lastname;
    }
    public void setLastname(String lastname) {
        this.lastname = lastname;
    }
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }
    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }
    public Boolean getActive() {
        return isActive;
    }
    public void setActive(Boolean active) {
        isActive = active;
    }
    public List<TrainerDTO> getTrainers() {
        return trainers;
    }
    public void setTrainers(List<TrainerDTO> trainers) {
        this.trainers = trainers;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    public List<TrainingDTO> getTrainings() {
        return trainings;
    }
    public void setTrainings(List<TrainingDTO> trainings) {
        this.trainings = trainings;
    }

    public @NotBlank(groups = ChangeLogin.class) String getNewPassword() {
        return newPassword;
    }
    public void setNewPassword(@NotBlank(groups = ChangeLogin.class) String newPassword) {
        this.newPassword = newPassword;
    }
    public String getOldPassword() {
        return oldPassword;
    }
    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }


    @Override
    public String toString() {
        return "TraineeDTO{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", address='" + address + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                ", isActive=" + isActive +
                ", trainers=" + trainers +
                ", trainings=" + trainings +
                ", oldPassword='" + oldPassword + '\'' +
                ", newPassword='" + newPassword + '\'' +
                '}';
    }
}
