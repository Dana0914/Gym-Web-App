package epam.com.gymapplication.entity;



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

    @Column(name = "training_name")
    @NotNull(message = "training name can not be null")
    private String trainingName;

    @Column(name = "training_date")
    @NotNull(message = "training date can not be null")
    private LocalDate trainingDate;

    @Column(name = "training_duration")
    @NotNull(message = "training duration can not be null")
    private Integer trainingDuration;

    public Training() {

    }

    public Training(Long id,
                    String trainingName,
                    LocalDate trainingDate,
                    Integer trainingDuration) {

        this.id = id;
        this.trainingName = trainingName;
        this.trainingDate = trainingDate;
        this.trainingDuration = trainingDuration;
    }

    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.MERGE, CascadeType.REMOVE})
    @JoinColumn(name="training_type_id")
    private TrainingType trainingType;

    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.MERGE, CascadeType.REMOVE})
    @JoinColumn(name = "trainee_ID")
    private Trainee trainee;

    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.MERGE, CascadeType.REMOVE})
    @JoinColumn(name = "trainer_ID")
    private Trainer trainer;

    public Trainee getTrainee() {
        return trainee;
    }

    public void setTrainee(Trainee trainee) {
        this.trainee = trainee;
    }

    public Trainer getTrainer() {
        return trainer;
    }

    public void setTrainer(Trainer trainer) {
        this.trainer = trainer;
    }

    public TrainingType getTrainingType() {
        return trainingType;
    }

    public void setTrainingType(TrainingType trainingType) {
        this.trainingType = trainingType;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
                ", trainingName='" + trainingName + '\'' +
                ", trainingDate=" + trainingDate +
                ", trainingDuration=" + trainingDuration +
                ", trainingType=" + trainingType +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Training training = (Training) o;
        return Objects.equals(id, training.id)
                && Objects.equals(trainingName, training.trainingName)
                && Objects.equals(trainingDate, training.trainingDate)
                && Objects.equals(trainingDuration, training.trainingDuration)
                && Objects.equals(trainingType, training.trainingType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, trainingName, trainingDate, trainingDuration, trainingType, trainee, trainer);
    }
}
