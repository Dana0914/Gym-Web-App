package epam.com.gymapplication.dao;


import epam.com.gymapplication.model.Trainer;

import java.util.List;





public interface TrainerDAO {

    void save(Trainer trainer);
    void update(Trainer trainer);
    void delete(Trainer trainer);
    List<Trainer> findAll();
    Trainer findById(Long id);
    List<Trainer> findTrainerNotAssignedToTrainee(String username);
    Trainer findByFirstName(String trainerName);
    Trainer findByLastName(String trainerName);
    Trainer findBySpecialization(Long specialization);
    Trainer findByUsername(String username);
}
