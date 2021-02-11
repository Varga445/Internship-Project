package eu.accesa.learningplatform.controller;

import eu.accesa.learningplatform.exceptionhandler.LearningPlatformException;
import eu.accesa.learningplatform.service.exception.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class ExceptionHandlerControllerAdvice {

    private final static Logger logger = LoggerFactory.getLogger(ExceptionHandlerControllerAdvice.class);

    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(EntityNotFoundException.class)
    public String handleEntityNotFoundException(EntityNotFoundException exception) {
        logger.warn(exception.getMessage());
        return exception.getMessage();
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(LearningPlatformException.class)
    public String handleLearningPlatformException(LearningPlatformException learningPlatformException) {
        return learningPlatformException.getMessage();
    }

    @ResponseBody
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String handleException(Exception exception) {
        logger.warn(exception.getMessage(), exception);
        return exception.getMessage();
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public List<String> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
        List<ObjectError> allErrors = exception.getBindingResult().getAllErrors();
        List<String> body = new ArrayList<>();

        for (ObjectError object : allErrors) {
            body.add(object.getDefaultMessage());
        }
        return body;
    }
}