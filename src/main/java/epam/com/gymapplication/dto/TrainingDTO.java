package epam.com.gymapplication.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import epam.com.gymapplication.entity.ActionType;
import epam.com.gymapplication.entity.Trainee;
import epam.com.gymapplication.entity.Trainer;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;



@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class TrainingDTO {

    public TrainingDTO() {

    }

    public interface AddTraining{}
    public interface AddTrainersTrainingWorkload{}

    @NotNull(groups = {AddTraining.class})
    @NotBlank(groups = {AddTraining.class})
    private String traineeUsername;

    @NotBlank(groups = {AddTraining.class, AddTrainersTrainingWorkload.class})
    @NotNull(groups = {AddTraining.class, AddTrainersTrainingWorkload.class})
    private String trainerUsername;

    @NotBlank(groups = {AddTrainersTrainingWorkload.class})
    @NotNull(groups = {AddTrainersTrainingWorkload.class})
    private String trainerFirstname;

    @NotBlank(groups = {AddTrainersTrainingWorkload.class})
    @NotNull(groups = {AddTrainersTrainingWorkload.class})
    private String trainerLastname;


    @NotNull(groups = {AddTrainersTrainingWorkload.class})
    @JsonProperty(value = "isActive")
    @Column(name = "is_active")
    private Boolean isActive;

    @NotBlank(groups = {AddTraining.class})
    @NotNull(groups = {AddTraining.class})
    private String trainingName;

    private LocalDate from;

    private LocalDate to;

    @NotNull(groups = {AddTraining.class, AddTrainersTrainingWorkload.class})
    @JsonProperty(value = "trainingDuration")
    @Column(name = "training_duration")
    private Integer trainingDuration;

    private String trainingType;


    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @NotNull(groups = {AddTraining.class, AddTrainersTrainingWorkload.class})
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate trainingDate;

    private Trainer trainer;

    private Trainee trainee;


    private String trainerName;

    private String traineeName;


    @NotNull(groups = {AddTrainersTrainingWorkload.class})
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private ActionType actionType;

    public TrainingDTO(String trainerUsername, String trainerFirstname,
                       String trainerLastname, Boolean isActive,
                       Integer trainingDuration, LocalDate trainingDate,
                       ActionType actionType) {
        this.trainerUsername = trainerUsername;
        this.trainerFirstname = trainerFirstname;
        this.trainerLastname = trainerLastname;
        this.isActive = isActive;
        this.trainingDuration = trainingDuration;
        this.trainingDate = trainingDate;
        this.actionType = actionType;

    }


    public TrainingDTO(String trainerUsername,
                       String traineeUsername,
                       String trainingName,
                       LocalDate trainingDate,
                       Integer trainingDuration) {
        this.trainerUsername = trainerUsername;
        this.traineeUsername = traineeUsername;
        this.trainingName = trainingName;
        this.trainingDate = trainingDate;
        this.trainingDuration = trainingDuration;
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

    public String getTrainerFirstname() {
        return trainerFirstname;
    }
    public void setTrainerFirstname(String trainerFirstname) {
        this.trainerFirstname = trainerFirstname;
    }
    public String getTrainerLastname() {
        return trainerLastname;
    }
    public void setTrainerLastname(String trainerLastname) {
        this.trainerLastname = trainerLastname;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }
    public Boolean getIsActive() {
        return isActive;
    }
    public void setTrainingName(String trainingName) {
        this.trainingName = trainingName;
    }
    public String getTrainingName() {
        return trainingName;
    }
    public void setTrainingDate(LocalDate trainingDate) {
        this.trainingDate = trainingDate;
    }
    public LocalDate getTrainingDate() {
        return trainingDate;
    }
    public void setTrainer(Trainer trainer) {
        this.trainer = trainer;
    }
    public Trainer getTrainer() {
        return trainer;
    }
    public void setTraineeName(String traineeName) {
        this.traineeName = traineeName;
    }
    public String getTraineeName() {
        return traineeName;
    }
    public void setTrainerName(String trainerName) {
        this.trainerName = trainerName;
    }
    public String getTrainerName() {
        return trainerName;
    }

    public void setTrainee(Trainee trainee) {
        this.trainee = trainee;
    }
    public Trainee getTrainee() {
        return trainee;
    }

    public Boolean getActive() {
        return isActive;
    }
    public void setActive(Boolean active) {
        isActive = active;
    }

    public Integer getTrainingDuration() {
        return trainingDuration;
    }
    public void setTrainingDuration(Integer trainingDuration) {
        this.trainingDuration = trainingDuration;
    }

    public void setTrainingType(String trainingType) {
        this.trainingType = trainingType;
    }
    public String getTrainingType() {
        return trainingType;
    }

    public ActionType getActionType() {
        return actionType;
    }
    public void setActionType(ActionType actionType) {
        this.actionType = actionType;
    }

    public void setTrainerUsername(String trainerUsername) {
        this.trainerUsername = trainerUsername;
    }
    public String getTrainerUsername() {
        return trainerUsername;
    }
    public String getTraineeUsername() {
        return traineeUsername;
    }

    public void setTraineeUsername(String traineeUsername) {
        this.traineeUsername = traineeUsername;
    }
}
