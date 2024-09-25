package epam.com.gymapplication.dto;


import epam.com.gymapplication.entity.TrainingType;

public class TrainerRequestDTO {
    private String firstname;
    private String lastname;
    private Long specialization;

    public TrainerRequestDTO() {

    }
    public TrainerRequestDTO(String firstname, String lastname, Long specialization) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.specialization = specialization;
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

    public Long getSpecialization() {
        return specialization;
    }

    public void setSpecialization(Long specialization) {
        this.specialization = specialization;
    }
}

