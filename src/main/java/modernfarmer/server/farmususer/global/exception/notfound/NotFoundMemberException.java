package modernfarmer.server.farmususer.global.exception.notfound;

import modernfarmer.server.farmususer.global.exception.ModernFarmerException;

import static modernfarmer.server.farmususer.global.exception.CustomExceptionContext.NOT_FOUND_MEMBER_ERROR;


public class NotFoundMemberException extends NotFoundException {
    public NotFoundMemberException() {
        super(NOT_FOUND_MEMBER_ERROR);
    }
}
