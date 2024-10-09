package epam.com.gymapplication.dao;

import epam.com.gymapplication.entity.Trainer;
import epam.com.gymapplication.entity.Training;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface TrainerRepository extends CrudRepository<Trainer, Long> {
    @Query(value = "select t FROM Trainer t join t.trainees t2 join t.trainings tt join t.trainingType t3 " +
            "where t.user.username =:username " +
            "AND t2.user.firstname =:traineeName " +
            "AND tt.trainingDate BETWEEN :from " +
            "AND :to " +
            "AND t3.trainingTypeName =:trainingType",
            nativeQuery = false)

    List<Training> findTrainersTrainingList(@Param("username") String username,
                                            @Param("from") LocalDate from,
                                            @Param("to") LocalDate to,
                                            @Param("traineeName") String traineeName,
                                            @Param("trainingType") String trainingType);

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
