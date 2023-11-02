package modernfarmer.server.farmususer.user.dto.response;


import lombok.*;
import lombok.experimental.SuperBuilder;

@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
@SuperBuilder
@Getter
public class ResponseDto {
    private int code;
    private String message;
}