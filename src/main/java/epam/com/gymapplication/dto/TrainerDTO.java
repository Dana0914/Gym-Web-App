package epam.com.gymapplication.dto;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class TrainerDTO {

    @JsonInclude(JsonInclude.Include.NON_NULL)

    private String username;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String password;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String firstname;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String lastname;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Long specialization;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty(value = "active")
    private Boolean isActive;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<TraineeDTO> trainees;

    @JsonInclude(JsonInclude.Include.NON_NULL)
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
}

