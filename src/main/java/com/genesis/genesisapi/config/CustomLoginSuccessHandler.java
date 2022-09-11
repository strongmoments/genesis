package com.genesis.genesisapi.config;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.util.UrlUtils;
import org.springframework.stereotype.Component;

import com.genesis.genesisapi.model.UsersModel;
import com.genesis.genesisapi.service.UsersModelService;



@Component
public class CustomLoginSuccessHandler implements AuthenticationSuccessHandler {
	private static final Logger LOGGER_APP = LoggerFactory.getLogger(CustomLoginSuccessHandler.class);
	
	private boolean contextRelative;
	
	@Autowired
	public UsersModelService usersModelService;
	
	
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) throws ServletException, IOException {
        User user =(User) authentication.getPrincipal();
        String userId  = user.getUsername();
        UsersModel uModel = usersModelService.findByUserId(userId);
        String redirectUrl = calculateRedirectUrl(request.getContextPath(), "/home");
		// perform to setting user detail in session
        redirectUrl = response.encodeRedirectURL(redirectUrl);
        if (LOGGER_APP.isDebugEnabled()) {
        	LOGGER_APP.debug("Redirecting to '" + redirectUrl + "'");
        }
        response.sendRedirect(redirectUrl);
    }
    
    private String calculateRedirectUrl(String contextPath, String url) {
        if (!UrlUtils.isAbsoluteUrl(url)) {
            if (contextRelative) {
                return url;
            } else {
                return contextPath + url;
            }
        }
        if (!contextRelative) {
            return url;
        }
        url = url.substring(url.lastIndexOf("://") + 3); // strip off scheme
        url = url.substring(url.indexOf(contextPath) + contextPath.length());
        if (url.length() > 1 && url.charAt(0) == '/') {
            url = url.substring(1);
        }
        return url;
    }


}
