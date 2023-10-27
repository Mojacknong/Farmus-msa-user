package modernfarmer.server.farmususer.global.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum CustomExceptionContext implements ExceptionContext{
    // MEMBER (예시)
    NOT_FOUND_MEMBER_ERROR(HttpStatus.NOT_FOUND, 1000, "해당 회원을 찾을 수 없습니다."),
    INVALID_MEMBER_ID_ERROR(HttpStatus.BAD_REQUEST, 1001, "회원에 대한 유효하지 않은 요청입니다."),
    NOT_FOUND_ALARM_ERROR(HttpStatus.NOT_FOUND, 1002, "해당 알람 정보를 찾을 수 없습니다."),
    INVALID_ADD_MEMBER(HttpStatus.BAD_REQUEST, 1003, "이미 존재하는 캐릭터입니다."),
    SDK_NOT_FOUND(HttpStatus.BAD_REQUEST, 1007, "해당 경로에 Firebase SDK json 파일이 존재하지 않아, Firebase 시스템을 초기화 할 수 없습니다."),
    FIREBASE_TOKEN_EXCEPTION(HttpStatus.BAD_REQUEST, 1004, "파이어베이스 토큰 오류입니다."),
    FIREBASE_CONFIG_EXCEPTION(HttpStatus.BAD_REQUEST, 1005, "파이어베이스 설정 오류입니다."),
    NOT_FOUND_MEDICINE_ERROR(HttpStatus.NOT_FOUND, 1006, "해당 약 정보를 찾을 수 없습니다.");
    // DOMAIN PER EXCEPTIONS ...


    private final HttpStatus httpStatus;
    private final int code;
    private final String message;
}
