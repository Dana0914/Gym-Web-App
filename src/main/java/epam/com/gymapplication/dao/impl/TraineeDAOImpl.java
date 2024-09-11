package epam.com.gymapplication.dao.impl;


import epam.com.gymapplication.customexception.DaoException;
import epam.com.gymapplication.dao.TraineeDAO;
import epam.com.gymapplication.model.Trainee;
import epam.com.gymapplication.profile.UserProfileService;
import jakarta.persistence.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.Optional;



@Repository
public class TraineeDAOImpl implements TraineeDAO {

    @Autowired
    private UserProfileService userProfileUtils;

    @PersistenceContext
    private EntityManager em;


    @Override
    @Transactional
    public void save(Trainee trainee)  throws DaoException {
        em.persist(trainee);



    }

    @Override
    @Transactional
    public void update(Trainee trainee) throws DaoException {
        Query updateQuery = em.createQuery("update Trainee t set t.user.firstname =:firstname," +
                "t.user.lastname =:lastname where t.id=:id");
        updateQuery.setParameter("id", trainee.getId());
        updateQuery.executeUpdate();
        String username = userProfileUtils.concatenateUsername(updateQuery.getParameter("firstname").toString(),
                updateQuery.getParameter("lastname").toString());
        trainee.getUser().setUsername(username);


    }

    @Override
    public void delete(Trainee trainee) throws DaoException {
        em.remove(trainee);



    }

    @Override
    public Optional<Trainee> findById(Long id) throws DaoException {
        Query findByIdQuery = em.createQuery("SELECT t FROM Trainee t WHERE t.id = :id");
        findByIdQuery.setParameter("id", id);
        Trainee trainee = (Trainee) findByIdQuery.getSingleResult();
        return Optional.ofNullable(trainee);


    }


    @Override
    public Optional<Trainee> findByFirstName(String firstName) throws DaoException {
        Query findByFirstnameQuery = em.createQuery("select t from Trainee t where t.user.firstname = :firstname");
        findByFirstnameQuery.setParameter("firstname", firstName);
        Trainee trainee = (Trainee) findByFirstnameQuery.getSingleResult();
        if (trainee != null) {
            return Optional.of(trainee);
        }
        return Optional.empty();

    }

    @Override
    public Optional<Trainee> findByLastName(String lastName) throws DaoException {
        Query findByLastnameQuery = em.createQuery("select t from Trainee t where t.user.lastname = :lastname");
        findByLastnameQuery.setParameter("lastname", lastName);
        Trainee trainee = (Trainee) findByLastnameQuery.getSingleResult();
        if (trainee != null) {
            return Optional.of(trainee);
        }
        return Optional.empty();

    }
    @Override
    public Optional<Trainee> findByUsername(String username) throws DaoException {
        Query findByUsernameQuery = em.createQuery("select t from Trainee t where t.user.username = :username");
        findByUsernameQuery.setParameter("username", username);
        Trainee trainee = (Trainee) findByUsernameQuery.getSingleResult();
        if (trainee != null) {
            return Optional.of(trainee);
        }
        return Optional.empty();
    }

    @Override
    public List<Trainee> findAll() throws DaoException {
        return em.createQuery("SELECT t FROM Trainee t", Trainee.class).getResultList();
    }

}
