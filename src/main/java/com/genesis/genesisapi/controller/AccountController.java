package com.genesis.genesisapi.controller;

import com.genesis.genesisapi.exceptions.AccountNotFoundException;
import com.genesis.genesisapi.model.Account;
import com.genesis.genesisapi.repository.AccountRepo;
import com.genesis.genesisapi.service.AccountService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/accounts")
@CrossOrigin("*")
public class AccountController {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private AccountService accountService;

    @GetMapping("/")
    public List<Account> fingAllAccount(){
    	logger.info("Records successfully retreived from accounts table.");
        return accountService.getAllAccount(Boolean.FALSE);
    }

    @GetMapping("/{accountId}")
    public ResponseEntity<Account> findAccountById(@PathVariable Long accountId) throws AccountNotFoundException{
        Account account = accountService.getAccount(Boolean.FALSE, accountId);
        if(account == null){
        	logger.info("Exception in AccountController: Account Id Not Found");
        	throw new AccountNotFoundException(accountId);
        	//return  new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }   
        else{
        	logger.info("Record accessed from AccountController from AccountId:"+accountId);
            return new ResponseEntity<>(account, HttpStatus.OK);
        }
        
    }
    
    @ExceptionHandler(AccountNotFoundException.class)
	public ModelAndView handleAccountNotFoundException(HttpServletRequest request, Exception ex){
	
		ModelAndView modelAndView = new ModelAndView();
	    modelAndView.addObject("exception", ex);
	    modelAndView.addObject("url", request.getRequestURL());
	    
	    modelAndView.setViewName("AccountNotFound");
	    return modelAndView;
	}


    @PostMapping("/")
    public Account saveAccount(@RequestBody Account account){
    	logger.info("Record saved in Account table with username:"+account.getUsername());
        return accountService.saveAccount(account);
    }

    @PutMapping("/{accountId}")
    public ResponseEntity<Account> updateAccount(@PathVariable Long accountId, @Valid @RequestBody Account account){
        Account accountData = accountService.getAccount(Boolean.FALSE, accountId);
        if(accountData == null){
        	logger.info("Exception in AccountController: Account Id Not Found. Could not update Account.");
            return  new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }  

        accountData.setUsername(account.getUsername());
        accountData.setPassword(account.getPassword());
        accountData.setDeleted(false);
        Account updatedAccount = accountService.saveAccount(accountData);
        logger.info("Record updated in Account table with username:"+account.getUsername());
            return new ResponseEntity<>(updatedAccount, HttpStatus.OK);
    }

    @DeleteMapping("/{accountId}")
    public ResponseEntity<Account> deleteAccount(@PathVariable Long accountId){
        Account account = accountService.getAccount(Boolean.FALSE, accountId);
        if(account == null){
        	logger.info("Exception in AccountController: Account Id Not Found.Could not delete record.");
            return  new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        account.setDeleted(true);
        logger.info("Record deleted in Account table with username:"+account.getUsername());
        Account updatedAccount = accountService.saveAccount(account);
        return  new ResponseEntity<>(HttpStatus.OK);
    }
}
