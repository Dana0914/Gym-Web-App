package epam.com.gymapplication.dao;


import epam.com.gymapplication.entity.Training;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


@Repository
public interface TrainingRepository extends CrudRepository<Training, Long> {


    @Query(value = "select t from Training t where t.trainingName =:trainingName", nativeQuery = false)
    Optional<Training> findByTrainingName(@Param("trainingName") String trainingName);

    @Query(value = "select t from Training t where t.trainingType =:trainingType", nativeQuery = false)
    Optional<Training> findByTrainingType(@Param("trainingType") String trainingType);
}
