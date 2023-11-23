package modernfarmer.server.farmususer.user.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
@Getter
public class GetUserResponseDto {

    private String nickName;
    private String userImageUrl;
    private long dDay;
}
