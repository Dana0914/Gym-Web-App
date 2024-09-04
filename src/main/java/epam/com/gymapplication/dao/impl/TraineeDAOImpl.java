package epam.com.gymapplication.dao.impl;

import epam.com.gymapplication.dao.TraineeDAO;
import epam.com.gymapplication.model.Trainee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;


import java.util.Map;
import java.util.Optional;
import java.util.Set;


@Repository
public class TraineeDAOImpl implements TraineeDAO {

    private Map<Long, Trainee> traineeStorage;

    @Autowired
    public void setTraineeStorage(Map<Long, Trainee> traineeStorage) {
        this.traineeStorage = traineeStorage;
    }

    @Override
    public void save(Trainee trainee)  {
        traineeStorage.put(trainee.getId(), trainee);


    }

    @Override
    public void update(Trainee trainee)  {
        Optional<Trainee> traineeById = findById(trainee.getId());
        traineeById.ifPresent(value -> traineeStorage.put(value.getId(), trainee));


    }

    @Override
    public void deleteById(Long id) {
        Optional<Trainee> traineeToRemove = findById(id);
        traineeToRemove.ifPresent(trainee -> traineeStorage.remove(trainee.getId(), trainee));


    }

    @Override
    public Optional<Trainee> findById(Long id) {
        if (traineeStorage.containsKey(id)) {
            Trainee trainee = traineeStorage.get(id);
            return Optional.of(trainee);
        }
        return Optional.empty();

    }

    @Override
    public Set<Map.Entry<Long, Trainee>> findAll() {
        return traineeStorage.entrySet();
    }

    @Override
    public Optional<Trainee> findByFirstName(String firstName) {
        return traineeStorage.values()
                .stream()
                .filter(trainee -> trainee.getUser().getFirstName().equals(firstName))
                .findFirst();
    }

    @Override
    public Optional<Trainee> findByLastName(String lastName) {
        return traineeStorage.values()
                .stream()
                .filter(trainee -> trainee.getUser().getLastName().equals(lastName))
                .findFirst();
    }

}
