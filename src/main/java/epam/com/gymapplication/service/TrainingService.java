package epam.com.gymapplication.service;

import epam.com.gymapplication.customexception.ServiceException;
import epam.com.gymapplication.dao.TrainingDAO;
import epam.com.gymapplication.model.Trainee;
import epam.com.gymapplication.model.Trainer;
import epam.com.gymapplication.model.Training;
import epam.com.gymapplication.model.TrainingType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;


@Service
public class TrainingService {
    private static final Logger logger = LoggerFactory.getLogger(TrainingService.class);

    @Autowired
    private TrainingDAO trainingDAO;



    public Training findTrainingListByTraineeCriteria(String username, LocalDate from, LocalDate to, String trainingTypeName) throws ServiceException {
        if (username.isEmpty() || trainingTypeName.isEmpty()) {
            logger.warn("Entity find by Training list failed");
            throw new ServiceException("Entity find training list failed, username, trainingTypeName and date are invalid");
        }
        logger.info("Find training list by trainee criteria");
        return trainingDAO.findTrainingListByTraineeCriteria(username, from, to, trainingTypeName);
    }

    public Training findTrainingListByTrainerCriteria(String username, LocalDate from, LocalDate to, String trainingTypeName) throws  ServiceException {
        if (username.isEmpty() || trainingTypeName.isEmpty()) {
            logger.warn("Entity find by Training list failed");
            throw new ServiceException("Entity find training list failed, username, trainingTypeName and date are invalid");
        }
        logger.info("Find training list by trainer criteria");
        return trainingDAO.findTrainingListByTraineeCriteria(username, from, to, trainingTypeName);
    }

    public void addTraining(Trainee trainee, Trainer trainer, TrainingType trainingType) {
        if (trainee.getAddress() == null || trainee.getAddress().isEmpty()
                || trainee.getDateOfBirth() == null || trainee.getUser() == null
                || trainer.getUser() == null || trainer.getTrainingType() == null
                || trainingType.getTrainingTypeName() == null || trainingType.getTrainingTypeName().isEmpty()) {

            logger.warn("Entity add training failed");
            throw new ServiceException("Entity add training failed, trainee and trainer are invalid");
        }

        logger.info("Add training list");
        trainingDAO.addTraining(trainee, trainer, trainingType);
    }


    @Transactional
    public void saveTraining(Training training) throws ServiceException {
        if (training.getTrainingName() == null || training.getTrainingName().isEmpty()
                || training.getTrainingDate() == null || training.getTrainingDate().toString().isEmpty()
                || training.getTrainingDuration() == null || training.getTrainingDuration().toString().isEmpty()) {

            logger.warn("Training save failed");
            throw new ServiceException("Training save failed, training is invalid");
        }
        trainingDAO.save(training);
        logger.info("Training saved");

    }

    public void updateTraining(Training training) throws ServiceException {
        if (training.getTrainingName() == null || training.getTrainingName().isEmpty()
                || training.getTrainingDate() == null || training.getTrainingDate().toString().isEmpty()
                || training.getTrainingDuration() == null || training.getTrainingDuration().toString().isEmpty()) {

            logger.warn("Training update failed");
            throw new ServiceException("Training update failed, training is invalid");
        }

        trainingDAO.update(training);
        logger.info("Training updated");

    }

    public void deleteTraining(Training training) throws ServiceException {
        if (training.getTrainingName() == null || training.getTrainingName().isEmpty()
                || training.getTrainingDate() == null || training.getTrainingDate().toString().isEmpty()
                || training.getTrainingDuration() == null || training.getTrainingDuration().toString().isEmpty()) {

            logger.warn("Training delete failed");
            throw new ServiceException("Training delete failed, training is invalid");
        }

        trainingDAO.delete(training);
        logger.info("Training deleted");

    }

    public Training findTrainingById(Long id) {
        if (id == null || id <= 0) {
            logger.warn("Training find by id failed");
            throw new ServiceException("Training find by id failed, id is invalid");
        }
        logger.info("Found training by id {} ", id);
        return trainingDAO.findById(id);
    }


    public Training findByTrainingName(String trainingName) {
        if (trainingName == null || trainingName.isEmpty()) {
            logger.warn("Training finding by name failed");
            throw new ServiceException("Finding training by name failed, training name is invalid");
        }
        logger.info("Training name found {} ", trainingName);
        return trainingDAO.findByTrainingName(trainingName);
    }

    public Training findByTrainingType(String trainingType) {
        if (trainingType == null || trainingType.isEmpty()) {
            logger.warn("Training finding by type failed");
            throw new ServiceException("Finding training by type failed, training type is invalid");
        }
        logger.info("Training type found {} ", trainingType);
        return trainingDAO.findByTrainingType(trainingType);
    }
}
