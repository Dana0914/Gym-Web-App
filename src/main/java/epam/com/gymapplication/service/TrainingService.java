package epam.com.gymapplication.service;

import epam.com.gymapplication.dao.TraineeRepository;
import epam.com.gymapplication.dao.TrainerRepository;
import epam.com.gymapplication.dao.TrainingRepository;


import epam.com.gymapplication.dao.TrainingTypeRepository;
import epam.com.gymapplication.dto.TraineeDTO;
import epam.com.gymapplication.dto.TrainerDTO;
import epam.com.gymapplication.dto.TrainingDTO;
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
import java.util.Optional;


@Service
public class TrainingService {
    private static final Logger logger = LoggerFactory.getLogger(TrainingService.class);

    @Autowired
    private TrainingRepository trainingRepository;
    @Autowired
    private TraineeRepository traineeRepository;
    @Autowired
    private TrainerRepository trainerRepository;





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

    public TrainingDTO addTraining(Trainee trainee, Trainer trainer, TrainingDTO trainingDTO) {

        Training training = new Training();
        training.setTrainingDate(trainingDTO.getTrainingDate());
        training.setTrainingDuration(trainingDTO.getTrainingDuration());
        training.setTrainingName(trainingDTO.getTrainingName());
        training.setTrainingType(trainingDTO.getTrainingType());
        training.setTrainee(trainee);
        training.setTrainer(trainer);

        trainingRepository.save(training);


        TrainingDTO trainingRequestDTO = new TrainingDTO();
        trainingRequestDTO.setTraineeUsername(training.getTrainee().getUser().getUsername());
        trainingRequestDTO.setTrainerUsername(training.getTrainer().getUser().getUsername());
        trainingRequestDTO.setTrainingName(trainingDTO.getTrainingName());
        trainingRequestDTO.setTrainingDuration(trainingDTO.getTrainingDuration());
        trainingRequestDTO.setTrainingDate(trainingDTO.getTrainingDate());


        return trainingRequestDTO;

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
