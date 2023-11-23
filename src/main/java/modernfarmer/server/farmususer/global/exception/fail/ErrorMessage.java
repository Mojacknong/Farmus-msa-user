package modernfarmer.server.farmususer.global.exception.fail;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.*;

@Getter
public enum ErrorMessage {

    INTERVAL_SERVER_ERROR(1001,  "요청을 처리하는 과정에서 서버가 예상하지 못한 오류가 발생하였습니다."),
    REFRESH_NOTIFICATION_ERROR(1002,  "Refresh Token 인증 오류"),
    NO_USER_DATA(1003,  "유저에 대한 정보가 없습니다."),
    NO_MOTIVATION_DATA(1004,  "동기에 대한 정보가 없습니다."),
    FAIL_GET_INFORMATION(1005,  "소셜에서 정보를 가져오는데 실패했습니다.");



    private final int code;
    private final String message;

    ErrorMessage(int code, String message) {
        this.code = code;
        this.message = message;
    }
}