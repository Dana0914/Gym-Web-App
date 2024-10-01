package epam.com.gymapplication.entity;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.*;


@Entity
@Table(name = "trainee")
public class Trainee {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;


    @Column(name = "date_of_birth")
    @NotNull(message = "date of birth can not be null")
    private LocalDate dateOfBirth;

    @NotNull(message = "Address can not be null")
    private String address;




    public Trainee() {

    }

    public Trainee(Long id, LocalDate dateOfBirth, String address) {
        this.id = id;
        this.dateOfBirth = dateOfBirth;
        this.address = address;

    }

    @OneToOne(cascade = {CascadeType.MERGE, CascadeType.REMOVE}, orphanRemoval = true)
    @JoinColumn(name = "users_id")
    private User user;

    public void setUser(User user) {
        this.user = user;
    }
    public User getUser() {
        return user;
    }

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name="trainer_trainee",
            joinColumns=@JoinColumn(name="trainer_id"),
            inverseJoinColumns=@JoinColumn(name="trainee_id"))
    private List<Trainer> trainers = new ArrayList<>();


    public List<Trainer> getTrainers() {
        return trainers;
    }
    public void setTrainers(List<Trainer> trainers) {
        this.trainers = trainers;
    }

    @OneToMany(mappedBy = "trainee", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<Training> trainings = new ArrayList<>();

    public List<Training> getTrainings() {
        return trainings;
    }
    public void setTrainings(List<Training> trainings) {
        this.trainings = trainings;
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Trainee trainee = (Trainee) o;
        return Objects.equals(id, trainee.id)
                && Objects.equals(dateOfBirth, trainee.dateOfBirth)
                && Objects.equals(address, trainee.address)
                && Objects.equals(user, trainee.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, dateOfBirth, address, user);
    }

    @Override
    public String toString() {
        return "Trainee{" +
                "id=" + id +
                ", dateOfBirth=" + dateOfBirth +
                ", address='" + address + '\'' +
                ", user=" + user +
                '}';
    }
}
