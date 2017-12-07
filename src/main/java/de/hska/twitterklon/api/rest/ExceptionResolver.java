package de.hska.twitterklon.api.rest;

import javax.management.openmbean.KeyAlreadyExistsException;
import javax.servlet.http.HttpServletRequest;

import de.hska.twitterklon.api.exceptions.LoginFailedException;
import de.hska.twitterklon.api.transferobjects.ErrorResultTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionResolver {

    @ExceptionHandler(LoginFailedException.class)
    public ResponseEntity<ErrorResultTO> resolveLoginFailedException(LoginFailedException ex) {
        return ErrorLogCreator.create(HttpStatus.UNAUTHORIZED, ex.getMessage()).log().createResponseEntity();
    }

    @ExceptionHandler(KeyAlreadyExistsException.class)
    public ResponseEntity<ErrorResultTO> resolveKeyAlreadyExistsException(KeyAlreadyExistsException ex ){
        return ErrorLogCreator.create(HttpStatus.CONFLICT, ex.getMessage()).log().createResponseEntity();
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ErrorResultTO> resolveHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException ex) {
        return ErrorLogCreator.create(HttpStatus.METHOD_NOT_ALLOWED, ex.getMessage()).log().createResponseEntity();
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResultTO> resolveException(HttpServletRequest request, Exception ex) {
        return ErrorLogCreator.create(HttpStatus.INTERNAL_SERVER_ERROR, null, ex.getMessage())
                .log().createResponseEntity();
    }
}
