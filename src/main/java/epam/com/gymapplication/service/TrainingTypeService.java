package epam.com.gymapplication.service;


import epam.com.gymapplication.dao.TrainingTypeRepository;
import epam.com.gymapplication.model.TrainingType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class TrainingTypeService {
    private static final Logger logger = LoggerFactory.getLogger(TrainingType.class);

    @Autowired
    private TrainingTypeRepository trainingTypeRepository;


    public void save(TrainingType trainingType)  {
        logger.info("Entity saved {} ", trainingType);
        trainingTypeRepository.save(trainingType);
    }

    public void update(TrainingType trainingType) {
        logger.info("Entity updated {} ", trainingType);
        trainingTypeRepository.update(trainingType);

    }

    public void deleteById(Long id)  {
        logger.info("Entity deleted by id{} ", id);
        trainingTypeRepository.deleteById(id);
    }

    public TrainingType findById(Long id)  {
        logger.info("Entity id found {} ", id);
        return trainingTypeRepository.findById(id).orElseThrow();
    }

    public Optional<TrainingType> findByTrainingTypeName(String trainingType) {
        TrainingType trainingTypeName = trainingTypeRepository.findByTrainingTypeName(trainingType);
        if (trainingTypeName == null) {
            return Optional.empty();
        }
        logger.info("Entity found by name {} ", trainingType);
        return Optional.of(trainingTypeName);
    }

    public List<TrainingType> findAll() {
        return (List<TrainingType>) trainingTypeRepository.findAll();
    }




}
