package epam.com.gymapplication.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import epam.com.gymapplication.entity.Trainee;
import epam.com.gymapplication.entity.Trainer;
import epam.com.gymapplication.entity.TrainingType;

import java.time.LocalDate;

public class TrainingDTO {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String trainingName;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private LocalDate from;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private LocalDate to;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer trainingDuration;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private TrainingType trainingType;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private LocalDate trainingDate;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Trainer trainer;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Trainee trainee;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String trainingTypeName;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String trainerName;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String traineeName;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String traineeUsername;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String trainerUsername;


    public TrainingDTO() {

    }

    public TrainingDTO(String trainingName, LocalDate from, LocalDate to, Integer trainingDuration,
                       TrainingType trainingType, Trainer trainer, Trainee trainee) {
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

    public TrainingType getTrainingType() {
        return trainingType;
    }
    public void setTrainingType(TrainingType trainingType) {
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
    public String getTrainingTypeName() {
        return trainingTypeName;
    }
    public void setTrainingTypeName(String trainingTypeName) {
        this.trainingTypeName = trainingTypeName;
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
