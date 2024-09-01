package epam.com.gymapplication.model;

import java.util.Objects;

public class Trainer extends UserBase{
    private Long id;
    private Long userId;
    private String specialization;
    private User user;

    public Trainer() {

    }

    public Trainer(Long id, Long userId, String specialization, User user) {
        this.id = id;
        this.userId = userId;
        this.specialization = specialization;
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Trainer{" +
                "id=" + id +
                ", userId=" + userId +
                ", specialization='" + specialization + '\'' +
                ", user=" + user +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Trainer trainer = (Trainer) o;
        return Objects.equals(id, trainer.id)
                && Objects.equals(userId, trainer.userId)
                && Objects.equals(specialization, trainer.specialization)
                && Objects.equals(user, trainer.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userId, specialization, user);
    }
}
