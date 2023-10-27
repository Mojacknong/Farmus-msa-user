package modernfarmer.server.farmususer.global.exception.badrequest;


import lombok.Getter;
import modernfarmer.server.farmususer.global.exception.ExceptionContext;
import modernfarmer.server.farmususer.global.exception.ModernFarmerException;

@Getter
public class BadRequestException extends ModernFarmerException {
    public BadRequestException(ExceptionContext context){
        super(context.getHttpStatus(), context.getMessage(), context.getCode());
    }
}
