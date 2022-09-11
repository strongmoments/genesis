package com.genesis.genesisapi;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.genesis.genesisapi.repository.UserRepository;

@Controller
public class WelcomeController {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	Date date = null;
	String formatted;
	SimpleDateFormat dateFormat = new SimpleDateFormat("EEE dd-MM-yyyy HH:mm:ss zzz");

	@Autowired
	private UserRepository userRepository;

	@RequestMapping("/")
	public String welcome(Map<String, Object> model, HttpSession httpSession) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		try {
			User user = (User) auth.getPrincipal();
			String userId = user.getUsername();
			String userName = userRepository.findByuserId(userId).getName();
			String userEmail = userRepository.findByuserId(userId).getEmailId();
			logger.info("UserName:"+userName+" Email Id: "+userEmail);
			httpSession.setAttribute("emailId", userEmail);
			httpSession.setAttribute("userId", userName);
			date = new Date();
			formatted = dateFormat.format(date);
			logger.info("Login details: admin logged at " + formatted + ".");
			return "home";
		} catch (Exception e) {
			logger.info("Exception in LoginController:" + e.getMessage());
			return "login";
		}
	}

	@RequestMapping("/login")
	public String loginPage(Map<String, Object> model) {
		return "login";
	}

	@RequestMapping("/home")
	public String home(Map<String, Object> model, HttpSession httpSession) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		try {
			User user = (User) auth.getPrincipal();
			String userId = user.getUsername();
			String userName = userRepository.findByuserId(userId).getName();
			String userEmail = userRepository.findByuserId(userId).getEmailId();
			//String userProfilePic = userRepository.findByuserId(userId).getProfilePicPath();
			long sno = userRepository.findByuserId(userId).getSno();
			logger.info("UserName:"+userName+" Email Id: "+userEmail);
			httpSession.setAttribute("emailId", userEmail);
			httpSession.setAttribute("sno", sno);
			httpSession.setAttribute("userId", userName);
			//httpSession.setAttribute("profilePic", userProfilePic);
			date = new Date();
			formatted = dateFormat.format(date);
			logger.info("Login details: admin logged at " + formatted + ".");
			return "home";
		} catch (Exception e) {
			logger.info("Exception in LoginController:" + e.getMessage());
			return "login";
		}
	}

	@RequestMapping("/index")
	public String index(Map<String, Object> model) {
		return "home";
	}

	@RequestMapping("/logout")
	public String logout(HttpServletRequest request, HttpServletResponse response) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth != null) {
			new SecurityContextLogoutHandler().logout(request, response, auth);
		}
		date = new Date();
		formatted = dateFormat.format(date);
		logger.info("Logout details: admin logged out at " + formatted + ".");
		return "login";// Y
	}
	

}