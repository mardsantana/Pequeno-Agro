package br.com.agroorg.pequeno_agro.handler;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import lombok.Getter;
import lombok.extern.log4j.Log4j2;

@Getter
@Log4j2
public class APIException extends RuntimeException {
    private HttpStatus statusException;
    private ErrorApiResponse bodyException;

    private APIException(HttpStatus statusException, String message, Throwable e) {
        super(message, e);  // Usa Throwable para maior flexibilidade (exceções e causas)
        this.statusException = statusException;
        this.bodyException = ErrorApiResponse.builder()
                .message(message)
                .description(getDescription(message, e))
                .build();
    }

    public static APIException build(HttpStatus statusException, String message, Throwable e) {
        log.error("Exception: ", e);
        return new APIException(statusException, message, e);
    }

    public static APIException build(HttpStatus statusException, String message) {
        return new APIException(statusException, message, new Exception(message));
    }

    private String getDescription(String message, Throwable e) {
        if (e == null || e.getCause() == null) {
            return message;
        }
        return getMessageCause(e);
    }

    private static String getMessageCause(Throwable e) {
        return e.getCause() != null ? e.getCause().getMessage() : e.getMessage();
    }

    public ResponseEntity<ErrorApiResponse> buildErrorResponseEntity() {
        return ResponseEntity
                .status(statusException)
                .body(bodyException);
    }

    private static final long serialVersionUID = 1L;
}

