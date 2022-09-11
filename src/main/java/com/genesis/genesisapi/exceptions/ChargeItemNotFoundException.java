package com.genesis.genesisapi.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.NOT_FOUND, reason="Charge Item Id Not Found") //404
public class ChargeItemNotFoundException extends Exception{
	private static final long serialVersionUID = -3332292346834265371L;
	public ChargeItemNotFoundException(Long chargeItemsId){
		super("ChargeItemNotFoundException with id="+chargeItemsId);
	}
}
