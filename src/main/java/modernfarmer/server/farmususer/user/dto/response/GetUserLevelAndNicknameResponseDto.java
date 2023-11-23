package modernfarmer.server.farmususer.user.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor(staticName = "of")
@NoArgsConstructor
@Getter
public class GetUserLevelAndNicknameResponseDto {

    private String level;
    private String nickname;
    private String motivation;
}
