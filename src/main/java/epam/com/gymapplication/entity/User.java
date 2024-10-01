package epam.com.gymapplication.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.util.Objects;


@Entity
@Table(name = "users")
public class User {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;
    @NotNull(message = "firstname can not be null")
    private String firstname;

    @NotNull(message = "lastname can not be null")
    private String lastname;

    @NotNull(message = "username can not be null")
    private String username;

    @NotNull(message = "password can not be null")
    private String password;

    @Column(name = "is_active")
    @NotNull(message = "isActive switch can not be null")
    private Boolean isActive;

    public User() {

    }

    public User(String firstname, String lastname,
                String username, String password,
                boolean isActive) {

        this.firstname = firstname;
        this.lastname = lastname;
        this.username = username;
        this.password = password;
        this.isActive = isActive;
    }

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Trainee trainee;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstname;
    }

    public void setFirstName(String firstname) {
        this.firstname = firstname;
    }

    public String getLastName() {
        return lastname;
    }

    public void setLastName(String lastname) {
        this.lastname = lastname;
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getActive() {
        return isActive;
    }
    public void setActive(Boolean isActive) {
        this.isActive = isActive;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return isActive == user.isActive
                && Objects.equals(id, user.id)
                && Objects.equals(firstname, user.firstname)
                && Objects.equals(lastname, user.lastname)
                && Objects.equals(username, user.username)
                && Objects.equals(password, user.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstname, lastname, username, password, isActive);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", isActive=" + isActive +
                '}';
    }
}
