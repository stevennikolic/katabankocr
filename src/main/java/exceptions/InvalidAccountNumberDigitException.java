package exceptions;

public class InvalidAccountNumberDigitException extends Exception {
    public InvalidAccountNumberDigitException(String message) {
        super(message);
    }
}