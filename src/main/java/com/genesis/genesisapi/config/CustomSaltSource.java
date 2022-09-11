package com.genesis.genesisapi.config;



import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.genesis.genesisapi.util.EncryptionHandler;


@Component("custSaltSource")
public class CustomSaltSource {//implements SaltSource {


    public Object getSalt(UserDetails user) {
        return EncryptionHandler.ENCRYPTION_STANDARD_KEY;
    }
}

