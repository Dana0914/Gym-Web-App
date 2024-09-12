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




@Repository
public class TrainingDAOImpl implements TrainingDAO {

    @PersistenceContext
    private EntityManager em;

    @Override
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

        return (Training) findTrainingListQuery.getSingleResult();

    }

    @Override
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

        return (Training) findTrainingListQuery.getSingleResult();

    }

    @Override
    @Transactional
    public void addTraining(Trainee trainee, Trainer trainer, TrainingType trainingType) {
        Training training = new Training();
        training.setTrainer(trainer);
        training.setTrainingType(trainingType);
        training.setTrainee(trainee);

        em.merge(training);
    }

    @Override
    @Transactional
    public void save(Training training) throws DaoException {
        if (training.getId() != null) {
            em.merge(training);
        } else {
            em.persist(training);
        }

    }

    @Override
    @Transactional
    public void update(Training training)  {
        Training trainingById = findById(training.getId());
        if (trainingById != null) {
            trainingById.setId(trainingById.getId());
            em.merge(trainingById);
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
    public Training findById(Long id) {
        Query findIdQuery = em.createQuery("SELECT t FROM Training t WHERE t.id = :id");
        findIdQuery.setParameter("id", id);
        return (Training) findIdQuery.getSingleResult();
    }



    @Override
    public Training findByTrainingName(String trainingName) {
        Query findByNameQuery = em.createQuery("SELECT t FROM Training t WHERE t.trainingName = :trainingName");
        findByNameQuery.setParameter("trainingName", trainingName);
        return (Training) findByNameQuery.getSingleResult();
    }

    @Override
    public Training findByTrainingType(String trainingType) {
        Query findByTrainingTypeQuery = em.createQuery("SELECT t FROM Training t WHERE t.trainingType = :trainingType");
        findByTrainingTypeQuery.setParameter("trainingType", trainingType);
        return (Training) findByTrainingTypeQuery.getSingleResult();

    }
}
