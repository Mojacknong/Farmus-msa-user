package modernfarmer.server.farmususer.user.dto.response;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;
import modernfarmer.server.farmususer.global.exception.fail.ErrorMessage;
import org.springframework.http.HttpStatus;

import static modernfarmer.server.farmususer.global.exception.success.SuccessMessage.SUCCESS;




@Getter
@JsonPropertyOrder({"code", "isSuccess", "message", "result"})
public class BaseResponseDto<T> {
    private final int code;
    @JsonProperty("isSuccess")
    private final Boolean isSuccess;
    private final String message;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T result;

    // 요청에 성공한 경우
    public BaseResponseDto(T result) {
        this.code = HttpStatus.OK.value();
        this.isSuccess = SUCCESS.isSuccess();
        this.message = SUCCESS.getMessage();
        this.result = result;
    }

    public BaseResponseDto(ErrorMessage errorMessage) {
        this.code = errorMessage.getCode();
        this.isSuccess = errorMessage.isSuccess();
        this.message = errorMessage.getMessage();
    }

    public BaseResponseDto(int code, Boolean isSuccess, String errorMessage) {
        this.code = code;
        this.isSuccess = isSuccess;
        this.message = errorMessage;
    }
}