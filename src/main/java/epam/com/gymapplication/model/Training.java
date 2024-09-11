package epam.com.gymapplication.model;



import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.Objects;


@Entity
@Table(name = "training")
public class Training {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;
    @Column(name = "trainer_id")
    private Long trainerID;
    @Column(name = "trainee_id")
    private Long traineeID;
    @Column(name = "training_name", nullable = false)
    @NotNull(message = "training name can not be null")
    private String trainingName;

    @Column(name = "training_date", nullable = false)
    @NotNull(message = "training date can not be null")
    private LocalDate trainingDate;
    @Column(name = "training_duration", nullable = false)
    @NotNull(message = "training duration can not be null")
    private Integer trainingDuration;

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

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="training_type_id", nullable = false, unique = true)
    private TrainingType trainingType;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "trainee_ID", nullable = false, unique = true)
    private Trainee trainee;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "trainer_ID", nullable = false, unique = true)
    private Trainer trainer;

    public void setTrainee(Trainee trainee) {
        this.trainee = trainee;
    }

    public Trainee getTrainee() {
        return trainee;
    }
    public void setTrainer(Trainer trainer) {
        this.trainer = trainer;
    }
    public Trainer getTrainer() {
        return trainer;
    }

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
                && Objects.equals(trainingTypeID, training.trainingTypeID)
                && Objects.equals(trainingType, training.trainingType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
                id, trainerID, traineeID,
                trainingName, trainingDate, trainingDuration,
                trainingTypeID, trainingType);


    }
}
