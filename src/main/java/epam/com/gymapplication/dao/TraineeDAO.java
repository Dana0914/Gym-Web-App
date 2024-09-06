package epam.com.gymapplication.dao;

import epam.com.gymapplication.model.Trainee;

import java.util.Map;
import java.util.Optional;
import java.util.Set;



public interface TraineeDAO {

    void save(Trainee trainee);
    void update(Trainee trainee);
    void deleteById(Long id);
    Optional<Trainee> findById(Long id);
    Set<Map.Entry<Long, Trainee>> findAll();
    Optional<Trainee> findByFirstName(String firstName);
    Optional<Trainee> findByLastName(String lastName);

}
