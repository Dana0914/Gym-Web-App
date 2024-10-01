package epam.com.gymapplication.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;


import java.util.Objects;


@Entity
@Table(name = "training_type")
public class TrainingType {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @Column(name = "training_type_name")
    @NotNull(message = "training type name can not be null")
    private String trainingTypeName;


    public TrainingType() {

    }

    public TrainingType(Long id, String trainingTypeName) {
        this.id = id;
        this.trainingTypeName = trainingTypeName;
    }


    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setTrainingTypeName(String trainingTypeName) {
        this.trainingTypeName = trainingTypeName;
    }

    public String getTrainingTypeName() {
        return trainingTypeName;
    }

    @Override
    public String toString() {
        return "TrainingType{" +
                "id=" + id +
                ", trainingTypeName='" + trainingTypeName + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TrainingType that = (TrainingType) o;
        return Objects.equals(id, that.id)
                && Objects.equals(trainingTypeName, that.trainingTypeName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, trainingTypeName);
    }
}
