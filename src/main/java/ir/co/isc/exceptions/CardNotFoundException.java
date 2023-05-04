package ir.co.isc.exceptions;

public class CardNotFoundException extends RuntimeException {
    public CardNotFoundException() {
    }

    public CardNotFoundException(String message) {
        super(message);
    }
}

