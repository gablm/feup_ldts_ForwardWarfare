package com.ldts.ForwardWarfare.Controller;

public class InvalidControllerException extends Exception {
    public InvalidControllerException() {}
    public InvalidControllerException(String message) {
        super(message);
    }
}
