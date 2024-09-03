package epam.com.gymapplication.dao.impl;

import epam.com.gymapplication.customexception.DaoException;
import epam.com.gymapplication.dao.TrainingDAO;
import epam.com.gymapplication.model.Training;
import epam.com.gymapplication.model.TrainingType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.Optional;
import java.util.Set;


@Repository
public class TrainingDAOImpl implements TrainingDAO {
    private static final Logger logger = LoggerFactory.getLogger(TrainingDAOImpl.class);
    private Map<Long, Training> trainingStorage;


    @Autowired
    public void setTrainingStorage(Map<Long, Training> trainingStorage) {
        this.trainingStorage = trainingStorage;
    }

    @Override
    public void save(Training training) throws DaoException {
        if (training == null) {
            throw new DaoException("Training must not be null");
        }
        trainingStorage.put(training.getId(), training);
        logger.info("Training saved successfully {}", training);

    }

    @Override
    public void update(Training training) throws DaoException {
        if (training == null) {
            throw new DaoException("Training must not be null");
        }
        Optional<Training> trainingById = findById(training.getId());

        if (trainingById.isPresent()) {
            trainingStorage.put(trainingById.get().getId(), training);
            logger.info("Training updated successfully {}", training);
        }

    }

    @Override
    public Boolean deleteById(Long id) throws DaoException {
        if (id == null || id <= 0) {
            throw new DaoException("Training must not be null and it must have valid id");
        }
        Optional<Training> trainingToRemove = findById(id);
        if (trainingToRemove.isPresent()) {
            logger.info("Training deleted successfully");
            trainingStorage.remove(trainingToRemove.get().getId(), trainingToRemove.get());
            return true;
        }
        logger.info("Training was not deleted {}", trainingToRemove);
        return false;
    }

    @Override
    public Optional<Training> findById(Long id) {
        if (id == null || id <= 0) {
            logger.warn("Training id does not exist");
            return Optional.empty();
        }
        if (trainingStorage.containsKey(id)) {
            Training training = trainingStorage.get(id);
            logger.info("Training found by id {}", id);
            return Optional.of(training);
        }
        logger.warn("No Training found with id {}", id);
        return Optional.empty();
    }

    @Override
    public Set<Map.Entry<Long, Training>> findAll() throws DaoException {
        if (trainingStorage.isEmpty()) {
            throw new DaoException("No trainings found");
        }
        logger.info("Trainings found successfully");
        return trainingStorage.entrySet();

    }

    @Override
    public Optional<Training> findByTrainingName(String trainingName) {
        if (trainingName == null || trainingName.isEmpty()) {
            logger.warn("Training name does not exist");
            return Optional.empty();
        }
        logger.info("Training name found successfully {}", trainingName);
        return trainingStorage.values()
                .stream()
                .filter(existingtraining -> existingtraining.getTrainingName().equals(trainingName))
                .findFirst();
    }

    @Override
    public Optional<Training> findByTrainingType(TrainingType trainingType) {
        if (trainingType == null || trainingType.toString().isEmpty()) {
            logger.warn("Training type does not exist");
            return Optional.empty();
        }
        logger.info("Training type found successfully {}", trainingType);
        return trainingStorage.values()
                .stream()
                .filter(training -> training.getTrainingType().equals(trainingType))
                .findFirst();

    }
}
