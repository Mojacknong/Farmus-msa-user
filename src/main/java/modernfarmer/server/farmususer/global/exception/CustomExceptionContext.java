package modernfarmer.server.farmususer.global.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum CustomExceptionContext implements ExceptionContext{


    FIREBASE_CONFIG_EXCEPTION(HttpStatus.BAD_REQUEST, 1005, "파이어베이스 설정 오류입니다."),
    NOT_FOUND_MEDICINE_ERROR(HttpStatus.NOT_FOUND, 1006, "해당 약 정보를 찾을 수 없습니다."),
    REFRESH_TOKEN_DIFFERENT(HttpStatus.BAD_REQUEST , 1000,"접근이 올바르지 않습니다.");
    // DOMAIN PER EXCEPTIONS ...


    private final HttpStatus httpStatus;
    private final int code;
    private final String message;
}
