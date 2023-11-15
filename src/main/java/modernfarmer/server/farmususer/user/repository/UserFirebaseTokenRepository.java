package modernfarmer.server.farmususer.user.repository;


import modernfarmer.server.farmususer.user.entity.UserFirebaseToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UserFirebaseTokenRepository extends JpaRepository<UserFirebaseToken, Long> {
}
