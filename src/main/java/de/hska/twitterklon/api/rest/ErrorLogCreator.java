package de.hska.twitterklon.api.rest;

import java.util.Optional;
import java.util.UUID;

import de.hska.twitterklon.api.transferobjects.ErrorResultTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ErrorLogCreator {

    private static final Logger logger = LoggerFactory.getLogger(ErrorLogCreator.class);

    private final HttpStatus httpStatus;
    private Optional<String> description = Optional.empty();
    private String incidentId;

    public static ErrorLogCreator create(HttpStatus status) {
        return new ErrorLogCreator(status, null);
    }

    public static ErrorLogCreator create(HttpStatus status, String description) {
        return new ErrorLogCreator(status, description);
    }

    private ErrorLogCreator(HttpStatus httpStatus, String description) {
        this.httpStatus = httpStatus;
        this.description = Optional.ofNullable(description);
        incidentId = UUID.randomUUID().toString();
    }

    public ErrorResultTO createErrorResultTO() {
        ErrorResultTO result = new ErrorResultTO();
        result.incidentId = incidentId;
        result.errorCode = httpStatus.value();
        result.description = description.orElse(httpStatus.getReasonPhrase());
        return result;
    }

    public ResponseEntity<ErrorResultTO> createResponseEntity() {
        return new ResponseEntity<>(createErrorResultTO(), httpStatus);
    }

    public ErrorLogCreator log() {
        logger.error(String.format("incident: %s, description: %s, status: %s", this.incidentId, this.description.orElse("null"), this
                .httpStatus));
        return this;
    }
}
