package epam.com.gymapplication.dto;

import epam.com.gymapplication.entity.Trainer;

import java.time.LocalDate;
import java.util.Set;

public class TraineeProfileResponseDTO {
    private String firstname;
    private String lastname;
    private String address;
    private LocalDate dateOfBirth;
    private boolean isActive;
    private Set<Trainer> trainers;

    public TraineeProfileResponseDTO() {

    }
    public TraineeProfileResponseDTO(String firstname, String lastname, String address,
                                     LocalDate dateOfBirth, boolean isActive, Set<Trainer> trainers) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.address = address;
        this.dateOfBirth = dateOfBirth;
        this.isActive = isActive;
        this.trainers = trainers;
    }
    public String getFirstname() {
        return firstname;
    }
    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }
    public String getLastname() {
        return lastname;
    }
    public void setLastname(String lastname) {
        this.lastname = lastname;
    }
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }
    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }
    public boolean isActive() {
        return isActive;
    }
    public void setActive(boolean active) {
        isActive = active;
    }
    public Set<Trainer> getTrainers() {
        return trainers;
    }
    public void setTrainers(Set<Trainer> trainers) {
        this.trainers = trainers;
    }
}
