package modernfarmer.server.farmususer.global.exception.badrequest;


import static modernfarmer.server.farmususer.global.exception.CustomExceptionContext.FIREBASE_CONFIG_EXCEPTION;

public class FirebaseConfigException extends BadRequestException {
    public FirebaseConfigException(){
        super(FIREBASE_CONFIG_EXCEPTION);
    }
}