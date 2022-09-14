package com.genesis.genesisapi.repository;

import com.genesis.genesisapi.model.AccountInfo;
import com.genesis.genesisapi.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepo extends JpaRepository<Role,Long> {
}
