package epam.com.gymapplication.dto;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)

public class TrainerDTO {

    private String username;
    private String password;
    private String firstname;
    private String lastname;
    private Long specialization;
    @JsonProperty(value = "active")
    private Boolean isActive;
    private List<TraineeDTO> trainees;
    private List<TrainingDTO> trainings;

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
                '}';
    }
}

