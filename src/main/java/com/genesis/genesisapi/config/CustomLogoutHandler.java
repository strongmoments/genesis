package com.genesis.genesisapi.config;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Component;




@Component
public class CustomLogoutHandler implements LogoutHandler {
	
	private static final Logger LOGGER_APP = LoggerFactory.getLogger(CustomLogoutHandler.class);

	
	@Override
	public void logout(HttpServletRequest request,
			HttpServletResponse response, Authentication authentication) {
			try{
				// custom logout code
			} catch(Exception e){
				LOGGER_APP.error("Login history creation failed :"+e);	
			}
	}
}
