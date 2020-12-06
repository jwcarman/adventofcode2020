package adventofcode.io;

public class InputException extends RuntimeException {
    public InputException(Exception cause, String message, Object... params) {
        super(String.format(message, params), cause);
    }
}
