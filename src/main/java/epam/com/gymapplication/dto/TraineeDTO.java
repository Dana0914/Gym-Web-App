package epam.com.gymapplication.dto;



import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;


import java.time.LocalDate;
import java.util.List;

public class TraineeDTO {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String username;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String password;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String firstname;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String lastname;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String address;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate dateOfBirth;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty(value = "active")
    private Boolean isActive;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<TrainerDTO> trainers;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<TrainingDTO> trainings;


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
}
