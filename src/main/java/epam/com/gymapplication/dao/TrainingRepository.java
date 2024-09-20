package epam.com.gymapplication.dao;


import epam.com.gymapplication.model.Training;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;


@Repository
public interface TrainingRepository extends CrudRepository<Training, Long> {

    @Query(value = "select t FROM Training t " +
            "where t.trainee.user.username =:username " +
            "AND t.trainingDate BETWEEN :from AND :to " +
            "AND t.trainingType.trainingTypeName =: trainingType",
            nativeQuery = false)

    List<Training> findTrainingListByTraineeCriteria(@Param("username") String username,
                                                     @Param("from") LocalDate from,
                                                     @Param("to") LocalDate to,
                                                     @Param("trainingType") String trainingTypeName);


    @Query(value = "select t FROM Training t " +
            "where t.trainer.user.username =:username " +
            "AND t.trainingDate BETWEEN :from AND :to " +
            "AND t.trainingType.trainingTypeName =: trainingType",
            nativeQuery = false)

    List<Training> findTrainingListByTrainerCriteria(@Param("username") String username,
                                                     @Param("from") LocalDate from,
                                                     @Param("to") LocalDate to,
                                                     @Param("trainingType") String trainingTypeName);


    @Query(value = "select t from Training t where t.trainingName =: trainingName", nativeQuery = false)
    Training findByTrainingName(@Param("trainingName") String trainingName);

    @Query(value = "select t from Training t where t.trainingType =: trainingType", nativeQuery = false)
    Training findByTrainingType(@Param("trainingType") String trainingType);
}
