package modernfarmer.server.farmususer.user.dto.response;


import lombok.*;
import lombok.experimental.SuperBuilder;

@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
@Getter
public class ProfileImageResponseDto{

    private String profileImage;
}
