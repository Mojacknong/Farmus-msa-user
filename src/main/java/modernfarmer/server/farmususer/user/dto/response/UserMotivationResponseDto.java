package modernfarmer.server.farmususer.user.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor(staticName = "of")
@NoArgsConstructor
@Getter
public class UserMotivationResponseDto {

    private String motivation;
}
