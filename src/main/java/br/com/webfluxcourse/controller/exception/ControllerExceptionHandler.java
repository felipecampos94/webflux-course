package br.com.webfluxcourse.controller.exception;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import reactor.core.publisher.Mono;

import static java.time.LocalDateTime.now;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

@ControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(DuplicateKeyException.class)
    ResponseEntity<Mono<StandardError>> duplicateKeyException(DuplicateKeyException e, ServerHttpRequest request) {
        return ResponseEntity.badRequest()
                .body(Mono.just(
                        StandardError.builder()
                                .timestamp(now())
                                .status(BAD_REQUEST.value())
                                .error(BAD_REQUEST.getReasonPhrase())
                                .message(verifyDuplicateKey(e.getMessage()))
                                .path(request.getURI().getPath())
                                .build()
                ));
    }

    private String verifyDuplicateKey(String message) {
        if (message.contains("email dup key")) {
            return "Email already registered";
        }
        return "Duplicate Key Exception";
    }
}
