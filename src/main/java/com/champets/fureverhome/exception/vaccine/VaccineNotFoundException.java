package com.champets.fureverhome.exception.vaccine;

public class VaccineNotFoundException  extends RuntimeException{
    public VaccineNotFoundException(String message){
        super(message);
    }

    public VaccineNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
