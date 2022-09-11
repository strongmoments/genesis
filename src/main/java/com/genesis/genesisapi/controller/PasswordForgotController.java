/*package com.genesis.genesisapi.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.genesis.genesisapi.model.Account;
import com.genesis.genesisapi.model.PasswordResetToken;
import com.genesis.genesisapi.repository.AccountRepo;
import com.genesis.genesisapi.repository.PasswordResetTokenRepo;

@Controller
@RequestMapping("/forgot-password")
public class PasswordForgotController {

    @Autowired private AccountRepo accountRepo;
    @Autowired private PasswordResetTokenRepo tokenRepository;
    @Autowired private EmailService emailService;

    @ModelAttribute("forgotPasswordForm")
    public PasswordForgotDto forgotPasswordDto() {
        return new PasswordForgotDto();
    }

    @GetMapping("/")
    public String displayForgotPasswordPage() {
        return "forgot-password";
    }

    @PostMapping("/")
    public String processForgotPasswordForm(@ModelAttribute("forgotPasswordForm") @Valid PasswordForgotDto form,
                                            BindingResult result,
                                            HttpServletRequest request) {

        if (result.hasErrors()){
            return "forgot-password";
        }

        Account account = accountRepo.findByUsername(form.getEmail());
        if (account == null){
            result.rejectValue("email", null, "We could not find an account for that e-mail address.");
            return "forgot-password";
        }

        PasswordResetToken token = new PasswordResetToken();
        token.setToken(UUID.randomUUID().toString());
        token.setAccount(account);
        token.setExpiryDate(30);
        tokenRepository.save(token);

        Mail mail = new Mail();
        mail.setFrom("");
        mail.setTo(account.getUsername());
        mail.setSubject("Password reset request");

        Map<String, Object> model = new HashMap();
        model.put("token", token);
        model.put("user", account);
        model.put("signature", "https://memorynotfound.com");
        String url = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();
        model.put("resetUrl", url + "/reset-password?token=" + token.getToken());
        mail.setModel(model);
        emailService.sendEmail(mail);

        return "redirect:/forgot-password?success";

    }

}
*/