package com.champets.fureverhome.exception.pet;

public class PetServiceException extends RuntimeException{
    public PetServiceException(String message) {
        super(message);
    }

    public PetServiceException(String message, Throwable cause) {
        super(message, cause);
    }


}
