package com.example.test_project.exception;
/**
 * Класс исключения о не найденном значении в бд
 *
 * @author L.Gushin
 * @version 2.0
 * @since 07/09/2023
 */
public class ResourceNotFoundException extends RuntimeException {
    /**
     * конструктор исключения
     *
     * @param message текст сообщения
     */
    public ResourceNotFoundException(String message) {
        super(message);
    }
}
