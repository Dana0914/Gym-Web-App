package epam.com.gymapplication.dao;


import epam.com.gymapplication.entity.Training;
import epam.com.gymapplication.entity.TrainingType;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


@Repository

public interface TrainingRepository extends CrudRepository<Training, Long> {

    @Modifying
    @Transactional
    @Query(value = "update Training t set t.trainingName =: trainingName, " +
            "t.trainingDate =: trainingDate, " +
            "t.trainingType.trainingTypeName =: trainingTypeName, " +
            "t.trainingDuration =: trainingDuration " +
            "where t.id =: id", nativeQuery = false)
    void updateTraining(@Param("trainingName") String trainingName,
                        @Param("trainingDate") LocalDate trainingDate,
            @Param("trainingTypeName") String trainingTypeName,
            @Param("trainingDuration") Integer trainingDuration);


    @Query(value = "select t FROM Training t join t.trainee t2 join t.trainer t3 join t.trainingType tt " +
            "where t2.user.username =:username " +
            "AND t3.user.firstname =:trainerName " +
            "AND t.trainingDate BETWEEN :from " +
            "AND :to " +
            "AND tt.trainingTypeName =:trainingType",
            nativeQuery = false)


    List<Training> findTraineesTrainingList(@Param("username") String username,
                                            @Param("from") LocalDate from,
                                            @Param("to") LocalDate to,
                                            @Param("trainerName") String trainerName,
                                            @Param("trainingType") String trainingType);



    @Query(value = "select t FROM Training t join t.trainer t2 join t.trainee t3 join t.trainingType tt " +
            "where t2.user.username =:username " +
            "AND t3.user.firstname =:traineeName " +
            "AND t.trainingDate BETWEEN :from " +
            "AND :to " +
            "AND tt.trainingTypeName =:trainingType",
            nativeQuery = false)

    List<Training> findTrainingListByTrainerCriteria(@Param("username") String username,
                                                     @Param("from") LocalDate from,
                                                     @Param("to") LocalDate to,
                                                     @Param("traineeName") String traineeName,
                                                     @Param("trainingType") String trainingType);


    @Query(value = "select t from Training t where t.trainingName =:trainingName", nativeQuery = false)
    Optional<Training> findByTrainingName(@Param("trainingName") String trainingName);

    @Query(value = "select t from Training t where t.trainingType =:trainingType", nativeQuery = false)
    Optional<Training> findByTrainingType(@Param("trainingType") String trainingType);
}
