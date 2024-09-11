package epam.com.gymapplication.dao.impl;


import epam.com.gymapplication.customexception.DaoException;
import epam.com.gymapplication.dao.TrainingDAO;
import epam.com.gymapplication.model.Trainee;
import epam.com.gymapplication.model.Trainer;
import epam.com.gymapplication.model.Training;
import epam.com.gymapplication.model.TrainingType;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.PersistenceException;
import jakarta.persistence.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;



@Repository
public class TrainingDAOImpl implements TrainingDAO {

    @PersistenceContext
    private EntityManager em;


    public Training findTrainingListByTraineeCriteria(String username, LocalDate from,
                                                      LocalDate to, String trainingTypeName) {
        Query findTrainingListQuery = em.createQuery(
                "select t FROM Training t " +
                        "where t.trainee.user.username =:username " +
                        "AND t.trainingDate =: from " +
                        "AND t.trainingDate =:to " +
                        "AND t.trainingType.trainingTypeName =: trainingType");

        findTrainingListQuery
                .setParameter("username", username)
                .setParameter("from", from)
                .setParameter("to", to)
                .setParameter("trainingType", trainingTypeName);

        return (Training) findTrainingListQuery.getResultList();

    }


    public Training findTrainingListByTrainerCriteria(String username, LocalDate from,
                                                      LocalDate to, String trainingTypeName) {
        Query findTrainingListQuery = em.createQuery(
                "select t FROM Training t " +
                        "where t.trainer.user.username =:username " +
                        "AND t.trainingDate =: from " +
                        "AND t.trainingDate =:to " +
                        "AND t.trainingType.trainingTypeName =: trainingType");

        findTrainingListQuery
                .setParameter("username", username)
                .setParameter("from", from)
                .setParameter("to", to)
                .setParameter("trainingType", trainingTypeName);

        return (Training) findTrainingListQuery.getResultList();

    }

    public void addTraining(Trainee trainee, Trainer trainer, TrainingType trainingType) {
        Training training = new Training();
        training.setTrainee(trainee);
        training.setTrainer(trainer);
        training.setTrainingType(trainingType);
        training.setTraineeID(trainee.getId());
        training.setTrainerID(trainer.getId());

        em.persist(training);
    }

    @Override
    @Transactional
    public void save(Training training) throws DaoException {
        if (!em.contains(training)) {
            em.persist(training);
        }
        throw new DaoException("Training already exists");

    }

    @Override
    @Transactional
    public void update(Training training)  {
        Optional<Training> trainingById = findById(training.getId());
        if (trainingById.isPresent()) {
            trainingById.get().setId(trainingById.get().getId());
            em.persist(trainingById);
        }

    }

    @Override
    @Transactional
    public void delete(Training training) throws DaoException  {
        try {
            em.remove(em.merge(training));
        } catch (PersistenceException e) {
            throw new DaoException(e.getMessage());
        }

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
