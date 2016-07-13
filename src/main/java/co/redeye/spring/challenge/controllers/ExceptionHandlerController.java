package co.redeye.spring.challenge.controllers;

import co.redeye.spring.challenge.services.AuthenticationException;
import co.redeye.spring.challenge.views.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Global Handler for uncaught Exceptions.
 */
@ControllerAdvice
public class ExceptionHandlerController {
    @ExceptionHandler (value = AuthenticationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorResponse authenticationException(AuthenticationException e) {
        return new ErrorResponse(e.getMessage());
    }

    @ExceptionHandler (value = Throwable.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public ErrorResponse catchAll(Throwable e) {
        e.printStackTrace();
        return new ErrorResponse("An unexpected error has occurred.");
    }
}
