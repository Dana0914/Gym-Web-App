package epam.com.gymapplication.service;

import epam.com.gymapplication.dao.TrainingRepository;
import epam.com.gymapplication.entity.Trainee;
import epam.com.gymapplication.entity.Trainer;
import epam.com.gymapplication.entity.Training;
import epam.com.gymapplication.entity.TrainingType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;


@Service
public class TrainingService {
    private static final Logger logger = LoggerFactory.getLogger(TrainingService.class);

    @Autowired
    private TrainingRepository trainingRepository;



    public List<Training> findTrainingListByTraineeCriteria(String username, LocalDate from, LocalDate to, String trainingTypeName)  {
        logger.info("Find training list by trainee criteria");
        return trainingRepository.findTrainingListByTraineeCriteria(username, from, to, trainingTypeName);
    }

    public List<Training> findTrainingListByTrainerCriteria(String username, LocalDate from, LocalDate to, String trainingTypeName)  {
        logger.info("Find training list by trainer criteria");
        return trainingRepository.findTrainingListByTraineeCriteria(username, from, to, trainingTypeName);
    }

    public void addTraining(Trainee trainee, Trainer trainer, TrainingType trainingType) {

        Training training = new Training();
        training.setTrainer(trainer);
        training.setTrainingType(trainingType);
        training.setTrainee(trainee);
        trainingRepository.save(training);

    }


    public void saveTraining(Training training) {
        trainingRepository.save(training);
        logger.info("Training saved");

    }

    public void updateTraining(Training training)  {
        trainingRepository.updateTraining(
                training.getTrainingName(),
                training.getTrainingDate(),
                training.getTrainingType().getTrainingTypeName(),
                training.getTrainingDuration());
        logger.info("Training updated");

    }

    public void deleteById(Long id)  {
        trainingRepository.deleteById(id);
        logger.info("Training deleted");

    }

    public Training findTrainingById(Long id) {
        logger.info("Found training by id {} ", id);
        return trainingRepository.findById(id).orElseThrow();
    }


    public Training findByTrainingName(String trainingName) {
        logger.info("Training name found {} ", trainingName);
        return trainingRepository.findByTrainingName(trainingName).orElseThrow();
    }

    public Training findByTrainingType(String trainingType) {
        logger.info("Training type found {} ", trainingType);
        return trainingRepository.findByTrainingType(trainingType).orElseThrow();
    }
}
