package epam.com.gymapplication.dao;


import epam.com.gymapplication.model.Training;

import java.util.List;
import java.util.Optional;



public interface TrainingDAO {

    void save(Training training);
    void update(Training training);
    void deleteById(Long id);
    List<Training> findAll();
    Optional<Training> findById(Long id);
    Optional<Training> findByTrainingName(String trainingName);
    Optional<Training> findByTrainingType(String trainingType);

}
