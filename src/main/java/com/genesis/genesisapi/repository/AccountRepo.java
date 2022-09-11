package com.genesis.genesisapi.repository;

import com.genesis.genesisapi.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AccountRepo extends JpaRepository<Account, Long> {

    Optional<Account> findByUsername(String username);
    List<Account> findByisDeleted(Boolean isDeleted);
    Account findByisDeletedAndAccountId(Boolean isDeleted, Long accountId);

    Account findByUsernameAndPassword(String username, String password);
    /*Optional<Account> findByUsername(String username);*/
}
