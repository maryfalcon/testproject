package com.example.testproject.exceptions;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestExceptionHandler
        extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = { NoteIdMismatchException.class})
    protected ResponseEntity<Object> handleNoteIdMismatch(
            NoteIdMismatchException ex) {
        String bodyOfResponse = "Id in the url doesn't match id in the body.";
        return new ResponseEntity<>(bodyOfResponse,
                 HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = { NoAccessToNoteException.class})
    protected ResponseEntity<Object> handleNoAccessToNote(
            NoAccessToNoteException ex) {
        String bodyOfResponse = "Access is denied to this note, because you're not the owner.";
        return new ResponseEntity<>(bodyOfResponse,
                HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(value = { NoteNotFoundException.class})
    protected ResponseEntity<Object> handleNoteNotFound(
            NoteNotFoundException ex) {
        String bodyOfResponse = "Note is not found.";
        return new ResponseEntity<>(bodyOfResponse,
                HttpStatus.NOT_FOUND);
    }
}
