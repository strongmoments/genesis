package com.genesis.genesisapi.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.NOT_FOUND, reason="Account Id Not Found") //404
public class OtherChargeNotFoundException extends Exception{
	private static final long serialVersionUID = -3332292346834265371L;
	public OtherChargeNotFoundException(Long otherChargesId){
		super("OtherChargeNotFoundException with id="+otherChargesId);
	}
}
