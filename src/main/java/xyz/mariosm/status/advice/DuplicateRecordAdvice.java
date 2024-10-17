package xyz.mariosm.status.advice;

import org.springframework.hateoas.mediatype.problem.Problem;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import xyz.mariosm.status.exception.DuplicateRecordException;

@RestControllerAdvice
public class DuplicateRecordAdvice {
    @ExceptionHandler(DuplicateRecordException.class)
    ResponseEntity<?> duplicateRecordException(DuplicateRecordException exception) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                             .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_PROBLEM_JSON_VALUE)
                             .body(Problem.create()
                                          .withTitle("Record Conflict")
                                          .withDetail(exception.getMessage()));
    }
}
