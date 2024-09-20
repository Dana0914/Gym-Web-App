package epam.com.gymapplication.customexception;

public class ProfileIsInActiveException extends RuntimeException {
    public ProfileIsInActiveException(String message) {
        super(message);
    }
}
