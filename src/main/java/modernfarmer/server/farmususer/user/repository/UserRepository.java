package modernfarmer.server.farmususer.user.repository;

import modernfarmer.server.farmususer.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {



    Optional<User> findByUAndUsernumber(String userId);





}