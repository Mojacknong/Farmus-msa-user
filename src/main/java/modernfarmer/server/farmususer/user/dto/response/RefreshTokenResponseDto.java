package modernfarmer.server.farmususer.user.dto.response;


import lombok.*;

@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
@ToString
@Builder
@Getter
public class RefreshTokenResponseDto {

    private String accessToken;

    private String refreshToken;
}
