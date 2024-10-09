package epam.com.gymapplication.dao;


import epam.com.gymapplication.entity.Trainee;
import epam.com.gymapplication.entity.Training;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


import java.time.LocalDate;
import java.util.List;
import java.util.Optional;



@Repository
public interface TraineeRepository extends CrudRepository<Trainee, Long> {

    @Query(value = "select t FROM Trainee t join t.trainers t2 join t.trainings tt join t2.trainingType t3 " +
            "where t.user.username =:username " +
            "AND t2.user.firstname =:trainerName " +
            "AND tt.trainingDate BETWEEN :from " +
            "AND :to " +
            "AND t3.trainingTypeName =:trainingType",
            nativeQuery = false)

    List<Training> findTraineesTrainingList(@Param("username") String username,
                                            @Param("from") LocalDate from,
                                            @Param("to") LocalDate to,
                                            @Param("trainerName") String trainerName,
                                            @Param("trainingType") String trainingType);


    @Query(value = "select t from Trainee t where t.user.firstname = :firstname", nativeQuery = false)
    Optional<Trainee> findByFirstName(@Param("firstname") String firstName);

    @Query(value = "select t from Trainee t where t.user.lastname = :lastname", nativeQuery = false)
    Optional<Trainee> findByLastName(@Param("lastname") String lastName);

    @Query(value = "select t from Trainee t where t.user.username = :username", nativeQuery = false)
    Optional<Trainee> findByUsername(@Param("username") String username);



}
