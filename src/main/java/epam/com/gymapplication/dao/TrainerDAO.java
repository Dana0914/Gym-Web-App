package epam.com.gymapplication.dao;

import epam.com.gymapplication.model.Trainer;

import java.util.Map;
import java.util.Optional;
import java.util.Set;



public interface TrainerDAO {

    void save(Trainer trainer);
    void update(Trainer trainer);
    void deleteById(Long id);
    Optional<Trainer> findById(Long id);
    Set<Map.Entry<Long, Trainer>> findAll();
    Optional<Trainer> findByFirstName(String trainerName);
    Optional<Trainer> findByLastName(String trainerName);
    Optional<Trainer> findBySpecialization(String specialization);

}
