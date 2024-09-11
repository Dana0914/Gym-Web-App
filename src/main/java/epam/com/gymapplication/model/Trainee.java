package epam.com.gymapplication.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;


@Entity
@Table(name = "trainee")
public class Trainee extends UserBase {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonFormat(pattern="yyyy-MM-dd")
    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;
    private String address;

    @Column(name = "users_id", insertable = false, updatable = false)
    private Long userId;



    public Trainee() {

    }

    public Trainee(Long id, Long userId, LocalDate dateOfBirth, String address) {
        this.id = id;
        this.userId = userId;
        this.dateOfBirth = dateOfBirth;
        this.address = address;

    }

    @OneToOne
    @JoinColumn(name = "users_id", unique = true, nullable = false)
    private User user;


    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUser(User user) {
        this.user = user;
    }
    public User getUser() {
        return user;
    }

    @ManyToMany
    @JoinTable(name="trainer_trainee",
            joinColumns=@JoinColumn(name="trainer_id"),
            inverseJoinColumns=@JoinColumn(name="trainee_id"))
    private Set<Trainer> trainers = new HashSet<>();

    public Set<Trainer> getTrainers() {
        return trainers;
    }
    public void setTrainers(Set<Trainer> trainers) {
        this.trainers = trainers;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }


    @Override
    public String toString() {
        return "Trainee{" +
                "id=" + id +
                ", dateOfBirth=" + dateOfBirth +
                ", address='" + address + '\'' +
                ", userId=" + userId +
                ", user=" + user +
                ", trainers=" + trainers +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Trainee trainee = (Trainee) o;
        return Objects.equals(id, trainee.id)
                && Objects.equals(dateOfBirth, trainee.dateOfBirth)
                && Objects.equals(address, trainee.address)
                && Objects.equals(userId, trainee.userId)
                && Objects.equals(user, trainee.user)
                && Objects.equals(trainers, trainee.trainers);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, dateOfBirth, address, userId, user, trainers);
    }
}
