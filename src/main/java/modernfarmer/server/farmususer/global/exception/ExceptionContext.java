package modernfarmer.server.farmususer.global.exception;

import org.springframework.http.HttpStatus;

public interface ExceptionContext {
    HttpStatus getHttpStatus();

    String getMessage();

    int getCode();
}
