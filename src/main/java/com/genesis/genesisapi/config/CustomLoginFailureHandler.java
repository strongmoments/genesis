package com.genesis.genesisapi.config;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

/**
 * @author Vikash Sharma
 *
 */
@Component
public class CustomLoginFailureHandler extends SimpleUrlAuthenticationFailureHandler {
	
	private static final Logger LOGGER_APP = LoggerFactory.getLogger(CustomLoginFailureHandler.class);

 	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception)
			throws IOException, ServletException {
 		
 		setAllowSessionCreation(true);
		request.getSession().setAttribute(WebAttributes.AUTHENTICATION_EXCEPTION, exception);
		Object userId = request.getParameter("j_username");
		getRedirectStrategy().sendRedirect(request, response, "/login?error");
	}
}
