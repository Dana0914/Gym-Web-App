package epam.com.gymapplication.dao;

import epam.com.gymapplication.entity.TrainingType;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


@Repository
public interface TrainingTypeRepository extends CrudRepository<TrainingType, Long> {

    @Modifying
    @Transactional
    @Query(value = "update TrainingType t set t.trainingTypeName =:trainingTypeName where t.id =:id", nativeQuery = false)
    void update(@Param("trainingTypeName") String trainingTypeName, @Param("id") Long id);

    @Query(value = "select t from TrainingType t where t.trainingTypeName =:trainingTypeName", nativeQuery = false)
    Optional<TrainingType> findByTrainingTypeName(@Param("trainingTypeName") String trainingTypeName);
}
