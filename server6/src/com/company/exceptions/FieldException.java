package com.company.exceptions;

public class FieldException extends RuntimeException {

    public FieldException() {
        super("Недопустимое значение");
    }
}

