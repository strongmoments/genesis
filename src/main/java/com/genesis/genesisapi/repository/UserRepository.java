package com.genesis.genesisapi.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.genesis.genesisapi.model.Account;
import com.genesis.genesisapi.model.UsersModel;

public interface UserRepository extends  JpaRepository<UsersModel, Long> {
	
	UsersModel findByuserId(String UserId);
	Optional<UsersModel> findByemailId(String EmailId);
	Optional<UsersModel> findByResetToken(String resetToken);
}
