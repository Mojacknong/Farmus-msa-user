package modernfarmer.server.farmususer.global.exception;

import lombok.*;
import org.springframework.http.HttpStatus;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@ToString
public class ErrorResponse {

    private HttpStatus code;
    private String message;
}
