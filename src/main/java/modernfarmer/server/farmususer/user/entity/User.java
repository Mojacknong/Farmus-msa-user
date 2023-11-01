package modernfarmer.server.farmususer.user.entity;


import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.Instant;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class User extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id", nullable = false)
    private Long id;

    @Size(max = 45)
    @Column(name = "username", length = 45)
    private String username;

    @Size(max = 45)
    @Column(name = "nickname", length = 45)
    private String nickname;

    @Size(max = 255)
    @Column(name = "profile_image")
    private String profileImage;

    @Size(max = 6)
    @NotNull
    @Column(name = "roles", nullable = false, length = 6)
    private String roles;

    @Size(max = 30)
    @NotNull
    @Column(name = "user_number", nullable = false, length = 30)
    private String userNumber;

    @Size(max = 100)
    @Column(name = "firebase_token", length = 100)
    private String firebaseToken;

    @Size(max = 40)
    @Column(name = "motivation", length = 40)
    private String motivation;

    @Size(max = 10)
    @Column(name = "level", length = 10)
    private String level;


    @OneToMany(mappedBy = "user")
    private Set<UserFirebaseToken> userFirebaseTokens = new LinkedHashSet<>();



}