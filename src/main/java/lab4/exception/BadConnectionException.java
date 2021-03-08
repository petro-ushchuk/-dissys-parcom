package lab4.exception;

public class BadConnectionException extends Exception{
    public BadConnectionException(String message) {
        super(message, new Throwable());
    }
}
