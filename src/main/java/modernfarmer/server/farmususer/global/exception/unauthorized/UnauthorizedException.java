package modernfarmer.server.farmususer.global.exception.unauthorized;


import modernfarmer.server.farmususer.global.exception.ModernFarmerException;
import org.springframework.http.HttpStatus;

public class UnauthorizedException  extends ModernFarmerException {

    public UnauthorizedException(String message, int code) {
        super(HttpStatus.UNAUTHORIZED, message, code);
    }
}