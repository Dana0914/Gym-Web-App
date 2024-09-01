package epam.com.gymapplication.dao.impl;

import epam.com.gymapplication.customexception.DaoException;
import epam.com.gymapplication.dao.TraineeDAO;
import epam.com.gymapplication.model.Trainee;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;


import java.util.Map;
import java.util.Optional;
import java.util.Set;

@Repository
public class TraineeDAOImpl implements TraineeDAO {
    private static final Logger logger = LoggerFactory.getLogger(TraineeDAOImpl.class);
    private final Map<Long, Trainee> traineeStorage;

    @Autowired
    public TraineeDAOImpl(Map<Long, Trainee> traineeStorage) {
        this.traineeStorage = traineeStorage;
    }


    @Override
    public void save(Trainee trainee) throws DaoException {
        if (trainee == null) {
            throw new DaoException("Trainee must not be null");
        }
        traineeStorage.put(trainee.getId(), trainee);
        logger.info("Trainee saved successfully {}", trainee);

    }

    @Override
    public void update(Trainee trainee) throws DaoException {
        if (trainee == null) {
            throw new DaoException("Trainee must not be null");
        }
        Optional<Trainee> traineeById = findById(trainee.getId());
        if (traineeById.isPresent()) {
            traineeStorage.put(traineeById.get().getId(), trainee);
            logger.info("Trainee updated successfully {} by id", trainee);
        }

    }

    @Override
    public Boolean deleteById(Long id) throws DaoException {
        if (id == null || id <= 0) {
            throw new DaoException("Id must not be null, and must be a valid id");
        }
        Optional<Trainee> traineeToRemove = findById(id);
        logger.info("Trainee deleted successfully by id");
        return traineeStorage.remove(traineeToRemove.get().getId(), traineeToRemove.get());

    }

    @Override
    public Optional<Trainee> findById(Long id) {
        if (id == null || id <= 0) {
            logger.warn("Trainee id does not exist or is invalid");
            return Optional.empty();
        }
        Trainee traineeById = traineeStorage.get(id);
        logger.info("Trainee found by id {}", traineeById);
        if (traineeById != null) {
            return Optional.of(traineeById);
        }
        return Optional.empty();

    }

    @Override
    public Set<Map.Entry<Long, Trainee>> findAll() throws DaoException {
        if (traineeStorage.entrySet().isEmpty()) {
            throw new DaoException("Trainee list is empty");
        }
        return traineeStorage.entrySet();
    }

    @Override
    public Optional<Trainee> findByFirstName(String firstName) {
        if (firstName == null || firstName.isEmpty()) {
            logger.warn("Trainee name does not exist or is invalid");
            return Optional.empty();
        }
        logger.info("Trainee found by name {}", firstName);
        return traineeStorage.values()
                .stream()
                .filter(trainee -> trainee.getUser().getFirstName().equals(firstName))
                .findFirst();
    }

    @Override
    public Optional<Trainee> findByLastName(String lastName) {
        if (lastName == null || lastName.isEmpty()) {
            logger.warn("Trainee last name does not exist or is invalid");
            return Optional.empty();
        }
        logger.info("Trainee found by last name {}", lastName);
        return traineeStorage.values()
                .stream()
                .filter(trainee -> trainee.getUser().getLastName().equals(lastName))
                .findFirst();
    }

}
