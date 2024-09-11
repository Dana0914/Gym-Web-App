package epam.com.gymapplication.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.Objects;


@Entity
@Table(name = "training")
public class Training extends UserBase {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    Long id;
    @Column(name = "trainer_id")
    Long trainerID;
    @Column(name = "trainee_id")
    Long traineeID;
    @Column(name = "training_name")
    String trainingName;

    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonFormat(pattern="yyyy-MM-dd")
    @Column(name = "training_date")
    LocalDate trainingDate;
    @Column(name = "training_duration")
    Integer trainingDuration;

    @JsonProperty(value = "trainingTypeId")
    @Column(name = "training_type_id", insertable = false, updatable = false)
    private Long trainingTypeID;

    public Training() {

    }

    public Training(Long id,
                    Long trainerID,
                    Long traineeID,
                    String trainingName,
                    LocalDate trainingDate,
                    Integer trainingDuration,
                    Long trainingTypeID) {

        this.id = id;
        this.trainerID = trainerID;
        this.traineeID = traineeID;
        this.trainingName = trainingName;
        this.trainingDate = trainingDate;
        this.trainingDuration = trainingDuration;
        this.trainingTypeID = trainingTypeID;
    }

    @ManyToOne
    @JoinColumn(name="training_type_id", nullable = false, unique = true)
    private TrainingType trainingType;

    public TrainingType getTrainingType() {
        return trainingType;
    }

    public void setTrainingType(TrainingType trainingType) {
        this.trainingType = trainingType;
    }

    public Long getTrainingTypeID() {
        return trainingTypeID;
    }

    public void setTrainingTypeID(Long trainingTypeID) {
        this.trainingTypeID = trainingTypeID;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTrainerID() {
        return trainerID;
    }

    public void setTrainerID(Long trainerID) {
        this.trainerID = trainerID;
    }

    public Long getTraineeID() {
        return traineeID;
    }

    public void setTraineeID(Long traineeID) {
        this.traineeID = traineeID;
    }

    public String getTrainingName() {
        return trainingName;
    }

    public void setTrainingName(String trainingName) {
        this.trainingName = trainingName;
    }


    public LocalDate getTrainingDate() {
        return trainingDate;
    }

    public void setTrainingDate(LocalDate trainingDate) {
        this.trainingDate = trainingDate;
    }

    public Integer getTrainingDuration() {
        return trainingDuration;
    }

    public void setTrainingDuration(Integer trainingDuration) {
        this.trainingDuration = trainingDuration;
    }


    @Override
    public String toString() {
        return "Training{" +
                "id=" + id +
                ", trainerID=" + trainerID +
                ", traineeID=" + traineeID +
                ", trainingName='" + trainingName + '\'' +
                ", trainingDate=" + trainingDate +
                ", trainingDuration=" + trainingDuration +
                ", trainingType=" + trainingType +
                ", trainingTypeID=" + trainingTypeID +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Training training = (Training) o;
        return Objects.equals(id, training.id)
                && Objects.equals(trainerID, training.trainerID)
                && Objects.equals(traineeID, training.traineeID)
                && Objects.equals(trainingName, training.trainingName)
                && Objects.equals(trainingDate, training.trainingDate)
                && Objects.equals(trainingDuration, training.trainingDuration)
                && Objects.equals(trainingType, training.trainingType)
                && Objects.equals(trainingTypeID, training.trainingTypeID);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
                id, trainerID, traineeID,
                trainingName, trainingDate, trainingDuration,
                trainingType, trainingTypeID);
    }
}
