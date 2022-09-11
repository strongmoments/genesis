package com.genesis.genesisapi.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.session.SessionFixationProtectionStrategy;


@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private ApplicationContext applicationContext;
    @Autowired
    private CustomAuthenticationProvider  authenticationProvider;
    @Autowired
    CustomLogoutHandler customLogoutHandler;
    @Autowired
    CustomLoginFailureHandler customeLoginFailureHandler;
    @Autowired
    CustomLoginSuccessHandler  customLoginSuccessHandler; 

    @Bean(name = "authenticationManager")
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
    
 
     @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //auth.authenticationProvider(preAuthenticatedAuthenticationProvider);
       auth.authenticationProvider(authenticationProvider);
       // auth.inMemoryAuthentication().withUser("vikash").password("123").roles("USER");
    }

     @Override
     public void configure(WebSecurity web) throws Exception {
    	 web.ignoring().antMatchers("/resources/**", "/resources/***","/forgot-password","/reset-password");
     }
    @Override
    protected void configure(HttpSecurity http) throws Exception {
    	SessionFixationProtectionStrategy sessionFixationProtectionStrategy = new SessionFixationProtectionStrategy();
    	sessionFixationProtectionStrategy.setMigrateSessionAttributes(false);
    	 http.authorizeRequests()
   	  .antMatchers("/hello","/index").access("hasRole('ROLE_ADMIN')")
   	  .anyRequest().permitAll()
   	  .and()
   	    .formLogin().loginPage("/login").loginProcessingUrl("/j_spring_security_check")
   	 .failureHandler(customeLoginFailureHandler)
     .successHandler(customLoginSuccessHandler)
     .usernameParameter("j_username").passwordParameter("j_password")
   	  .and().
   	logout().logoutUrl("/j_spring_security_logout").logoutSuccessUrl("/login?logout").deleteCookies("JSESSIONID").invalidateHttpSession(true)
   	   .and()
   	   .exceptionHandling().accessDeniedPage("/403")
   	  .and()
   	    .csrf().disable();
    }
}
