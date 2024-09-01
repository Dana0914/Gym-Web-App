package epam.com.gymapplication.dao;

import epam.com.gymapplication.customexception.DaoException;
import epam.com.gymapplication.model.Training;
import epam.com.gymapplication.model.TrainingType;

import java.util.Map;
import java.util.Optional;
import java.util.Set;

public interface TrainingDAO {
    void save(Training training) throws DaoException;
    void update(Training training) throws DaoException;
    Boolean deleteById(Long id) throws DaoException;
    Optional<Training> findById(Long id);
    Set<Map.Entry<Long, Training>> findAll() throws DaoException;
    Optional<Training> findByTrainingName(String trainingName);
    Optional<Training> findByTrainingType(TrainingType trainingType);
}
