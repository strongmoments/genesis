package com.genesis.genesisapi.controller;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.net.*;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.genesis.genesisapi.controller.UserController;
import com.genesis.genesisapi.model.UsersModel;
import com.genesis.genesisapi.repository.PasswordResetTokenRepo;
import com.genesis.genesisapi.model.PasswordResetToken;
import com.genesis.genesisapi.service.EmailService;
import com.genesis.genesisapi.service.UsersModelServiceImpl;
import com.genesis.genesisapi.util.EncryptionHandler;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

@Controller
public class PasswordController {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
    private JavaMailSender sender;

	@Autowired
	private UsersModelServiceImpl userService;
	
	@Autowired
	private EmailService emailService;

	// Display forgotPassword page
	@RequestMapping(value = "/forgot", method = RequestMethod.GET)
	public ModelAndView displayForgotPasswordPage() {
		return new ModelAndView("forgot-password");
    }
		
	@PostMapping("/forgotpassword")
    
    public ModelAndView sendResetPasswordEmail(ModelAndView modelAndView, @RequestParam("email") String email, HttpServletRequest request) throws Exception {
		
		// Lookup user in database by e-mail
		UsersModel usersModel = null;
		Optional<UsersModel> optional = userService.findByemailId(email);
		

		if (!optional.isPresent()) {
			modelAndView.addObject("errorMessage", "We didn't find an account for that e-mail address.");
		} else {
			logger.info("Records successfully retreived from UsersModel table where email = "+email);
			// Generate random 36-character string token for reset password 
			usersModel = optional.get();
			usersModel.setResetToken(UUID.randomUUID().toString());
			
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		    Date date = new Date();
		    System.out.println(dateFormat.format(date));
		    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date tokenDate=formatter.parse(dateFormat.format(date));
			usersModel.setTokenDate(tokenDate);
			// Save token to database
			userService.save(usersModel);
		
			//String appUrl = request.getScheme() + "://" + request.getServerName()+":"+Integer.toString(request.getServerPort())+"/genesis";			
			String appUrl = request.getScheme() + "://" + "16f6e71c.ngrok.io"+"/genesis";			
			//sendEmail(email);
			// Email message
			SimpleMailMessage passwordResetEmail = new SimpleMailMessage();
			
			passwordResetEmail.setTo(usersModel.getEmailId());
			passwordResetEmail.setSubject("Password Reset Request");
			passwordResetEmail.setText("To reset your password, click the link below:\n" + appUrl
					+ "/reset?token=" + usersModel.getResetToken()+"\nNote: This link is valid only for 24 hours after that it will expire.\nThis mail is sent by Genesis Cold Storage Company.");
			
			emailService.sendEmail(passwordResetEmail);	
			String recipient = usersModel.getMobileNo();
            String message = "To reset your password, click the link below:\n " + appUrl
					+ "/reset?token=" + usersModel.getResetToken()+" \nNote: This link is valid only for 24 hours after that it will expire.\nThis mail is sent by Genesis Cold Storage Company.";
			sendSms(recipient,message);
            String successMsg="A password reset link has been sent to " + email +" and to your registered mobile number.";
	        modelAndView.addObject("successMessage", successMsg);
		}
		modelAndView.setViewName("forgot-password");
		return modelAndView;
    }
	
	// Display form to reset password
		@RequestMapping(value = "/reset", method = RequestMethod.GET)
		public ModelAndView displayResetPasswordPage(ModelAndView modelAndView, @RequestParam("token") String token) {
			
			Optional<UsersModel> usersModel = userService.findByResetToken(token);

			if (usersModel.isPresent()) { // Token found in DB
				logger.info("Records successfully retreived from UsersModel table where ResetToken = "+token);
				modelAndView.addObject("resetToken", token);
			} else { // Token not found in DB
				modelAndView.addObject("errorMessage", "Oops!  This is an invalid password reset link.");
			}

			modelAndView.setViewName("reset-password");
			return modelAndView;
		}
		
		// Process reset password form
		@RequestMapping(value = "/reset", method = RequestMethod.POST)
		public ModelAndView setNewPassword(ModelAndView modelAndView, @RequestParam Map<String, String> requestParams, RedirectAttributes redir) {
			
			// Find the user associated with the reset token
			Optional<UsersModel> user = userService.findByResetToken(requestParams.get("token"));

			// This should always be non-null but we check just in case
			if (user.isPresent()) {
				logger.info("Records successfully retreived from UsersModel table where ResetToken = "+requestParams.get("token"));
				
				UsersModel resetUser = user.get(); 
				logger.info("Token :"+requestParams.get("token"));
				logger.info("Token :"+requestParams.get("password"));

				DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				Date date = new Date();
				String todayDate = dateFormat.format(date);
				
				String resetTokenDt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(user.get().getTokenDate().getTime());
				logger.info("Reset Token date is:"+resetTokenDt);
				Boolean dateValid = DateTimeDifference(resetTokenDt,todayDate);
				logger.info("Reset Token date validity:"+dateValid);
				if(dateValid == true){
					// Set new password    
		            String pass = requestParams.get("password");
		            resetUser.setPassword(EncryptionHandler.get_SHA_1_SecurePassword(pass));
		            
					// Set the reset token to null so it cannot be used again
					resetUser.setResetToken(null);
					resetUser.setTokenDate(null);

					// Save user
					userService.save(resetUser);
					logger.info("Records successfully saved in UsersModel table where ResetToken = "+requestParams.get("token"));
					// In order to set a model attribute on a redirect, we must use
					// RedirectAttributes
					//redir.addFlashAttribute("successMessage", "You have successfully reset your password.  You may now login.");
					modelAndView.addObject("successMessage", "You have successfully reset your password.  You may now login.");
					modelAndView.setViewName("PassResetMsg");
					return modelAndView;
				}
				else
				{
					resetUser.setResetToken(null);
					resetUser.setTokenDate(null);
					// Save user
					userService.save(resetUser);
					logger.info("Records successfully saved in UsersModel table where ResetToken = "+requestParams.get("token"));
					modelAndView.addObject("errorMessage", "Your token is expired. Token is valid only for 24 hours!");
					modelAndView.setViewName("PassResetMsg");
					return modelAndView;
				}
			} else {
				modelAndView.addObject("errorMessage", "Oops!  This is an invalid password reset link.");
				modelAndView.setViewName("reset-password");	
			}
			return modelAndView;
	   }
	   
