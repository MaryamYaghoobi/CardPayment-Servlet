package ir.co.isc.exceptions;

public class DontSaveCardsException extends RuntimeException {

    public DontSaveCardsException() {
    }

    public DontSaveCardsException(String message) {
        super(message);
    }
}
