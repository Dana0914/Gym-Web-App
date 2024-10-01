package epam.com.gymapplication.dao;

import epam.com.gymapplication.entity.Trainer;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;



import java.util.List;
import java.util.Optional;

@Repository
public interface TrainerRepository extends CrudRepository<Trainer, Long> {

    @Query("SELECT t FROM Trainer t WHERE t.id NOT IN " +
            "(SELECT tr.id FROM Trainee tn JOIN tn.trainers tr WHERE tn.user.username = :username)")
    List<Trainer> findTrainersListNotAssignedToTraineeByUsername(@Param("username") String username);


    @Query(value = "select t from Trainer t where t.user.username =:username", nativeQuery = false)
    Optional<Trainer> findByUsername(@Param("username") String username);

    @Query(value = "select t from Trainer t where t.user.firstname =:firstname", nativeQuery = false)
    Optional<Trainer> findByFirstName(@Param("firstname") String firstName);

    @Query(value = "select t from Trainer t where t.user.lastname =:lastname", nativeQuery = false)
    Optional<Trainer> findByLastName(@Param("lastname") String lastName);

}
