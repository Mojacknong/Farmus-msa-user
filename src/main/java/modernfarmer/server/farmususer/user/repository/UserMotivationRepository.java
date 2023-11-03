package modernfarmer.server.farmususer.user.repository;

import modernfarmer.server.farmususer.user.entity.User;
import modernfarmer.server.farmususer.user.entity.UserMotivation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UserMotivationRepository extends JpaRepository<UserMotivation, Long> {
}
