package epam.com.gymapplication.dao.impl;


import epam.com.gymapplication.customexception.DaoException;
import epam.com.gymapplication.dao.TraineeDAO;
import epam.com.gymapplication.model.Trainee;
import jakarta.persistence.*;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;



import java.util.List;




@Repository
public class TraineeDAOImpl implements TraineeDAO {



    @PersistenceContext
    private EntityManager em;



    @Override
    @Transactional
    public void save(Trainee trainee)  throws DaoException {
        if (trainee.getId() != null) {
            em.merge(trainee);
        } else {
            em.persist(trainee);
        }
    }

    @Override
    @Transactional
    public void update(Trainee trainee) throws DaoException {
        Trainee traineeById = findById(trainee.getId());
        if (traineeById != null) {
            trainee.setId(traineeById.getId());
            em.merge(trainee);
        }



    }

    @Override
    @Transactional
    public void delete(Trainee trainee) throws DaoException {
        try {
            em.remove(em.merge(trainee));
        } catch (PersistenceException e) {
            throw new DaoException(e.getMessage());
        }

    }

    @Override
    public Trainee findById(Long id) throws DaoException {
        Query findByIdQuery = em.createQuery("SELECT t FROM Trainee t WHERE t.id = :id");
        findByIdQuery.setParameter("id", id);
        return (Trainee) findByIdQuery.getSingleResult();



    }


    @Override
    public Trainee findByFirstName(String firstName) throws DaoException {
        Query findByFirstnameQuery = em.createQuery("select t from Trainee t where t.user.firstname = :firstname");
        findByFirstnameQuery.setParameter("firstname", firstName);
        return (Trainee) findByFirstnameQuery.getSingleResult();

    }

    @Override
    public Trainee findByLastName(String lastName) throws DaoException {
        Query findByLastnameQuery = em.createQuery("select t from Trainee t where t.user.lastname = :lastname");
        findByLastnameQuery.setParameter("lastname", lastName);
        return (Trainee) findByLastnameQuery.getSingleResult();

    }
    @Override
    public Trainee findByUsername(String username) throws DaoException {
        Query findByUsernameQuery = em.createQuery("select t from Trainee t where t.user.username = :username");
        findByUsernameQuery.setParameter("username", username);
        return (Trainee) findByUsernameQuery.getSingleResult();
    }

    @Override
    public List<Trainee> findAll() throws DaoException {
        return em.createQuery("SELECT t FROM Trainee t", Trainee.class).getResultList();
    }

}
