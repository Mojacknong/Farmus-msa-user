package modernfarmer.server.farmususer.user.dto.response;


import lombok.*;
import lombok.experimental.SuperBuilder;

@NoArgsConstructor
@AllArgsConstructor
@ToString
@SuperBuilder
@Getter
public class TokenResponseDto extends  ResponseDto{

    private String accessToken;

    private String refreshToken;

    private boolean early;
}