package epam.com.gymapplication.dao.impl;


import epam.com.gymapplication.customexception.DaoException;
import epam.com.gymapplication.dao.TrainingTypeDAO;
import epam.com.gymapplication.model.TrainingType;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.PersistenceException;
import jakarta.persistence.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class TrainingTypeDAOImpl implements TrainingTypeDAO {

    @PersistenceContext
    private EntityManager em;


    @Override
    public TrainingType findById(Long id) throws DaoException {
        Query findByIdQuery = em.createQuery("SELECT t FROM TrainingType t WHERE t.id = :id");
        findByIdQuery.setParameter("id", id);
        TrainingType trainingType = (TrainingType) findByIdQuery.getSingleResult();
        return trainingType;
    }

    @Override
    @Transactional
    public void save(TrainingType trainingType) throws DaoException {
        em.persist(trainingType);

    }

    @Override
    @Transactional
    public void delete(TrainingType trainingType) throws DaoException {
        try {
            em.remove(em.merge(trainingType));
        } catch (PersistenceException e) {
            throw new DaoException(e.getMessage());
        }
    }

    @Override
    @Transactional
    public void update(TrainingType trainingType) throws DaoException {
        TrainingType trainingTypeById = findById(trainingType.getId());
        if (trainingTypeById != null) {

            trainingType.setId(trainingTypeById.getId());
            em.merge(trainingType);
        }

    }

    @Override
    public List<TrainingType> findAll() {
        return em.createQuery("SELECT t FROM TrainingType t", TrainingType.class).getResultList();
    }

    @Override
    public TrainingType findByTrainingTypeName(String trainingTypeName) {
        Query findTrainingTypeNameQuery = em.createQuery("select t from TrainingType t where t.trainingTypeName =: name");
        TrainingType trainingType = (TrainingType) findTrainingTypeNameQuery.setParameter("name", trainingTypeName);
        return trainingType;

    }
}
