package epam.com.gymapplication.service;


import epam.com.gymapplication.dao.TrainingTypeRepository;
import epam.com.gymapplication.entity.TrainingType;
import epam.com.gymapplication.utility.exception.ResourceNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Transactional
@Service
public class TrainingTypeService {
    private static final Logger logger = LoggerFactory.getLogger(TrainingType.class);


    private final TrainingTypeRepository trainingTypeRepository;

    public TrainingTypeService(TrainingTypeRepository trainingTypeRepository) {
        this.trainingTypeRepository = trainingTypeRepository;
    }


    public void save(TrainingType trainingType)  {
        logger.info("Entity saved");

        trainingTypeRepository.save(trainingType);
    }

    public void update(TrainingType trainingType) throws ResourceNotFoundException {
        TrainingType byId = trainingTypeRepository
                .findById(trainingType.getId())
                .orElseThrow(()->
                new ResourceNotFoundException("TrainingType not found for ID: " + trainingType.getId()));

        logger.info("Entity found by id {}",trainingType.getId());

        trainingTypeRepository.update(trainingType.getTrainingTypeName(), byId.getId());

        logger.info("Entity updated with new name {}",trainingType.getTrainingTypeName());


    }

    public void deleteById(Long id)  {
        logger.info("Entity deleted by id {} ", id);

        trainingTypeRepository.deleteById(id);
    }

    public TrainingType findById(Long id)  {
        logger.info("Entity found by id {} ", id);

        return trainingTypeRepository
                .findById(id)
                .orElseThrow(() ->
                new EntityNotFoundException("TrainingType not found for ID: " + id));
    }

    public TrainingType findByTrainingTypeName(String trainingType) {
        logger.info("Entity found by name {} ", trainingType);

        return trainingTypeRepository
                .findByTrainingTypeName(trainingType)
                .orElseThrow(() ->
                new EntityNotFoundException("TrainingType not found by name: " + trainingType))    ;
    }

    public List<TrainingType> getAllTrainingTypes() {
        return (List<TrainingType>) trainingTypeRepository.findAll();
    }




}
