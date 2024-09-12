package epam.com.gymapplication.dao;


import epam.com.gymapplication.model.Trainee;
import epam.com.gymapplication.model.Trainer;
import epam.com.gymapplication.model.Training;
import epam.com.gymapplication.model.TrainingType;

import java.time.LocalDate;
import java.util.List;



public interface TrainingDAO {

    void save(Training training);
    void update(Training training);
    void delete(Training training);
    List<Training> findAll();
    Training findById(Long id);
    Training findByTrainingName(String trainingName);
    Training findByTrainingType(String trainingType);
    void addTraining(Trainee trainee, Trainer trainer, TrainingType trainingType);
    Training findTrainingListByTraineeCriteria(String username, LocalDate from, LocalDate to, String trainingTypeName);
    Training findTrainingListByTrainerCriteria(String username, LocalDate from, LocalDate to, String trainingTypeName);

}
