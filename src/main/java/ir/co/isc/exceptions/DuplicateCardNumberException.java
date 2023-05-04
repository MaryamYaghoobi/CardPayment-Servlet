package ir.co.isc.exceptions;

public class DuplicateCardNumberException extends RuntimeException {
    public DuplicateCardNumberException() {
    }

    public DuplicateCardNumberException(String message) {
        super(message);
    }
}

