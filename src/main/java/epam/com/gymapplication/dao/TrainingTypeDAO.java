package epam.com.gymapplication.dao;

import epam.com.gymapplication.model.TrainingType;

import java.util.List;

public interface TrainingTypeDAO {
    TrainingType findById(Long id);
    void save(TrainingType trainingType);
    void delete(TrainingType trainingType);
    void update(TrainingType trainingType);
    List<TrainingType> findAll();
    TrainingType findByTrainingTypeName(String trainingTypeName);
}
