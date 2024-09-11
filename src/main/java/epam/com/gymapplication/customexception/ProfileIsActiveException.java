package epam.com.gymapplication.customexception;

public class ProfileIsActiveException extends RuntimeException {
    public ProfileIsActiveException(String message) {
        super(message);
    }
}
