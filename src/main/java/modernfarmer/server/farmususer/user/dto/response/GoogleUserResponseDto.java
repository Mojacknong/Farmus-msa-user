package modernfarmer.server.farmususer.user.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class GoogleUserResponseDto {

    private String id;
    private String email;
    private String picture;
    private String name;
}
