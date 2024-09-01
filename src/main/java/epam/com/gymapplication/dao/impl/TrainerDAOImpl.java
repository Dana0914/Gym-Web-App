package epam.com.gymapplication.dao.impl;

import epam.com.gymapplication.customexception.DaoException;
import epam.com.gymapplication.dao.TrainerDAO;
import epam.com.gymapplication.model.Trainer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.Optional;
import java.util.Set;


@Repository
public class TrainerDAOImpl implements TrainerDAO {
    private static final Logger logger = LoggerFactory.getLogger(TrainerDAOImpl.class);
    private final Map<Long, Trainer> trainerStorage;

    @Autowired
    public TrainerDAOImpl(Map<Long, Trainer> trainerStorage) {
        this.trainerStorage = trainerStorage;
    }

    @Override
    public void save(Trainer trainer) throws DaoException {
        if (trainer == null) {
            throw new DaoException("Trainer must not be null");
        }
        trainerStorage.put(trainer.getId(), trainer);
        logger.info("Trainer saved successfully {} ", trainer);

    }

    @Override
    public void update(Trainer trainer) throws DaoException {
        if (trainer == null) {
            throw new DaoException("Trainer must not be null");
        }

        Optional<Trainer> trainerById = findById(trainer.getId());
        if (trainerById.isPresent()) {
            trainerStorage.put(trainerById.get().getId(), trainer);
            logger.info("Trainer updated successfully {} ", trainer);
        }

    }

    @Override
    public Boolean deleteById(Long id) throws DaoException {
        if (id == null || id <= 0) {
            throw new DaoException("Trainer must not be null and it must have valid id");
        }
        Optional<Trainer> traineeToRemove = findById(id);
        logger.info("Trainer deleted successfully");
        return trainerStorage.remove(traineeToRemove.get().getId(), traineeToRemove.get());


    }

    @Override
    public Optional<Trainer> findById(Long id) {
        if (id == null || id <= 0) {
            logger.warn("Trainer must not be null and it must have valid id");
            return Optional.empty();
        }
        Trainer trainerById = trainerStorage.get(id);
        logger.info("Trainer found by id {}", trainerById);
        if (trainerById != null) {
            return Optional.of(trainerById);
        }
        return Optional.empty();

    }

    @Override
    public Set<Map.Entry<Long, Trainer>> findAll() throws DaoException {
        if (trainerStorage.isEmpty()) {
            throw new DaoException("No trainers found");
        }
        return trainerStorage.entrySet();
    }

    @Override
    public  Optional<Trainer> findByFirstName(String firstName) {
        if (firstName == null || firstName.isEmpty()) {
            logger.warn("Trainer first name does not exist");
            return Optional.empty();
        }
        logger.info("Trainer found successfully {} ", firstName);
        return trainerStorage.values()
                .stream()
                .filter(trainer -> trainer.getUser().getFirstName().equals(firstName))
                .findFirst();
    }

    @Override
    public Optional<Trainer> findByLastName(String lastName) {
        if (lastName == null || lastName.isEmpty()) {
            logger.warn("Trainer last name does not exist");
            return Optional.empty();
        }
        logger.info("Trainer found successfully {} ", lastName);
        return trainerStorage.values()
                .stream()
                .filter(trainer -> trainer.getUser().getLastName().equals(lastName)).
                findFirst();
    }

    @Override
    public Optional<Trainer> findBySpecialization(String specialization) {
        if (specialization == null || specialization.isEmpty()) {
            logger.warn("Trainer specialization does not exist");
            return Optional.empty();
        }
        logger.info("Trainer found successfully {} ", specialization);
        return trainerStorage.values()
                .stream()
                .filter(trainer -> trainer.getSpecialization().equals(specialization))
                .findFirst();
    }
}
