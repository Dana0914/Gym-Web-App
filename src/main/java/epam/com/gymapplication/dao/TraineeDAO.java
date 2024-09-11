package epam.com.gymapplication.dao;

import epam.com.gymapplication.model.Trainee;

import java.util.List;
import java.util.Optional;




public interface TraineeDAO {

    void save(Trainee trainee);
    void update(Trainee trainee);
    void delete(Trainee trainee);
    Optional<Trainee> findById(Long id);
    Optional<Trainee> findByFirstName(String firstName);
    Optional<Trainee> findByLastName(String lastName);
    List<Trainee> findAll();
    Optional<Trainee> findByUsername(String username);

}
