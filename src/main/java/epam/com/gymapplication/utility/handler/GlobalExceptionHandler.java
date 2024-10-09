package epam.com.gymapplication.utility.handler;



import epam.com.gymapplication.utility.RestErrorResponse;
import epam.com.gymapplication.utility.exception.BadRequestException;
import epam.com.gymapplication.utility.exception.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;


@RestControllerAdvice(basePackages = "epam.com.gymapplication.controller")
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    RestErrorResponse handleEntityNotFoundException(EntityNotFoundException e) {
        return new RestErrorResponse(HttpStatus.NOT_FOUND.value(), e.getMessage(),
                LocalDateTime.now());
    }

    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    RestErrorResponse handleBadRequestException(BadRequestException e) {
        return new RestErrorResponse(HttpStatus.BAD_REQUEST.value(), e.getMessage(),
                LocalDateTime.now());
    }






}
