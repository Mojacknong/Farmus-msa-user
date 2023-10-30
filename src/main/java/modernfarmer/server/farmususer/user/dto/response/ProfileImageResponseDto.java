package modernfarmer.server.farmususer.user.dto.response;


import lombok.*;
import lombok.experimental.SuperBuilder;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@SuperBuilder
public class ProfileImageResponseDto extends  ResponseDto{

    private String profileImage;
}
