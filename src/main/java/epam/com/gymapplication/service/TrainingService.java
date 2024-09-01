package epam.com.gymapplication.service;

import epam.com.gymapplication.customexception.DaoException;
import epam.com.gymapplication.customexception.ServiceException;
import epam.com.gymapplication.dao.impl.TrainingDAOImpl;
import epam.com.gymapplication.model.Training;
import epam.com.gymapplication.model.TrainingType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;
import java.util.Set;

@Service
public class TrainingService {
    private static final Logger logger = LoggerFactory.getLogger(TrainingService.class);
    private final TrainingDAOImpl trainingDAOImpl;

    @Autowired
    public TrainingService(TrainingDAOImpl trainingDAOImpl) {
        this.trainingDAOImpl = trainingDAOImpl;
    }

    public void saveTraining(Training training) throws ServiceException {
        try {
            trainingDAOImpl.save(training);
            logger.info("Training saved successfully {} ", training);
        } catch (DaoException e) {
            throw new ServiceException("There was a problem saving the Training");
        }
    }

    public void updateTraining(Training training) throws ServiceException {
        try {
            trainingDAOImpl.update(training);
            logger.info("Training updated successfully {} ", training);
        } catch (DaoException e) {
            throw new ServiceException("There was a problem updating the Training");
        }
    }

    public void deleteTrainingById(Long id) throws ServiceException {
        try {
            trainingDAOImpl.deleteById(id);
            logger.info("Training deleted successfully {} ", id);
        } catch (DaoException e) {
            throw new ServiceException("There was a problem deleting the Training by its id");
        }
    }

    public Optional<Training> findTrainingById(Long id) {
        logger.info("Training id found successfully {} ", id);
        return trainingDAOImpl.findById(id);
    }

    public Set<Map.Entry<Long, Training>> getAllTrainings() {
        return trainingDAOImpl.findAll();
    }

    public Optional<Training> findByTrainingName(String trainingName) {
        logger.info("Training name found successfully {} ", trainingName);
        return trainingDAOImpl.findByTrainingName(trainingName);
    }

    public Optional<Training> findByTrainingType(TrainingType trainingType) {
        logger.info("Training type found successfully {} ", trainingType);
        return trainingDAOImpl.findByTrainingType(trainingType);
    }
}
