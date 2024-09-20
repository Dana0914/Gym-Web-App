package epam.com.gymapplication.service;

import epam.com.gymapplication.dao.TrainingRepository;
import epam.com.gymapplication.model.Trainee;
import epam.com.gymapplication.model.Trainer;
import epam.com.gymapplication.model.Training;
import epam.com.gymapplication.model.TrainingType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


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
        trainingRepository.update(training);
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


    public Optional<Training> findByTrainingName(String trainingName) {
        Training byTrainingName = trainingRepository.findByTrainingName(trainingName);
        if (byTrainingName == null) {
            return Optional.empty();
        }
        logger.info("Training name found {} ", trainingName);
        return Optional.of(byTrainingName);
    }

    public Optional<Training> findByTrainingType(String trainingType) {
        Training byTrainingType = trainingRepository.findByTrainingType(trainingType);
        if (byTrainingType == null) {
            return Optional.empty();
        }
        logger.info("Training type found {} ", trainingType);
        return Optional.of(byTrainingType);
    }
}
