package modernfarmer.server.farmususer.user.repository;

import io.lettuce.core.dynamic.annotation.Param;
import modernfarmer.server.farmususer.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUserNumber(String usernumber);
    @Query("SELECT a.roles FROM User AS a  WHERE a.id = :userId")
    String findUserRole(@Param("userId") Long userId);


    @Modifying
    @Query("delete from User as u where u.id = :userId")
    void deleteUser(@Param("userId") Long userId);

    @Query("select u.profileImage from User as u where u.id = :userId")
    String selectUserProfileImage(@Param("userId") Long userId);

    @Modifying
    @Query("update User as u set u.nickname = :nickName where u.id = :userId")
    void updateUserNickname(@Param("nickName") String nickName, @Param("userId") Long userId);

    @Modifying
    @Query("update User as u set u.early = 0 where u.id = :userId")
    void updateEarly( @Param("userId") Long userId);

    @Modifying
    @Query("update User as u set u.profileImage = null where u.id = :userId")
    void updateUserProfileDefault( @Param("userId") Long userId);


//    @Modifying
//    @Query("update User as u set u.profileImage = :profileImage where u.id = :userId")
//    void emitUserProfileImage(@Param("userId") Long userId, @Param("profileImage") String profileImage);


    @Modifying
    @Query("update User as u set u.profileImage = :profileImage, u.nickname = :nickName where u.id = :userId")
    void selectUserProfileAndNickname(@Param("userId") Long userId, @Param("profileImage") String profileImage, @Param("nickName") String nickName);



    @Modifying
    @Query("update User as u set u.level= :level where u.id = :userId")
    void insertUserLevel(@Param("userId") Long userId, @Param("level") String level);

    List<User> findAllBy();

    Optional<User> findById(Long userId);










}