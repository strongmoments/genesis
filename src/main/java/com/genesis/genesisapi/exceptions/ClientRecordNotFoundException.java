package com.genesis.genesisapi.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.NOT_FOUND, reason="Client Not Found") //404
public class ClientRecordNotFoundException extends Exception{
private static final long serialVersionUID = -3332292346834265371L;
	
	/*public ClientNotFoundException(String message){
        super(message);
    }*/
	public ClientRecordNotFoundException(){
		super("ClientNotFoundException");
	}
}
