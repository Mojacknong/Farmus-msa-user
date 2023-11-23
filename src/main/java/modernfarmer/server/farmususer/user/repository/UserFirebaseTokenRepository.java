package modernfarmer.server.farmususer.user.repository;


import io.lettuce.core.dynamic.annotation.Param;
import modernfarmer.server.farmususer.user.entity.User;
import modernfarmer.server.farmususer.user.entity.UserFirebaseToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface UserFirebaseTokenRepository extends JpaRepository<UserFirebaseToken, Long> {


    @Modifying
    @Query("delete from UserFirebaseToken as uf where uf.user = :user and uf.token = :firebaseToken ")
    void deleteFirebaseToken(@Param("user") User user, @Param("firebaseToken") String firebaseToken);
}
