package pl.alfacode.dnbloans.rest;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import pl.alfacode.dnbloans.service.ResourceNotFoundException;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    private static final String STATUS = "status";

    private static final String ERROR = "error";

    private static final String MESSAGE = "message";

    private static final String PATH = "path";

    private static final String TIMESTAMP = "timestamp";


    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> allExceptionHandler(Exception ex, WebRequest request) {
        return handleExceptionInternal(ex,
                body(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, request),
                new HttpHeaders(),
                HttpStatus.INTERNAL_SERVER_ERROR,
                request);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Object> resourceNotFoundExceptionHandler(ResourceNotFoundException ex, WebRequest request) {
        return handleExceptionInternal(ex,
                body(HttpStatus.NOT_FOUND, ex.getMessage(), HttpStatus.NOT_FOUND, request),
                new HttpHeaders(),
                HttpStatus.NOT_FOUND,
                request);
    }


    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers,
                                                                  HttpStatus status, WebRequest request) {
        ObjectError error = ex.getBindingResult().getAllErrors().get(0);
        return handleExceptionInternal(ex,
                body(error.getObjectName(), error.getDefaultMessage(), status, request),
                headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleBindException(BindException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        ObjectError error = ex.getBindingResult().getAllErrors().get(0);
        log.error("Exception:", ex);
        return super.handleExceptionInternal(ex,
                body(error.getObjectName(), error.getDefaultMessage(), status, request),
                headers, status, request);
    }


    private Map<String, Object> body(Object code, String message, HttpStatus status, WebRequest request) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put(STATUS, status.value());
        body.put(ERROR, code);
        body.put(MESSAGE, message);
        body.put(PATH, extractUri(request));
        body.put(TIMESTAMP, LocalDateTime.now());

        return body;
    }

    private String extractUri(WebRequest request) {
        HttpServletRequest req = (HttpServletRequest) request.resolveReference(WebRequest.REFERENCE_REQUEST);
        return req.getRequestURI();
    }
}
