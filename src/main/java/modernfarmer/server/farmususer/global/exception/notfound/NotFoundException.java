package modernfarmer.server.farmususer.global.exception.notfound;


import lombok.Getter;
import modernfarmer.server.farmususer.global.exception.ExceptionContext;
import modernfarmer.server.farmususer.global.exception.ModernFarmerException;
import org.springframework.http.HttpStatus;

@Getter
public class NotFoundException extends ModernFarmerException {
    public NotFoundException(ExceptionContext context) {
        super(context.getHttpStatus(), context.getMessage(), context.getCode());
    }


}
