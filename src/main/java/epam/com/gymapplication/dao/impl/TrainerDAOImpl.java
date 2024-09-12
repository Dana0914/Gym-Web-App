package epam.com.gymapplication.dao.impl;

import epam.com.gymapplication.customexception.DaoException;
import epam.com.gymapplication.dao.TrainerDAO;
import epam.com.gymapplication.model.Trainer;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.PersistenceException;
import jakarta.persistence.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;




@Repository
public class TrainerDAOImpl implements TrainerDAO {

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<Trainer> findTrainerNotAssignedToTrainee(String username)  {
        Query findTrainersListQuery = em.createQuery("select t from Trainee t join t.trainers tt where t.user.username =: username");
        findTrainersListQuery.setParameter("username", username);
        return (List<Trainer>) findTrainersListQuery.getResultList();
    }

    @Override
    @Transactional
    public void save(Trainer trainer)  {
        if (trainer.getId() != null) {
            em.merge(trainer);
        } else {
            em.persist(trainer);
        }

    }

    @Override
    @Transactional
    public void update(Trainer trainer) throws DaoException {
        Trainer trainerById = findById(trainer.getId());
        if (trainerById != null) {
            trainer.setId(trainerById.getId());
            em.merge(trainer);
        }



    }

    @Override
    @Transactional
    public void delete(Trainer trainer) {
        try {
            em.remove(em.merge(trainer));
        } catch (PersistenceException e) {
            throw new DaoException(e.getMessage());
        }

    }

    @Override
    public Trainer findById(Long id) throws DaoException {
        Query query = em.createQuery("SELECT t FROM Trainer t WHERE t.id = :id");
        query.setParameter("id", id);
        return (Trainer) query.getSingleResult();
    }



    @Override
    public Trainer findByFirstName(String firstname) {
        Query query = em.createQuery("select t from Trainer t where t.user.firstname = :firstname");
        query.setParameter("firstname", firstname);
        return  (Trainer) query.getSingleResult();
    }

    @Override
    public Trainer findByLastName(String lastname) {
        Query query = em.createQuery("select t from Trainer t where t.user.lastname = :lastname");
        query.setParameter("lastname", lastname);
        return (Trainer) query.getSingleResult();

    }

    @Override
    public Trainer findBySpecialization(Long specialization) {
        Query findBySpecialization = em.createQuery("select t from Trainer t where t.specialization = :specialization");
        return (Trainer) findBySpecialization.setParameter("specialization", specialization).getSingleResult();

    }

    @Override
    public Trainer findByUsername(String username) {
        Query findByUsernameQuery = em.createQuery("select t from Trainer t where t.user.username = :username");
        findByUsernameQuery.setParameter("username", username);
        return (Trainer) findByUsernameQuery.getSingleResult();
    }

    @Override
    public List<Trainer> findAll() {
        return em.createQuery("SELECT t FROM Trainer t", Trainer.class).getResultList();
    }
}
