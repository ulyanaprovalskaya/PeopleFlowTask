package eu.senla.peopleflowtask.exception;

public class CannotChangeStateException extends RuntimeException {

    public CannotChangeStateException(String message) {
        super(message);
    }
}
