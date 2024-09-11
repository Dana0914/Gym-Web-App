package epam.com.gymapplication.service;

import epam.com.gymapplication.customexception.ServiceException;
import epam.com.gymapplication.dao.TrainingDAO;
import epam.com.gymapplication.model.Training;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.Optional;

@Service
public class TrainingService {
    private static final Logger logger = LoggerFactory.getLogger(TrainingService.class);

    @Autowired
    private TrainingDAO trainingDAO;

    @Transactional
    public void saveTraining(Training training) throws ServiceException {
        if (training.getTrainingName() == null || training.getTrainingName().isEmpty()
                || training.getTrainingType() == null || training.getTrainingType().toString().isEmpty()
                || training.getTrainingDate() == null || training.getTrainingDate().toString().isEmpty()
                || training.getTrainingDuration() == null || training.getTrainingDuration().toString().isEmpty()
                || training.getTrainerID() == null || training.getTrainerID() <= 0
                || training.getTraineeID() == null || training.getTraineeID() <= 0) {

            logger.warn("Training save failed");
            throw new ServiceException("Training save failed, training is invalid");
        }
        trainingDAO.save(training);
        logger.info("Training saved {} ", training);

    }

    public void updateTraining(Training training) throws ServiceException {
        if (training.getTrainingName() == null || training.getTrainingName().isEmpty()
                || training.getTrainingType() == null || training.getTrainingType().toString().isEmpty()
                || training.getTrainingDate() == null || training.getTrainingDate().toString().isEmpty()
                || training.getTrainingDuration() == null || training.getTrainingDuration().toString().isEmpty()
                || training.getTrainerID() == null || training.getTrainerID() <= 0
                || training.getTraineeID() == null || training.getTraineeID() <= 0) {

            logger.warn("Training update failed");
            throw new ServiceException("Training update failed, training is invalid");
        }

        trainingDAO.update(training);
        logger.info("Training updated {} ", training);

    }

    public void deleteTrainingById(Long id) throws ServiceException {
        if (id == null || id <= 0) {
            logger.warn("Training delete by id failed");
            throw new ServiceException("Removing training by id failed, training id is invalid");
        }

        trainingDAO.deleteById(id);
        logger.info("Training deleted {} ", id);

    }

    public Optional<Training> findTrainingById(Long id) {
        if (id == null || id <= 0) {
            logger.warn("Training find by id failed");
            throw new ServiceException("Training find by id failed, id is invalid");
        }
        logger.info("Found training by id {} ", id);
        Optional<Training> byId = trainingDAO.findById(id);
        return Optional.of(byId.orElseThrow());
    }


    public Optional<Training> findByTrainingName(String trainingName) {
        if (trainingName == null || trainingName.isEmpty()) {
            logger.warn("Training finding by name failed");
            throw new ServiceException("Finding training by name failed, training name is invalid");
        }
        logger.info("Training name found {} ", trainingName);
        return trainingDAO.findByTrainingName(trainingName);
    }

    public Optional<Training> findByTrainingType(String trainingType) {
        if (trainingType == null || trainingType.isEmpty()) {
            logger.warn("Training finding by type failed");
            throw new ServiceException("Finding training by type failed, training type is invalid");
        }
        logger.info("Training type found {} ", trainingType);
        return trainingDAO.findByTrainingType(trainingType);
    }
}
