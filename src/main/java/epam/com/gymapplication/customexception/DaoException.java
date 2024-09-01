package epam.com.gymapplication.customexception;

public class DaoException extends RuntimeException {
    public DaoException(String message) {
        super(message);
    }
}
