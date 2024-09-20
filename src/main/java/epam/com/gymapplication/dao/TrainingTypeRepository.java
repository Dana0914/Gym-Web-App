package epam.com.gymapplication.dao;

import epam.com.gymapplication.model.TrainingType;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface TrainingTypeRepository extends CrudRepository<TrainingType, Long> {

    void update(TrainingType trainingType);

    @Query(value = "select t from TrainingType t where t.trainingTypeName =: trainingTypeName", nativeQuery = false)
    TrainingType findByTrainingTypeName(@Param("trainingTypeName") String trainingTypeName);
}
