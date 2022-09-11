package com.genesis.genesisapi.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;


@Component("customPrototypeFactory")
public class CustomPrototypeFactory {
	private static final Logger LOGGER_APP = LoggerFactory.getLogger(CustomPrototypeFactory.class);

	@Autowired
	private ApplicationContext applicationContext;

	/**
	 * provide  user specific information For the Normal Authentication 
	 * @param user Spring UserDetails
	 * @return
	 */
	public UserDetails makeUserDetails(UserDetails user){
		UserDetails details = (UserDetails) applicationContext.getBean("customSecurityHolder", user);
		return details;
	}
}
