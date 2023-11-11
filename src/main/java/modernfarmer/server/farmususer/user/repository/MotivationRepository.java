package modernfarmer.server.farmususer.user.repository;


import modernfarmer.server.farmususer.user.entity.Motivation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MotivationRepository extends JpaRepository<Motivation, Long> {

    @Query("select m.id from Motivation  as m where m.motivationReason = :motivationReason")
    Long getMotivationId(@Param("motivationReason") String motivationReason);


    Optional<Motivation> findByMotivationReason(String motivationReason);



}
