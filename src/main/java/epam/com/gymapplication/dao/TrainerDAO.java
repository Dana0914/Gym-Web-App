package epam.com.gymapplication.dao;

import epam.com.gymapplication.model.Trainer;

import java.util.List;
import java.util.Optional;




public interface TrainerDAO {

    void save(Trainer trainer);
    void update(Trainer trainer);
    void delete(Trainer trainer);
    List<Trainer> findAll();
    Trainer findById(Long id);
    Optional<Trainer> findByFirstName(String trainerName);
    Optional<Trainer> findByLastName(String trainerName);
    Optional<Trainer> findBySpecialization(Long specialization);
    Trainer findByUsername(String username);
}
