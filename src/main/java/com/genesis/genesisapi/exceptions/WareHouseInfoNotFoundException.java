package com.genesis.genesisapi.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.NOT_FOUND, reason="Ware House Info Not Found") //404
public class WareHouseInfoNotFoundException extends Exception {
	private static final long serialVersionUID = -3332292346834265371L;
	public WareHouseInfoNotFoundException(Long warehouseInfoId){
		super("WareHouseInfoNotFoundException with id="+warehouseInfoId);
	}
}
