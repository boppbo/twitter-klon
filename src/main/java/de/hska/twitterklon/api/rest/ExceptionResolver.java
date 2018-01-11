package de.hska.twitterklon.api.rest;

import de.hska.twitterklon.api.exceptions.LoginFailedException;
import de.hska.twitterklon.api.exceptions.ResourceNotFoundException;
import de.hska.twitterklon.api.transferobjects.ErrorResultTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.management.openmbean.KeyAlreadyExistsException;
import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class ExceptionResolver {
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResultTO> resolveResourceNotFoundException(ResourceNotFoundException ex) {
        return ErrorLogCreator.create(HttpStatus.NOT_FOUND, ex.getMessage()).log().createResponseEntity();
    }

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

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ErrorResultTO> resolveMissingServletRequestParamterException(MissingServletRequestParameterException ex) {
        return ErrorLogCreator.create(HttpStatus.BAD_REQUEST, ex.getMessage()).log().createResponseEntity();
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResultTO> resolveException(HttpServletRequest request, Exception ex) {
        return ErrorLogCreator.create(HttpStatus.INTERNAL_SERVER_ERROR, null, ex.getMessage())
                .log().createResponseEntity();
    }
}
