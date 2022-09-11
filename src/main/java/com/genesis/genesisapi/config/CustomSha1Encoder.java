package com.genesis.genesisapi.config;


import org.springframework.stereotype.Component;

@Component("custPasswordEncoder")
public class CustomSha1Encoder {//extends ShaPasswordEncoder {

    /**
     * Static salt is included before password without delimiters
     */
   // @Override
    protected String mergePasswordAndSalt(String password, Object salt, boolean strict) {
        if (password == null) {
            password = "";
        }

        if (strict && (salt != null)) {
            if ((salt.toString().lastIndexOf("{") != -1) || (salt.toString().lastIndexOf("}") != -1)) {
                throw new IllegalArgumentException("Cannot use { or } in salt.toString()");
            }
        }

        if ((salt == null) || "".equals(salt)) {
            return password;
        } else {
            return salt.toString() + password;
        }
    }

    /**
     * Static salt of 32 chars is included before password without delimiters
     */
   // @Override
    protected String[] demergePasswordAndSalt(String mergedPasswordSalt) {
        if ((mergedPasswordSalt == null) || "".equals(mergedPasswordSalt)) {
            throw new IllegalArgumentException("Cannot pass a null or empty String");
        }

        String password = mergedPasswordSalt;
        String salt = "";

        int saltBegins = 0;

        if ((saltBegins != -1) && ((saltBegins + 1) < mergedPasswordSalt.length())) {
            salt = mergedPasswordSalt.substring(0, 32);
            password = mergedPasswordSalt.substring(33, mergedPasswordSalt.length());
        }
        return new String[] {password, salt};
    }


}
