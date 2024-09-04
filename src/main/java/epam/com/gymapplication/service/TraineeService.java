package epam.com.gymapplication.service;

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
        if (trainee.getId() == null || trainee.getId() <= 0
        || trainee.getUser().getId() == null || trainee.getUser().getId() <= 0
                || trainee.getUser().getFirstName() == null
                || trainee.getUser().getFirstName().isEmpty()
                || trainee.getUser().getLastName() == null
                || trainee.getUser().getLastName().isEmpty()
                || trainee.getUser().getUsername() == null
                || trainee.getUser().getUsername().isEmpty()
                || trainee.getUser().getPassword() == null
                || trainee.getUser().getPassword().isEmpty()
                || trainee.getAddress() == null
                || trainee.getAddress().isEmpty()
                || trainee.getDateOfBirth() == null
                || trainee.getDateOfBirth().toString().isEmpty()) {
            logger.warn("Trainee save failed");
            throw new ServiceException("Trainee save failed, trainee is invalid");

        }

        traineeDAOImpl.save(trainee);
        logger.info("Trainee saved {}", trainee);

    }

    public void updateTrainee(Trainee trainee) throws ServiceException {
        if (trainee.getId() == null || trainee.getId() <= 0
                || trainee.getUser().getId() == null || trainee.getUser().getId() <= 0
                || trainee.getUser().getFirstName() == null
                || trainee.getUser().getFirstName().isEmpty()
                || trainee.getUser().getLastName() == null
                || trainee.getUser().getLastName().isEmpty()
                || trainee.getUser().getUsername() == null
                || trainee.getUser().getUsername().isEmpty()
                || trainee.getUser().getPassword() == null
                || trainee.getUser().getPassword().isEmpty()
                || trainee.getAddress() == null
                || trainee.getAddress().isEmpty()
                || trainee.getDateOfBirth() == null
                || trainee.getDateOfBirth().toString().isEmpty()) {
            logger.warn("Trainee update failed");
            throw new ServiceException("Trainee update failed, trainee is invalid");
        }

        traineeDAOImpl.update(trainee);
        logger.info("Trainee updated {}", trainee);

    }

    public void deleteTraineeById(Long id) throws ServiceException {
        if (id == null || id <= 0) {
            logger.warn("Trainee find by id failed");
            throw new ServiceException("Trainee delete failed, id is invalid");
        }

        traineeDAOImpl.deleteById(id);
        logger.info("Trainee deleted {}", id);

    }

    public Optional<Trainee> findTraineeById(Long id)  {
        if (id == null || id <= 0) {
            logger.warn("Trainee find id failed");
            throw new ServiceException("Trainee find id failed, id is invalid");
        }
        logger.info("Found trainee by id {} ", id);
        Optional<Trainee> byId = traineeDAOImpl.findById(id);
        return Optional.of(byId.orElseThrow());
    }

    public Set<Map.Entry<Long, Trainee>> getAllTrainees() {
        return traineeDAOImpl.findAll();
    }

    public Optional<Trainee> findTraineeByFirstName(String firstName) {
        if (firstName == null || firstName.isEmpty()) {
            logger.warn("Trainee find by firstname failed");
            throw new ServiceException("Trainee find by firstname find failed, firstName is invalid");
        }
        logger.info("Trainee found by name {}", firstName);
        return Optional.of(traineeDAOImpl.findByFirstName(firstName).orElseThrow());
    }

    public Optional<Trainee> findByLastName(String lastName) {
        if (lastName == null || lastName.isEmpty()) {
            logger.warn("Trainee find by lastname  failed");
            throw new ServiceException("Trainee find by lastname failed, lastName is invalid");
        }
        logger.info("Trainee found by lastName {}", lastName);
        return Optional.of(traineeDAOImpl.findByLastName(lastName).orElseThrow());
    }
}
