package epam.com.gymapplication.service;


import epam.com.gymapplication.customexception.ServiceException;
import epam.com.gymapplication.dao.TrainingTypeDAO;
import epam.com.gymapplication.model.TrainingType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TrainingTypeService {
    private static final Logger logger = LoggerFactory.getLogger(TrainingType.class);

    @Autowired
    private TrainingTypeDAO trainingTypeDAO;


    public void save(TrainingType trainingType) throws ServiceException {
        if (trainingType.getTrainingTypeName().isEmpty()
                || trainingType.getTrainingTypeName() == null) {
            logger.warn("Entity save failed");
            throw new ServiceException("Training type save failed, entity does not exist");
        }
        logger.info("Entity saved {} ", trainingType);
        trainingTypeDAO.save(trainingType);
    }

    public void update(TrainingType trainingType) throws ServiceException {
        if (trainingType.getTrainingTypeName().isEmpty()
                || trainingType.getTrainingTypeName() == null) {
            logger.warn("Entity update failed");
            throw new ServiceException("Entity update failed, entity does not exist");
        }
        logger.info("Entity updated {} ", trainingType);
        trainingTypeDAO.update(trainingType);

    }

    public void delete(TrainingType trainingType) throws ServiceException {
        if (trainingType.getTrainingTypeName().isEmpty()
                || trainingType.getTrainingTypeName() == null) {
            logger.warn("Entity delete failed");
            throw new ServiceException("Entity delete failed, entity does not exist");
        }
        logger.info("Entity deleted {} ", trainingType);
        trainingTypeDAO.delete(trainingType);
    }

    public TrainingType findById(Long id) throws ServiceException {
        if (id == null || id <= 0) {
            logger.warn("Entity find id failed");
            throw new ServiceException("Entity find by id failed, id is invalid");
        }
        logger.info("Entity id found {} ", id);
        return trainingTypeDAO.findById(id);
    }

    public TrainingType findByTrainingTypeName(String trainingType) throws ServiceException{
        if (trainingType == null || trainingType.isEmpty()) {
            logger.warn("Entity find by name failed");
            throw new ServiceException("Entity find by name failed, name is invalid");
        }
        logger.info("Entity found by name {} ", trainingType);
        return trainingTypeDAO.findByTrainingTypeName(trainingType);
    }




}
