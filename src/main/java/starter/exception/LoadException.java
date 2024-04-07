package starter.exception;

import java.io.IOException;

public class LoadException extends RuntimeException {
    public LoadException(Throwable throwable) {
        super(throwable);
    }
}
