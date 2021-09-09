package eu.senla.peopleflowtask.controller.exceptionhandler;

import eu.senla.peopleflowtask.exception.CannotChangeStateException;
import eu.senla.peopleflowtask.exception.EmployeeNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@Slf4j
public class PeopleFlowExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler
    protected ResponseEntity<Object> handleException(Exception ex) {
        log.error("ERROR: " + ex.getMessage());
        return ResponseEntity.internalServerError().build();
    }

    @ExceptionHandler(value = {EmployeeNotFoundException.class, CannotChangeStateException.class})
    protected ResponseEntity<Object> handleException(RuntimeException ex) {
        log.error("ERROR: " + ex.getMessage());
        return ResponseEntity.notFound().build();
    }
}
