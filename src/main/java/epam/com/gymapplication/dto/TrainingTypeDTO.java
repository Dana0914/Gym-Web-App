package epam.com.gymapplication.dto;


import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class TrainingTypeDTO {
    private Long trainingTypeId;
    private String trainingTypeName;

    public TrainingTypeDTO() {

    }

    public TrainingTypeDTO(Long trainingTypeId, String trainingTypeName) {
        this.trainingTypeId = trainingTypeId;
        this.trainingTypeName = trainingTypeName;
    }
    public Long getTrainingTypeId() {
        return trainingTypeId;
    }
    public void setTrainingTypeId(Long trainingTypeId) {
        this.trainingTypeId = trainingTypeId;
    }
    public String getTrainingTypeName() {
        return trainingTypeName;
    }
    public void setTrainingTypeName(String trainingTypeName) {
        this.trainingTypeName = trainingTypeName;
    }

    @Override
    public String toString() {
        return "TrainingTypeDTO{" +
                "trainingTypeId=" + trainingTypeId +
                ", trainingTypeName='" + trainingTypeName + '\'' +
                '}';
    }
}
