package epam.com.gymapplication.dao.impl;


import epam.com.gymapplication.dao.TrainingDAO;
import epam.com.gymapplication.model.Training;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;



@Repository
public class TrainingDAOImpl implements TrainingDAO {

    @PersistenceContext
    private EntityManager em;

    @Override
    @Transactional
    public void save(Training training) {
        em.persist(training);

    }

    @Override
    @Transactional
    public void update(Training training)  {
        Optional<Training> trainingById = findById(training.getId());
        trainingById.ifPresent(value -> em.persist(value));

    }

    @Override
    @Transactional
    public void deleteById(Long id)  {
        Optional<Training> trainingToRemove = findById(id);
        trainingToRemove.ifPresent(training -> em.remove(training));

    }

    @Override
    public List<Training> findAll() {
        return em.createQuery("SELECT t FROM Training t", Training.class).getResultList();
    }

    @Override
    public Optional<Training> findById(Long id) {
        if (em.contains(id)) {
            return Optional.of(em.find(Training.class, id));
        }

        return Optional.empty();
    }



    @Override
    public Optional<Training> findByTrainingName(String trainingName) {
        if (em.contains(trainingName)) {
            return Optional.of(em.find(Training.class, trainingName));
        }
        return Optional.empty();
    }

    @Override
    public Optional<Training> findByTrainingType(String trainingType) {
        if (em.contains(trainingType)) {
            return Optional.of(em.find(Training.class, trainingType));
        }
        return Optional.empty();

    }
}
