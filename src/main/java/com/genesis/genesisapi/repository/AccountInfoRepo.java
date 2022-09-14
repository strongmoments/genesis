package com.genesis.genesisapi.repository;

import com.genesis.genesisapi.model.AccountInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountInfoRepo extends JpaRepository<AccountInfo,Long> {
}
