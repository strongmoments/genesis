package com.genesis.genesisapi.service;

import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.genesis.genesisapi.model.UsersModel;



public interface UsersModelService extends UserDetailsService {
	public void save(UsersModel usersModel);
	public void update(UsersModel usersModel);
	public void delete(UsersModel usersModel);
	public UsersModel findByUserId(String userId);
	public Optional<UsersModel> findByemailId(String EmailId);
	public Optional<UsersModel> findByResetToken(String resetToken);
}
