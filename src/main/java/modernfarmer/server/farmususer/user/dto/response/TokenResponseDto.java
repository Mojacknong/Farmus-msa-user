package modernfarmer.server.farmususer.user.dto.response;


import lombok.*;
import lombok.experimental.SuperBuilder;

@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
@ToString
@Builder
@Getter
public class TokenResponseDto {

    private String accessToken;

    private String refreshToken;

    private boolean early;
}