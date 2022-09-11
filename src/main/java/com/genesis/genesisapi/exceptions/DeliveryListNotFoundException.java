package com.genesis.genesisapi.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.NOT_FOUND, reason="Delivery List Id Not Found") //404
public class DeliveryListNotFoundException extends Exception{
	private static final long serialVersionUID = -3332292346834265371L;
	public DeliveryListNotFoundException(Long deliveryListId){
		super("DeliveryListNotFoundException with id="+deliveryListId);
	}
}
