package com.champets.fureverhome.exception.vaccine;

public class VaccineListEmptyException extends RuntimeException{
    public VaccineListEmptyException(String message){
        super(message);
    }

    public VaccineListEmptyException(String message, Throwable cause) {
        super(message, cause);
    }
}
