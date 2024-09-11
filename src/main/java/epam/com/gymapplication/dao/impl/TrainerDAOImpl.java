package epam.com.gymapplication.dao.impl;

import epam.com.gymapplication.dao.TrainerDAO;
import epam.com.gymapplication.model.Trainer;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.Optional;



@Repository
public class TrainerDAOImpl implements TrainerDAO {

    @PersistenceContext
    private EntityManager em;

    @Override
    @Transactional
    public void save(Trainer trainer)  {
        em.persist(trainer);

    }

    @Override
    @Transactional
    public void update(Trainer trainer)  {
        Optional<Trainer> trainerById = findById(trainer.getId());
        trainerById.ifPresent(value -> em.merge(value));

    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        Optional<Trainer> trainerToRemove = findById(id);
        trainerToRemove.ifPresent(trainer -> em.remove(trainer));

    }

    @Override
    public Optional<Trainer> findById(Long id) {
        Query query = em.createQuery("SELECT t FROM Trainer t WHERE t.id = :id");
        query.setParameter("id", id);
        Trainer trainer = (Trainer) query.getSingleResult();
        return Optional.of(trainer);
    }



    @Override
    public  Optional<Trainer> findByFirstName(String firstname) {
        Query query = em.createQuery("select t from Trainer t where t.user.firstname = :firstname");
        query.setParameter("firstname", firstname);
        Trainer trainers = (Trainer) query.getSingleResult();
        return Optional.of(trainers);
    }

    @Override
    public Optional<Trainer> findByLastName(String lastname) {
        Query query = em.createQuery("select t from Trainer t where t.user.lastname = :lastname");
        query.setParameter("lastname", lastname);
        Trainer trainer = (Trainer) query.getSingleResult();
        return Optional.of(trainer);

    }

    @Override
    public Optional<Trainer> findBySpecialization(String specialization) {
        Query findBySpecialization = em.createQuery("select t from Trainer t where t.specialization = :specialization");
        Trainer trainer = (Trainer) findBySpecialization.setParameter("specialization", specialization).getSingleResult();
        return Optional.of(trainer);
    }

    @Override
    public List<Trainer> findAll() {
        return em.createQuery("SELECT t FROM Trainer t", Trainer.class).getResultList();
    }
}
