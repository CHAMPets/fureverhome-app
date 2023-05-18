package com.champets.fureverhome.exception.pet;

public class PetNotFoundException extends RuntimeException{
    public PetNotFoundException(String message) {
        super(message);
    }
    public PetNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

}
