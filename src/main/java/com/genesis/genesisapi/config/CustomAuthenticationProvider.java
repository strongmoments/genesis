package com.genesis.genesisapi.config;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
//import org.springframework.security.authentication.dao.SaltSource;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

@Component("authenticationProvider")
public class CustomAuthenticationProvider extends DaoAuthenticationProvider {
		@Autowired
	    @Qualifier("userModelServiceBean")
	    @Override
	    public void setUserDetailsService(UserDetailsService userDetailsService) {
	        super.setUserDetailsService(userDetailsService);
	    }
		
		/*@Autowired
		@Qualifier("custPasswordEncoder")
		public void setPasswordEncoder(CustomSha1Encoder passwordEncoder) {
			super.setPasswordEncoder(passwordEncoder);
		}
		//@Autowired
		//@Qualifier("custSaltSource")
		/*@Override
		public void setSaltSource(SaltSource saltSource) {
			super.setSaltSource(saltSource);
		}*/
		
	    @Autowired
		private MessageSource messageSource;
	    @Override
	    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
	        try {
	            Authentication auth = super.authenticate(authentication);
	            return auth;
	        } catch (BadCredentialsException e) {
	            String userId = authentication.getName();
	            // code to login failed attem 
	            throw e;
	        } catch (LockedException e) {
	            String[] valsHeader = new String[0];
	            Locale locale = LocaleContextHolder.getLocale();
	            String error = messageSource.getMessage("CREDENTIALSLOCKEDMSG", valsHeader,"User account is locked. Please change your password to unlock !", locale);
	            throw new LockedException(error);
	        }
	    }


}
