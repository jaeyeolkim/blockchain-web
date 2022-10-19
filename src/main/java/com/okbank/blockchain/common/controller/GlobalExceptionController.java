package com.okbank.blockchain.common.controller;

import com.okbank.blockchain.common.payload.CommonResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.NoSuchElementException;

import static com.okbank.blockchain.common.constants.ResponseCode.BAD_REQUEST;

/**
 * 전엑 controller 에러 처리(400,401,404,500)
 */
@Slf4j
@ControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionController {

    // 400
    @ExceptionHandler({RuntimeException.class})
    public ResponseEntity<CommonResponse> handleBadRequestException(final RuntimeException ex) {
        log.error(ex.toString());
        return ResponseEntity.badRequest().body(CommonResponse.builder()
                .responseCode(BAD_REQUEST)
                .build()
        );
    }

    // 400 (Dto validate)
    @ExceptionHandler({BindException.class})
    public ResponseEntity<CommonResponse> handleBadRequestException(final BindException ex) {
        log.error(ex.toString());
        return ResponseEntity.badRequest().body(CommonResponse.builder()
                .responseCode(BAD_REQUEST)
                .build()
        );
    }

    // 401
    @ExceptionHandler({AccessDeniedException.class})
    public ResponseEntity<Void> handleAccessDeniedException(final AccessDeniedException ex) {
        log.error(ex.toString());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    // 404
    @ExceptionHandler({NoSuchElementException.class})
    public ResponseEntity<CommonResponse> handleNotFoundException(final NoSuchElementException ex) {
        log.error(ex.toString());
        return ResponseEntity.notFound().build();
    }

    // 500
    @ExceptionHandler({Exception.class})
    public ResponseEntity<Void> handleAll(final Exception ex) {
        log.error(ex.toString());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

}
