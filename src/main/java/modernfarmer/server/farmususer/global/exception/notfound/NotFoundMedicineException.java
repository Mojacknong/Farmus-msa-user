package modernfarmer.server.farmususer.global.exception.notfound;


import modernfarmer.server.farmususer.global.exception.ModernFarmerException;

import static modernfarmer.server.farmususer.global.exception.CustomExceptionContext.NOT_FOUND_MEDICINE_ERROR;

public class NotFoundMedicineException extends NotFoundException {
    public NotFoundMedicineException() {
        super(NOT_FOUND_MEDICINE_ERROR);
    }
}
