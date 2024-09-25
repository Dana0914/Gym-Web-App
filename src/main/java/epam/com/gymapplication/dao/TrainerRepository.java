package epam.com.gymapplication.dao;

import epam.com.gymapplication.entity.Trainer;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;

@Repository
public interface TrainerRepository extends CrudRepository<Trainer, Long> {

    @Query("SELECT t FROM Trainer t WHERE t.id NOT IN " +
            "(SELECT tr.id FROM Trainee tn JOIN tn.trainers tr WHERE tn.user.username = :username)")
    List<Trainer> findTrainersNotAssignedToTrainee(@Param("username") String username);


    @Query(value = "select t from Trainer t where t.user.username =: username", nativeQuery = false)
    Trainer findByUsername(@Param("username") String username);

    @Query(value = "select t from Trainer t where t.user.firstname =: firstname", nativeQuery = false)
    Trainer findByFirstName(@Param("firstname") String firstName);

    @Query(value = "select t from Trainer t where t.user.lastname =: lastname", nativeQuery = false)
    Trainer findByLastName(@Param("lastname") String lastName);

    @Modifying
    @Transactional
    @Query(value = "UPDATE Trainer t SET t.user.id = :users_id, t.trainingType.id =: trainingType WHERE t.id = :id",
            nativeQuery = false)

    void updateTrainee(@Param("users_id") Long usersId,
                       @Param("id") Long trainingType,
                       @Param("id") Long id);
}
