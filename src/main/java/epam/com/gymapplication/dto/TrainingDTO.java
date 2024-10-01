package epam.com.gymapplication.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import epam.com.gymapplication.entity.Trainee;
import epam.com.gymapplication.entity.Trainer;


import java.time.LocalDate;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class TrainingDTO {

    private String trainingName;

    private LocalDate from;

    private LocalDate to;

    private Integer trainingDuration;

    private String trainingType;


    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate trainingDate;

    private Trainer trainer;

    private Trainee trainee;

    private String trainerName;

    private String traineeName;

    @JsonProperty(value = "username")
    private String traineeUsername;

    private String trainerUsername;


    public TrainingDTO() {

    }

    public TrainingDTO(String trainingName, LocalDate from, LocalDate to, Integer trainingDuration,
                       String trainingType, Trainer trainer, Trainee trainee) {
        this.trainingName = trainingName;
        this.trainingType = trainingType;
        this.from = from;
        this.to = to;
        this.trainingDuration = trainingDuration;
        this.trainer = trainer;
        this.trainee = trainee;

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
    public String getTraineeUsername() {
        return traineeUsername;
    }
    public void setTraineeUsername(String traineeUsername) {
        this.traineeUsername = traineeUsername;
    }

    public String getTrainerUsername() {
        return trainerUsername;
    }
    public void setTrainerUsername(String trainerUsername) {
        this.trainerUsername = trainerUsername;
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
}
