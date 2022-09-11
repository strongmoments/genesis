/*package com.genesis.genesisapi.controller;

import com.genesis.genesisapi.model.Account;
import com.genesis.genesisapi.repository.AccountRepo;
import com.genesis.genesisapi.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
public class LoginController {
    @Autowired
    private AccountService accountService;
    
    @RequestMapping(value = "/login", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView loginPage() {
	    return new ModelAndView("login");
	}
	
	@RequestMapping(value = "/home", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView home (HttpServletRequest request, HttpServletResponse response) {
	    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	    try{
	    	User user =(User) auth.getPrincipal();
	        String userId  = user.getUsername();
	    	return new ModelAndView("home");
	    }catch(Exception e){
	    	return new ModelAndView("login");
	    }
    
	}
	
	@RequestMapping(value = "/index", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView indexpage() {
	    return new ModelAndView("home");
	}
	
	@RequestMapping(value = "/", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView indexpage2(HttpServletRequest request, HttpServletResponse response) {
	    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	    try{
	    	User user =(User) auth.getPrincipal();
	        String userId  = user.getUsername();
	    	return new ModelAndView("home");
	    }catch(Exception e){
	    	return new ModelAndView("login");
	    }
    
	}
	
	@RequestMapping(value="/logout", method = RequestMethod.GET)
	public ModelAndView logoutPage (HttpServletRequest request, HttpServletResponse response) {
	    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	    if (auth != null){    
	        new SecurityContextLogoutHandler().logout(request, response, auth);
	    }
	    return new ModelAndView("login");//You can redirect wherever you want, but generally it's a good practice to show login screen again.
	}
	
}
*/