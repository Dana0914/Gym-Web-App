package epam.com.gymapplication.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import epam.com.gymapplication.entity.Trainee;
import epam.com.gymapplication.entity.Trainer;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;


import java.time.LocalDate;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class TrainingDTO {

    public interface AddTraining{}

    private String username;

    @NotBlank(groups = {AddTraining.class})
    private String trainingName;

    private LocalDate from;

    private LocalDate to;

    @NotNull(groups = {AddTraining.class})
    private Integer trainingDuration;

    private String trainingType;

    @NotNull(groups = {AddTraining.class})
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate trainingDate;

    private Trainer trainer;

    private Trainee trainee;


    private String trainerName;

    private String traineeName;

    @NotBlank(groups = {AddTraining.class})
    private String traineeUsername;
    @NotBlank(groups = {AddTraining.class})
    private String trainerUsername;



    public TrainingDTO() {

    }

    public TrainingDTO(String trainingName, LocalDate from, LocalDate to, Integer trainingDuration,
                       String trainingType, Trainer trainer, Trainee trainee,
                       String trainerUsername, String traineeUsername) {
        this.trainingName = trainingName;
        this.trainingType = trainingType;
        this.from = from;
        this.to = to;
        this.trainingDuration = trainingDuration;
        this.trainer = trainer;
        this.trainee = trainee;
        this.trainerUsername = trainerUsername;
        this.traineeUsername = traineeUsername;

    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public String getTrainingName() {
        return trainingName;
    }
    public void setTrainingName(String trainingName) {
        this.trainingName = trainingName;
    }
    public LocalDate getFrom() {
        return from;
    }
    public void setFrom(LocalDate from) {
        this.from = from;
    }
    public LocalDate getTo() {
        return to;
    }
    public void setTo(LocalDate to) {
        this.to = to;
    }

    public Integer getTrainingDuration() {
        return trainingDuration;
    }
    public void setTrainingDuration(Integer trainingDuration) {
        this.trainingDuration = trainingDuration;
    }

    public String getTrainingType() {
        return trainingType;
    }
    public void setTrainingType(String trainingType) {
        this.trainingType = trainingType;
    }

    public Trainer getTrainer() {
        return trainer;
    }
    public void setTrainer(Trainer trainer) {
        this.trainer = trainer;
    }
    public Trainee getTrainee() {
        return trainee;
    }
    public void setTrainee(Trainee trainee) {
        this.trainee = trainee;
    }
    public String getTrainerName() {
        return trainerName;
    }
    public void setTrainerName(String trainerName) {
        this.trainerName = trainerName;
    }

    public String getTraineeName() {
        return traineeName;
    }
    public void setTraineeName(String traineeName) {
        this.traineeName = traineeName;
    }

    public LocalDate getTrainingDate() {
        return trainingDate;
    }
    public void setTrainingDate(LocalDate trainingDate) {
        this.trainingDate = trainingDate;
    }

    public @NotBlank(groups = {AddTraining.class}) String getTrainerUsername() {
        return trainerUsername;
    }

    public @NotBlank(groups = {AddTraining.class}) String getTraineeUsername() {
        return traineeUsername;
    }

    public void setTraineeUsername(@NotBlank(groups = {AddTraining.class}) String traineeUsername) {
        this.traineeUsername = traineeUsername;
    }

    public void setTrainerUsername(@NotBlank(groups = {AddTraining.class}) String trainerUsername) {
        this.trainerUsername = trainerUsername;
    }

    @Override
    public String toString() {
        return "TrainingDTO{" +
                ", trainingName='" + trainingName + '\'' +
                ", from=" + from +
                ", to=" + to +
                ", trainingDuration=" + trainingDuration +
                ", trainingType='" + trainingType + '\'' +
                ", trainingDate=" + trainingDate +
                ", trainer=" + trainer +
                ", trainee=" + trainee +
                ", trainerName='" + trainerName + '\'' +
                ", traineeName='" + traineeName + '\'' +
                ", traineeUsername='" + traineeUsername + '\'' +
                ", trainerUsername='" + trainerUsername + '\'' +
                '}';
    }
}
