package com.genesis.genesisapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.genesis.genesisapi.model.PasswordResetToken;


public interface PasswordResetTokenRepo extends JpaRepository<PasswordResetToken, Long>{

	PasswordResetToken findByToken(String token);
	
}
