package com.example.test_project.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Класс ошибки со статусом и сообщением
 *
 * @author L.Gushin
 * @version 2.0
 * @since 07/09/2023
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Error {
    /**
     * код ошибки
     */
    private int statusCode;
    /**
     * сообщение ошибки
     */
    private String message;


}
