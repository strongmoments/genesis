package com.genesis.genesisapi.repository;

import com.genesis.genesisapi.model.AccountInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountInfoRepo extends JpaRepository<AccountInfo,Long> {
}
