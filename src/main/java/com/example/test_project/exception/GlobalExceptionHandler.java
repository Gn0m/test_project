package com.example.test_project.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
/**
 * Класс глобального исключения
 *
 * @author L.Gushin
 * @version 2.0
 * @since 07/09/2023
 */
@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    /**
     * возвращает 404 статус запроса, а в теле statusCode: 404 и сообщение что по указанному Id ничего не найдено
     *
     * @param e исключение
     * @return {@code ResponseEntity<Error>}
     */
    @ExceptionHandler
    public ResponseEntity<Error> catchResourceNotFoundException(ResourceNotFoundException e) {
        log.error(e.getMessage(), e);
        return new ResponseEntity<>(new Error(HttpStatus.NOT_FOUND.value(), e.getMessage()), HttpStatus.NOT_FOUND);
    }
}
