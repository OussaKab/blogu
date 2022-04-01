package dev.oussama.blogu.config;

import dev.oussama.blogu.web.exceptions.PostNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class ExceptionAdviceController extends ResponseEntityExceptionHandler {

    @ExceptionHandler({
            BadCredentialsException.class,
            AuthenticationException.class,
            PostNotFoundException.class
    })
    private ResponseEntity<ExceptionMessage> handleAllException(Exception ex) {
        return ResponseEntity.badRequest().body(new ExceptionMessage(ex.getLocalizedMessage()));
    }

}
