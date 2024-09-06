package epam.com.gymapplication.dao;

import epam.com.gymapplication.model.Training;
import epam.com.gymapplication.model.TrainingType;

import java.util.Map;
import java.util.Optional;
import java.util.Set;



public interface TrainingDAO {

    void save(Training training);
    void update(Training training);
    void deleteById(Long id);
    Optional<Training> findById(Long id);
    Set<Map.Entry<Long, Training>> findAll();
    Optional<Training> findByTrainingName(String trainingName);
    Optional<Training> findByTrainingType(TrainingType trainingType);

}
