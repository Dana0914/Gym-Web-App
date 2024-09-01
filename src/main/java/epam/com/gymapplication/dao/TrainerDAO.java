package epam.com.gymapplication.dao;

import epam.com.gymapplication.customexception.DaoException;
import epam.com.gymapplication.model.Trainer;

import java.util.Map;
import java.util.Optional;
import java.util.Set;

public interface TrainerDAO {
    void save(Trainer trainer) throws DaoException;
    void update(Trainer trainer) throws DaoException;
    Boolean deleteById(Long id) throws DaoException;
    Optional<Trainer> findById(Long id);
    Set<Map.Entry<Long, Trainer>> findAll() throws DaoException;
    Optional<Trainer> findByFirstName(String trainerName);
    Optional<Trainer> findByLastName(String trainerName);
    Optional<Trainer> findBySpecialization(String specialization);
}
