package modernfarmer.server.farmususer.user.repository;

import io.lettuce.core.dynamic.annotation.Param;
import modernfarmer.server.farmususer.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {



    Optional<User> findByUsernumber(String usernumber);
    @Query("SELECT a.role FROM User AS a  WHERE a.id = :userId")
    User findUserRole(@Param("userId") Long userId);





}