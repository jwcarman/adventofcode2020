package adventofcode.io;

import java.io.IOException;

public class InputException extends RuntimeException {
    public InputException(IOException cause, String message, Object... params) {
        super(String.format(message, params), cause);
    }
}
