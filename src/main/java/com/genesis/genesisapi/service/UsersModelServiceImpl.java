package com.genesis.genesisapi.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.genesis.genesisapi.config.CustomPrototypeFactory;
import com.genesis.genesisapi.model.UsersModel;
import com.genesis.genesisapi.repository.UserRepository;



@Service("userModelServiceBean")
public class UsersModelServiceImpl implements UsersModelService {
	
	@Autowired
	private CustomPrototypeFactory customPrototypeFactory;
	
	@Autowired
	public UserRepository  usersModelDao; 
	
	@Override
	public void save(UsersModel usersModel) {
		usersModelDao.save(usersModel);
	}
	@Override
	public void update(UsersModel usersModel) {
		//
	}
	@Override
	public void delete(UsersModel usersModel) {
		usersModelDao.delete(usersModel);
	}
	@Override
	public UsersModel findByUserId(String userId) {
		return usersModelDao.findByuserId(userId);
	}
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UsersModel userModel= usersModelDao.findByuserId(username);
		if(userModel == null){
			throw new UsernameNotFoundException("User does not exsist");
		} else {
			User springSecurityUser = null;
			springSecurityUser = new User(userModel.getUserId(), userModel.getPassword(), true, true, true, true, getAuthorities("ROLE_USER"));
			return customPrototypeFactory.makeUserDetails(springSecurityUser);
		}
	}
	private Collection<? extends GrantedAuthority> getAuthorities(String role) {
		List<GrantedAuthority> authList = new ArrayList<GrantedAuthority>();
		authList.add(new SimpleGrantedAuthority(role));
		return authList;
	}
	@Override
	public Optional<UsersModel> findByemailId(String EmailId) {
		return usersModelDao.findByemailId(EmailId);
	}
	@Override
	public Optional<UsersModel> findByResetToken(String resetToken) {
		return usersModelDao.findByResetToken(resetToken);
	}
	
}
