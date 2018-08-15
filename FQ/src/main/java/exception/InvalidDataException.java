package exception;

import org.hibernate.service.spi.ServiceException;

public class InvalidDataException extends ServiceException {

    private static final long serialVersionUID = -2802830718801003023L;

    public InvalidDataException(String msg) {
        super(msg);
    }

    public InvalidDataException(String message, Throwable cause) {
        super(message, cause);
    }
}
