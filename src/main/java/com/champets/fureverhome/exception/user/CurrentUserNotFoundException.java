package com.champets.fureverhome.exception.user;
public class CurrentUserNotFoundException extends RuntimeException {
    public CurrentUserNotFoundException(String message) {
        super(message);
    }
}

