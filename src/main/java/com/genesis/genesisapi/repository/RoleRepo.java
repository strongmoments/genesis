package com.genesis.genesisapi.repository;

import com.genesis.genesisapi.model.AccountInfo;
import com.genesis.genesisapi.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepo extends JpaRepository<Role,Long> {
}
