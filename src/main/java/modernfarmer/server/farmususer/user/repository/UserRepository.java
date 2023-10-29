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
    String findUserRole(@Param("userId") Long userId);

    @Query("update User as u set u = :nickName where u.id = :userId")
    void updateUserNickname(@Param("nickName") String nickName, @Param("userId") Long userId);

    @Query("delete from User as u where u.id = : userId")
    void deleteUser(@Param("userId") Long userId);

    @Query("select u.profileImage from User as u where u.id = :userId")
    String selectUserProfileImage(@Param("userId") Long userId);





}