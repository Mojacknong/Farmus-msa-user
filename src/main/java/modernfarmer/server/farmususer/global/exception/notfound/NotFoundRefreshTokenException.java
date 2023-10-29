package modernfarmer.server.farmususer.global.exception.notfound;

import static modernfarmer.server.farmususer.global.exception.CustomExceptionContext.REFRESH_TOKEN_DIFFERENT;

public class NotFoundRefreshTokenException extends NotFoundException{

    public NotFoundRefreshTokenException() {
        super(REFRESH_TOKEN_DIFFERENT);
    }
}
