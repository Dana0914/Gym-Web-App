package epam.com.gymapplication.customexception;


public class ServiceException extends RuntimeException {
    public ServiceException(String message) {
        super(message);
    }
}
