package epam.com.gymapplication.dao;

import epam.com.gymapplication.customexception.DaoException;
import epam.com.gymapplication.model.Trainee;

import java.util.Map;
import java.util.Optional;
import java.util.Set;

public interface TraineeDAO {
    void save(Trainee trainee) throws DaoException;
    void update(Trainee trainee) throws DaoException;
    Boolean deleteById(Long id) throws DaoException;
    Optional<Trainee> findById(Long id);
    Set<Map.Entry<Long, Trainee>> findAll() throws DaoException;
    Optional<Trainee> findByFirstName(String firstName);
    Optional<Trainee> findByLastName(String lastName);
}
