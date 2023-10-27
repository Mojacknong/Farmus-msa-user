package modernfarmer.server.farmususer.global.exception;

import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@ToString
public class ErrorResponse {

    private int code;
    private String message;
}
