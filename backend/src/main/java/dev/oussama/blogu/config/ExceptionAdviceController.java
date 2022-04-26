package dev.oussama.blogu.config;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class ExceptionAdviceController extends ResponseEntityExceptionHandler {

    @ExceptionHandler({
        BadCredentialsException.class,
        AuthenticationException.class,
    })
    private ResponseEntity<ExceptionMessage> handleAllException(Exception ex) {
        return ResponseEntity.badRequest().body(new ExceptionMessage(ex.getLocalizedMessage()));
    }

    @ExceptionHandler({MaxUploadSizeExceededException.class})
    public ResponseEntity<ExceptionMessage> handleMaxSizeException(MaxUploadSizeExceededException e) {
        return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED)
                .body(new ExceptionMessage(e.getLocalizedMessage()));
    }

}
