package epam.com.gymapplication.dao.impl;


import epam.com.gymapplication.dao.TrainingDAO;
import epam.com.gymapplication.model.Training;
import epam.com.gymapplication.model.TrainingType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.Optional;
import java.util.Set;


@Repository
public class TrainingDAOImpl implements TrainingDAO {
    private Map<Long, Training> trainingStorage;


    @Autowired
    public void setTrainingStorage(Map<Long, Training> trainingStorage) {
        this.trainingStorage = trainingStorage;
    }

    @Override
    public void save(Training training) {
        trainingStorage.put(training.getId(), training);

    }

    @Override
    public void update(Training training)  {
        Optional<Training> trainingById = findById(training.getId());
        trainingById.ifPresent(value -> trainingStorage.put(value.getId(), training));

    }

    @Override
    public void deleteById(Long id)  {
        Optional<Training> trainingToRemove = findById(id);
        trainingToRemove.ifPresent(training -> trainingStorage.remove(training.getId(), training));

    }

    @Override
    public Optional<Training> findById(Long id) {
        if (trainingStorage.containsKey(id)) {
            Training training = trainingStorage.get(id);
            return Optional.of(training);
        }
        return Optional.empty();
    }

    @Override
    public Set<Map.Entry<Long, Training>> findAll()  {
        return trainingStorage.entrySet();

    }

    @Override
    public Optional<Training> findByTrainingName(String trainingName) {
        return trainingStorage.values()
                .stream()
                .filter(existingtraining -> existingtraining.getTrainingName().equals(trainingName))
                .findFirst();
    }

    @Override
    public Optional<Training> findByTrainingType(TrainingType trainingType) {
        return trainingStorage.values()
                .stream()
                .filter(training -> training.getTrainingType().equals(trainingType))
                .findFirst();

    }
}
