package epam.com.gymapplication.service;

import epam.com.gymapplication.dao.TraineeRepository;
import epam.com.gymapplication.dao.TrainerRepository;
import epam.com.gymapplication.dao.TrainingRepository;
import epam.com.gymapplication.dao.TrainingTypeRepository;
import epam.com.gymapplication.dto.TrainingDTO;
import epam.com.gymapplication.entity.Trainee;
import epam.com.gymapplication.entity.Trainer;
import epam.com.gymapplication.entity.Training;
import epam.com.gymapplication.entity.TrainingType;
import jakarta.persistence.EntityNotFoundException;
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
    @Autowired
    private TraineeRepository traineeRepository;
    @Autowired
    private TrainerRepository trainerRepository;
    @Autowired
    private TrainingTypeRepository trainingTypeRepository;




    public List<TrainingDTO> getTraineesTrainingList(String username, LocalDate from,
                                                     LocalDate to, String trainerName,
                                                     String trainingName) {

        Trainee traineeByUsername = traineeRepository.findByUsername(username).orElseThrow();
        logger.info("Found trainee by username {}", traineeByUsername);

        Trainer trainerByUsername = trainerRepository.findByFirstName(trainerName).orElseThrow();
        logger.info("Found trainer by username {}", trainerByUsername);


        Training trainingByName = trainingRepository.findByTrainingName(trainingName).orElseThrow();
        logger.info("Found training by username {}", trainingByName);

        TrainingDTO trainingRequestDTO = new TrainingDTO();

        trainingRequestDTO.setTrainee(traineeByUsername);
        trainingRequestDTO.setTrainer(trainerByUsername);



        List<Training> trainingListByTrainee = trainingRepository.findTraineesTrainingList(
                username, from, to, trainerName, trainingName);

        logger.info("Fetching training list for username: {}", trainingListByTrainee);

        List<TrainingDTO> traineesTrainingList = trainingListByTrainee.stream().map(training -> {
            TrainingDTO trainingResponseDTO = new TrainingDTO();

            trainingResponseDTO.setFrom(training.getTrainingDate());
            trainingResponseDTO.setTo(training.getTrainingDate());
            trainingResponseDTO.setTrainingType(training.getTrainingType());
            trainingResponseDTO.setTraineeUsername(trainingRequestDTO.getTrainee().getUser().getUsername());
            trainingResponseDTO.setTrainerName(trainingRequestDTO.getTrainer().getUser().getFirstName());
            trainingResponseDTO.setTrainingName(trainingByName.getTrainingName());

            return trainingResponseDTO;

        }).toList();

        return traineesTrainingList;


    }

    public List<TrainingDTO> getTrainersTrainingList(String username, LocalDate from, LocalDate to, String traineeName, String trainingName)  {
        Trainer trainerByUsername = trainerRepository.findByFirstName(traineeName).orElseThrow();
        logger.info("Found trainer by username {}", trainerByUsername);

        Trainee traineeByName = traineeRepository.findByFirstName(traineeName).orElseThrow();
        logger.info("Found trainer by username {}", trainerByUsername);

        Training trainingByName = trainingRepository.findByTrainingName(trainingName).orElseThrow();
        logger.info("Found training by username {}", trainingByName);

        TrainingDTO trainingRequestDTO = new TrainingDTO();
        trainingRequestDTO.setTrainer(trainerByUsername);
        trainingRequestDTO.setTrainee(traineeByName);


        List<Training> trainersTrainingList = trainingRepository.findTrainingListByTrainerCriteria(username, from, to, traineeName, trainingName);
        logger.info("Fetching training list for username: {}", trainerByUsername);

        List<TrainingDTO> trainingDTOS = trainersTrainingList.stream().map(training ->
        {
            TrainingDTO trainingResponseDTO = new TrainingDTO();
            trainingResponseDTO.setTrainerName(trainerByUsername.getUser().getUsername());
            trainingResponseDTO.setFrom(training.getTrainingDate());
            trainingResponseDTO.setTo(training.getTrainingDate());
            trainingResponseDTO.setTrainingType(training.getTrainingType());
            trainingResponseDTO.setTrainingName(trainingByName.getTrainingName());

            return trainingResponseDTO;

        }).toList();

        return trainingDTOS;
    }

    public void addTraining(TrainingDTO trainingDTO) {

        String traineeUsername = trainingDTO.getTraineeUsername();
        String trainerUsername = trainingDTO.getTrainerUsername();
        String trainingName = trainingDTO.getTrainingName();
        LocalDate trainingDate = trainingDTO.getTrainingDate();
        Integer trainingDuration = trainingDTO.getTrainingDuration();

        Trainee traineeByUsername = traineeRepository.findByUsername(traineeUsername).orElseThrow();
        logger.info("Found trainer by username {}", trainerUsername);
        Trainer trainerByUsername = trainerRepository.findByUsername(trainerUsername).orElseThrow();
        logger.info("Found trainer by username {}", trainerByUsername);
        Training trainingByName = trainingRepository.findByTrainingName(trainingName).orElseThrow();
        logger.info("Found training by username {}", trainingByName);
        TrainingType trainingType = trainingTypeRepository.findById(trainingByName.getId()).orElseThrow();
        logger.info("Found training type by id {}", trainingType);



        Training training = new Training();
        training.setTrainingDuration(trainingDuration);
        training.setTrainingDate(trainingDate);
        training.setTrainingType(trainingType);
        training.setTrainingDuration(trainingDuration);
        training.setTrainer(trainerByUsername);
        training.setTrainee(traineeByUsername);
        training.setTrainingName(trainingName);

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
        return trainingRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Entity not found by id"));
    }


    public Training findByTrainingName(String trainingName) {
        logger.info("Training name found {} ", trainingName);
        return trainingRepository.findByTrainingName(trainingName).orElseThrow(() ->
                new EntityNotFoundException("Entity not found  by trainingName"));
    }

    public Training findByTrainingType(String trainingType) {
        logger.info("Training type found {} ", trainingType);
        return trainingRepository.findByTrainingType(trainingType).orElseThrow(() ->
                new EntityNotFoundException("Entity not found by trainingType")) ;
    }
}
