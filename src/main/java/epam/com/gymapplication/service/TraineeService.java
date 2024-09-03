package epam.com.gymapplication.service;

import epam.com.gymapplication.customexception.DaoException;
import epam.com.gymapplication.customexception.ServiceException;
import epam.com.gymapplication.dao.impl.TraineeDAOImpl;
import epam.com.gymapplication.model.Trainee;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;
import java.util.Set;


@Service
public class TraineeService {
    private static final Logger logger = LoggerFactory.getLogger(TraineeService.class);

    @Autowired
    private TraineeDAOImpl traineeDAOImpl;


    public void saveTrainee(Trainee trainee) throws ServiceException {
        try {
            traineeDAOImpl.save(trainee);
            logger.info("Trainee saved successfully {}", trainee);
        } catch (DaoException e) {
            throw new ServiceException("There was a problem saving the Trainee");

        }
    }

    public void updateTrainee(Trainee trainee) throws ServiceException {
        try {
            traineeDAOImpl.update(trainee);
            logger.info("Trainee updated successfully {}", trainee);
        } catch (DaoException e) {
            throw new ServiceException("There was a problem updating the Trainee");
        }
    }

    public void deleteTraineeById(Long id) throws ServiceException {
        try {
            traineeDAOImpl.deleteById(id);
            logger.info("Trainee deleted successfully {}", id);
        } catch (DaoException e) {
            throw new ServiceException("There was a problem deleting Trainee by its id");

        }
    }

    public Optional<Trainee> findTraineeById(Long id)  {
        logger.info("Trainee found by id {}", id);
        return traineeDAOImpl.findById(id);
    }

    public Set<Map.Entry<Long, Trainee>> getAllTrainees() {
        return traineeDAOImpl.findAll();
    }

    public Optional<Trainee> findTraineeByFirstName(String firstName) {
        logger.info("Trainee found by name {}", firstName);
        return traineeDAOImpl.findByFirstName(firstName);
    }

    public Optional<Trainee> findByLastName(String lastName) {
        logger.info("Trainee found by lastName {}", lastName);
        return traineeDAOImpl.findByLastName(lastName);
    }
}
