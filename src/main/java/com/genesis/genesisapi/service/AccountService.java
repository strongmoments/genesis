package com.genesis.genesisapi.service;

import com.genesis.genesisapi.model.Account;
import com.genesis.genesisapi.repository.AccountRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AccountService {

    @Autowired
    private AccountRepo accountRepo;

    public List<Account> getAllAccount(Boolean b){
        return accountRepo.findByisDeleted(b);
    }

    public Account getAccount(Boolean b, Long accountId){
        return accountRepo.findByisDeletedAndAccountId(b, accountId);
    }

    public Account saveAccount(Account account){
        return accountRepo.save(account);
    }

    public Account isLogin(String username, String password) {
        return accountRepo.findByUsernameAndPassword(username, password);
    }
}