	    // Going to reset page without a token redirects to login page
		@ExceptionHandler(MissingServletRequestParameterException.class)
		public ModelAndView handleMissingParams(MissingServletRequestParameterException ex) {
			return new ModelAndView("redirect:login");
		}
	
    private void sendEmail(String email) throws Exception{
    	String appUrl = "http://localhost:8080/genesis/reset-link";
        MimeMessage message = sender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);
        
        helper.setTo(email);
        helper.setText("To reset your password, click the link below:\n" + appUrl);
        helper.setSubject("Mail to Reset Password!");
        
        sender.send(message);
    }
	
    public static Boolean DateTimeDifference(String dateStart, String dateStop){
		//HH converts hour in 24 hours format (0-23), day calculation
				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				
				Date d1 = null;
				Date d2 = null;
				
				long diff = 0L;

				long diffSeconds = 0L;
				long diffMinutes = 0L;
				long diffHours = 0L;
				long diffDays = 0L;

				
				try {
					d1 = format.parse(dateStart);
					d2 = format.parse(dateStop);

					//in milliseconds
					diff = d2.getTime() - d1.getTime();

					diffSeconds = diff / 1000 % 60;
					diffMinutes = diff / (60 * 1000) % 60;
					diffHours = diff / (60 * 60 * 1000) % 24;
					diffDays = diff / (24 * 60 * 60 * 1000);

					System.out.print(diffDays + " days, ");
					System.out.print(diffHours + " hours, ");
					System.out.print(diffMinutes + " minutes, ");
					System.out.print(diffSeconds + " seconds.");
					System.out.println();

				} catch (Exception e) {
					e.printStackTrace();
				}
			if(diffDays<1)
				return true;
			else if(diffDays==1  && diffHours==0 && diffMinutes==0 && diffSeconds==0)
				return true;
			else
				return false;
		
	}
    
    public String sendSms(String mobileNo, String msg) {
    	try{
    		// Find your Account Sid and Token at twilio.com/user/account
      	  final String ACCOUNT_SID = "AC9ba5084df9cc9ea7f5860652d1628992";
      	  final String AUTH_TOKEN = "9d059d88154591e43756eb6c2e7f51b5";

      	  Twilio.init(ACCOUNT_SID, AUTH_TOKEN);

    	    Message message = Message.creator(new PhoneNumber("+19714071334"),
    	        new PhoneNumber("+919999896595"), 
    	        msg).create();

    	    System.out.println(message.getSid());
    	    return "Sent sms Successfully";
    	}catch(Exception e){
    		System.out.println("Sms not sent due to error! "+e.getMessage());
    	}
    	return "Sent sms Successfully";
    	    
	}
    /*public String sendSms(String mobileNo, String msg) {
		try {
			// Construct data
			//String apiKey = "apikey=" + "rpkKmmQRBsE-DhIKPInRRDkJrkLuL12QzmtPiOPIOR";
			String apiKey = "apikey=" + "rpkKmmQRBsE-WizPG5EwN7h8OfaszfOGJ49XBIToKi";
			String message = "&message=" + msg;
			String sender = "&sender=" + "TXTLCL";
			String numbers = "&numbers=" + mobileNo;
			
			// Send data
			HttpURLConnection conn = (HttpURLConnection) new URL("https://api.textlocal.in/send/?").openConnection();
			String data = apiKey + numbers + message + sender;
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Length", Integer.toString(data.length()));
			conn.getOutputStream().write(data.getBytes("UTF-8"));
			final BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			final StringBuffer stringBuffer = new StringBuffer();
			String line;
			while ((line = rd.readLine()) != null) {
				stringBuffer.append(line);
			}
			rd.close();
			logger.info("Sms sent to the user with reset password link.");
			return stringBuffer.toString();
		} catch (Exception e) {
			System.out.println("Error SMS "+e);
			logger.info("Error occured while sending Sms to the user with reset password link. Error is "+e.getMessage());
			return "Error "+e;
		}
	}*/
}
/*package com.genesis.genesisapi.controller;

import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.genesis.genesisapi.model.Account;
import com.genesis.genesisapi.repository.AccountRepo;

public class PasswordController {

	@Autowired
	private AccountRepo userService;
	
	@RequestMapping(value = "/user/resetPassword", method = RequestMethod.POST)
	@ResponseBody
	public GenericResponse resetPassword(HttpServletRequest request, 
	@RequestParam("email") String userEmail) {
	Account user = userService.findAccountByEmail(userEmail);
	if (user == null) {
	    throw new UsernameNotFoundException();
	}
	String token = UUID.randomUUID().toString();
	userService.createPasswordResetTokenForUser(user, token);
	mailSender.send(constructResetTokenEmail(getAppUrl(request), 
	  request.getLocale(), token, user));
	return new GenericResponse(
	  messages.getMessage("message.resetPasswordEmail", null, 
	  request.getLocale()));
	}
	
}
*/