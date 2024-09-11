package epam.com.gymapplication.dao;

import epam.com.gymapplication.model.Trainee;

import java.util.List;
import java.util.Optional;




public interface TraineeDAO {

    void save(Trainee trainee);
    void update(Trainee trainee);
    void delete(Trainee trainee);
    Trainee findById(Long id);
    Trainee findByFirstName(String firstName);
    Trainee findByLastName(String lastName);
    List<Trainee> findAll();
    Trainee findByUsername(String username);

}
