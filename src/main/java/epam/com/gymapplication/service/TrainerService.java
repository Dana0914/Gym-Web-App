package epam.com.gymapplication.service;

import epam.com.gymapplication.customexception.DaoException;
import epam.com.gymapplication.customexception.ServiceException;
import epam.com.gymapplication.dao.impl.TrainerDAOImpl;
import epam.com.gymapplication.model.Trainer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;
import java.util.Set;

@Service
public class TrainerService {
    private static final Logger logger = LoggerFactory.getLogger(TrainerService.class);
    private final TrainerDAOImpl trainerDAOImpl;

    @Autowired
    public TrainerService(TrainerDAOImpl trainerDAOImpl) {
        this.trainerDAOImpl = trainerDAOImpl;
    }

    public void saveTrainer(Trainer trainer) throws ServiceException {
        try {
            trainerDAOImpl.save(trainer);
            logger.info("Trainer saved successfully {} ", trainer);
        } catch (DaoException e) {
            throw new ServiceException("There was a problem saving the Trainer");
        }
    }

    public void updateTrainer(Trainer trainer) throws ServiceException {
        try {
            trainerDAOImpl.update(trainer);
            logger.info("Trainer updated successfully {} ", trainer);
        } catch (DaoException e) {
            throw new ServiceException("There was a problem updating the Trainer");
        }
    }

    public void deleteTrainerById(Long id) throws ServiceException {
        try {
            trainerDAOImpl.deleteById(id);
            logger.info("Trainer deleted successfully {} ", id);
        } catch (DaoException e) {
            throw new ServiceException("There was a problem deleting the Trainer by its id");
        }
    }

    public Optional<Trainer> findTrainerById(Long id) {
        logger.info("Found trainer by id {} ", id);
        return trainerDAOImpl.findById(id);
    }

    public Set<Map.Entry<Long, Trainer>> getAllTrainers() {
        return trainerDAOImpl.findAll();
    }

    public  Optional<Trainer> findByFirstName(String firstName) {
        logger.info("Found trainer by name {} ", firstName);
        return trainerDAOImpl.findByFirstName(firstName);
    }

    public Optional<Trainer> findByLastName(String lastName) {
        logger.info("Found trainer by lastName {} ", lastName);
        return trainerDAOImpl.findByLastName(lastName);
    }

    public Optional<Trainer> findBySpecialization(String specialization) {
        logger.info("Found trainer by specialization {} ", specialization);
        return trainerDAOImpl.findBySpecialization(specialization);
    }
}
