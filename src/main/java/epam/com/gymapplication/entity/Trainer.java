package epam.com.gymapplication.entity;


import jakarta.persistence.*;


import java.util.*;


@Entity
@Table(name = "trainer")
public class Trainer {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    public Trainer() {

    }

    public Trainer(Long id) {
        this.id = id;
    }


    @Column(name = "specialization")
    private Long specialization;


    @OneToOne(cascade = {CascadeType.MERGE, CascadeType.REMOVE}, orphanRemoval = true)
    @JoinColumn(name = "users_id")
    private User user;

    public void setSpecialization(Long specialization) {
        this.specialization = specialization;
    }
    public Long getSpecialization() {
        return specialization;
    }

    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }


    @ManyToMany(mappedBy = "trainers", fetch = FetchType.EAGER)
    private List<Trainee> trainees = new ArrayList<>();

    public void setTrainees(List<Trainee> trainees) {
        this.trainees = trainees;
    }
    public List<Trainee> getTrainees() {
        return trainees;
    }

    @OneToMany(mappedBy = "trainer", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private Set<Training> trainings = new HashSet<>();

    public Set<Training> getTrainings() {
        return trainings;
    }

    public void setTrainings(Set<Training> trainings) {
        this.trainings = trainings;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Trainer{" +
                "id=" + id +
                ", specialization=" + specialization +
                ", user=" + user +
                ", trainees=" + trainees +
                ", trainings=" + trainings +
                '}';
    }
}
