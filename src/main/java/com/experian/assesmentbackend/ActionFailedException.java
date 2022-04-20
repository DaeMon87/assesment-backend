package com.experian.assesmentbackend;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ActionFailedException 
    extends RuntimeException{
    
        ActionFailedException(String exception){
            super(exception);
        }
}
