package com.genesis.genesisapi.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component("customSecurityHolder")
@Scope("prototype")
public class CustomSecurityHolder extends User {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6975570737179005510L;
	
	private static final Logger LOGGER_APP = LoggerFactory.getLogger(CustomSecurityHolder.class);
    private AuthType authType = AuthType.DEFAULT;

	public CustomSecurityHolder(UserDetails userDetails) {
        super(userDetails.getUsername(), userDetails.getPassword(), userDetails.isEnabled(), userDetails.isAccountNonExpired(), userDetails.isCredentialsNonExpired(),
                userDetails.isAccountNonLocked(), userDetails.getAuthorities());
        this.authType = authType;
    }
	

	public enum AuthType { LDAP, KERBEROS, SAML, OAUTH, DEFAULT }
}
