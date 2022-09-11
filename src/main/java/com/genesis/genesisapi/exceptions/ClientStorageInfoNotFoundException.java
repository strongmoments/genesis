package com.genesis.genesisapi.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.NOT_FOUND, reason="Client Storage Info Id Not Found") //404
public class ClientStorageInfoNotFoundException extends Exception{
	private static final long serialVersionUID = -3332292346834265371L;
	public ClientStorageInfoNotFoundException(Long clientStorageInfoId){
		super("ClientStorageInfoException with id="+clientStorageInfoId);
	}
}
