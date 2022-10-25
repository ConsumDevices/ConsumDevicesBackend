package ro.tuc.ds2020.controllers.handlers.exceptions.model;

import org.springframework.http.HttpStatus;

import java.util.ArrayList;

//extinde exceptia custom
public class DuplicateResourceException extends CustomException {
    private static final String MESSAGE = "Resource duplicated!";
    //aparent au nume, nu trebuie sa stim numarul
    private static final HttpStatus httpStatus = HttpStatus.CONFLICT;

    public DuplicateResourceException(String resource) {
        super(MESSAGE,httpStatus, resource, new ArrayList<>());
    }

}
