package epam.com.gymapplication.dao.impl;

import epam.com.gymapplication.dao.TrainerDAO;
import epam.com.gymapplication.model.Trainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.Optional;
import java.util.Set;


@Repository
public class TrainerDAOImpl implements TrainerDAO {
    private Map<Long, Trainer> trainerStorage;

    @Autowired
    public void setTrainerStorage(Map<Long, Trainer> trainerStorage) {
        this.trainerStorage = trainerStorage;
    }

    @Override
    public void save(Trainer trainer)  {
        trainerStorage.put(trainer.getId(), trainer);

    }

    @Override
    public void update(Trainer trainer)  {
        Optional<Trainer> trainerById = findById(trainer.getId());
        trainerById.ifPresent(value -> trainerStorage.put(value.getId(), trainer));

    }

    @Override
    public void deleteById(Long id) {
        Optional<Trainer> trainerToRemove = findById(id);
        trainerToRemove.ifPresent(trainer -> trainerStorage.remove(trainer.getId(), trainer));

    }

    @Override
    public Optional<Trainer> findById(Long id) {
        if (trainerStorage.containsKey(id)) {
            Trainer trainer = trainerStorage.get(id);
            return Optional.of(trainer);
        }
        return Optional.empty();
    }

    @Override
    public Set<Map.Entry<Long, Trainer>> findAll()  {
        return trainerStorage.entrySet();
    }

    @Override
    public  Optional<Trainer> findByFirstName(String firstName) {
        return trainerStorage.values()
                .stream()
                .filter(trainer -> trainer.getUser().getFirstName().equals(firstName))
                .findFirst();
    }

    @Override
    public Optional<Trainer> findByLastName(String lastName) {
        return trainerStorage.values()
                .stream()
                .filter(trainer -> trainer.getUser().getLastName().equals(lastName)).
                findFirst();
    }

    @Override
    public Optional<Trainer> findBySpecialization(String specialization) {
        return trainerStorage.values()
                .stream()
                .filter(trainer -> trainer.getSpecialization().equals(specialization))
                .findFirst();
    }
}
