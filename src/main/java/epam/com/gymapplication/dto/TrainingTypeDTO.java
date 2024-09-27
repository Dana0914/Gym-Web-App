package epam.com.gymapplication.dto;


import com.fasterxml.jackson.annotation.JsonInclude;

public class TrainingTypeDTO {
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Long trainingTypeId;
    @JsonInclude(JsonInclude.Include.NON_NULL)
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

}
