package com.company.proxy.exceptions

import org.springframework.dao.DuplicateKeyException
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(DuplicateKeyException)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    Map<String, String> handleDuplicateKeyException(DuplicateKeyException e) {
        return [error: "Login already exists! Please choose a different one."]
    }

    @ExceptionHandler(NoSuchElementException)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    Map<String, String> handleNoSuchElementException(NoSuchElementException e) {
        return [error: "User's Id or Login is wrong! Please, verify it exists."]
    }

    @ExceptionHandler(MessageEmpty)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    Map<String, String> handleMessageEmpty(MessageEmpty e) {
        return [error: "Post or Comment message is empty, please review it."]
    }

}
