package epam.com.gymapplication.utility.handler;

import epam.com.gymapplication.utility.RestErrorResponse;
import epam.com.gymapplication.utility.exception.ResourceNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;


@RestControllerAdvice(basePackages = "epam.com.gymapplication.controller")
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    ResponseEntity<RestErrorResponse> handleResourceNotFoundException(ResourceNotFoundException e) {

        logger.error("ResourceNotFoundException occurred: ", e);

        RestErrorResponse response = new RestErrorResponse();
        response.setStatus(HttpStatus.NOT_FOUND.value());
        response.setMessage("An unexpected error occurred: " + e.getMessage());
        response.setTimestamp(LocalDateTime.now());

        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);

    }

//    @ExceptionHandler(MethodArgumentNotValidException.class)
//    @ResponseStatus(HttpStatus.BAD_REQUEST)
//    ResponseEntity<RestErrorResponse> handleValidationException(MethodArgumentNotValidException e) {
//        logger.error("MethodArgumentNotValidException occurred: ", e);
//
//
//        Map<String, String> errors = new HashMap<>();
//        e.getBindingResult().getFieldErrors().forEach(error ->
//                errors.put(error.getField(), error.getDefaultMessage())
//        );
//
//
//        RestErrorResponse apiError = new RestErrorResponse(
//                HttpStatus.BAD_REQUEST.value(),
//                "Validation failed for the request",
//                LocalDateTime.now(),
//                errors
//        );
//
//
//        return ResponseEntity.badRequest().body(apiError);
//
//    }


    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<?> globuleExceptionHandler(Exception ex) {
        logger.error("Exception occurred: ", ex);

        RestErrorResponse response = new RestErrorResponse();
        response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        response.setMessage("An unexpected error occurred. Please try again later: " + ex.getMessage());
        response.setTimestamp(LocalDateTime.now());

        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }






}
