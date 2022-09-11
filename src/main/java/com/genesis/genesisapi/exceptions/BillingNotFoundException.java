package com.genesis.genesisapi.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.NOT_FOUND, reason="Billing Id Not Found") //404
public class BillingNotFoundException extends Exception{
	private static final long serialVersionUID = -3332292346834265371L;
	public BillingNotFoundException(Long billingId){
		super("BillingNotFoundException with id="+billingId);
	}
}
