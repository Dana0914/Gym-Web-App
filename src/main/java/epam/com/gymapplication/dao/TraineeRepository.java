package epam.com.gymapplication.dao;


import epam.com.gymapplication.entity.Trainee;
import epam.com.gymapplication.entity.Trainer;
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
public interface TraineeRepository extends CrudRepository<Trainee, Long> {



    @Modifying
    @Transactional
    @Query(value = "update Trainee t set t.trainers =: trainers where t.user.username =: username", nativeQuery = false)
    void updateTraineesTrainerList(
            @Param("trainers")
            List<Trainer> trainers,
            @Param("username")
            String username);

    @Query(value = "select t from Trainee t where t.user.firstname = :firstname", nativeQuery = false)
    Optional<Trainee> findByFirstName(@Param("firstname") String firstName);

    @Query(value = "select t from Trainee t where t.user.lastname = :lastname", nativeQuery = false)
    Optional<Trainee> findByLastName(@Param("lastname") String lastName);

    @Query(value = "select t from Trainee t where t.user.username = :username", nativeQuery = false)
    Optional<Trainee> findByUsername(@Param("username") String username);

    @Modifying
    @Transactional
    @Query(value = "UPDATE trainee SET users_id = :users_id, address = :address, date_of_birth = :date_of_birth WHERE id = :id",
            nativeQuery = true)
    void updateTrainee(@Param("id") Long id,
                       @Param("users_id") Long usersId,
                       @Param("address") String address,
                       @Param("date_of_birth") LocalDate dateOfBirth);
}
