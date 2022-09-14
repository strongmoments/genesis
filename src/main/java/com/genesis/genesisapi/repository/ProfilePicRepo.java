package com.genesis.genesisapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.genesis.genesisapi.model.ProfilePic;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfilePicRepo extends JpaRepository<ProfilePic, Long>{

}
