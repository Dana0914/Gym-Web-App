package epam.com.gymapplication.dto;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class TrainerDTO {

    public interface ActivateDeactivateTrainer {}
    public interface GetTrainerProfile{}
    public interface UnassignedTrainersOnTrainee{}
    public interface UpdateTrainerProfile{}
    @NotBlank(groups = {Login.class, GetTrainerProfile.class, UpdateTrainerProfile.class,
    UnassignedTrainersOnTrainee.class, ActivateDeactivateTrainer.class})
    private String username;
    @NotBlank(groups = Login.class)
    private String password;
    @NotBlank(groups = {Registration.class, UpdateTrainerProfile.class})
    private String firstname;
    @NotBlank(groups = {Registration.class, UpdateTrainerProfile.class})
    private String lastname;
    @NotBlank(groups = Registration.class)
    private Long specialization;

    @JsonProperty(value = "active")
    @NotBlank(groups = {UpdateTrainerProfile.class, ActivateDeactivateTrainer.class})
    private Boolean isActive;
    private List<TraineeDTO> trainees;
    private List<TrainingDTO> trainings;

    @NotBlank(groups = ChangeLogin.class)
    private String oldPassword;
    @NotBlank(groups = ChangeLogin.class)
    private String newPassword;

    public TrainerDTO() {

    }

    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
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

    public Long getSpecialization() {
        return specialization;
    }

    public void setSpecialization(Long specialization) {
        this.specialization = specialization;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    public List<TraineeDTO> getTrainees() {
        return trainees;
    }

    public void setTrainees(List<TraineeDTO> trainees) {
        this.trainees = trainees;
    }

    public Boolean getActive() {
        return isActive;
    }
    public void setActive(Boolean active) {
        isActive = active;
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
        return "TrainerDTO{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", specialization=" + specialization +
                ", isActive=" + isActive +
                ", trainees=" + trainees +
                ", trainings=" + trainings +
                ", oldPassword='" + oldPassword + '\'' +
                ", newPassword='" + newPassword + '\'' +
                '}';
    }
}

