package com.genesis.genesisapi.util;



import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Security;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import javax.xml.bind.DatatypeConverter;

import org.apache.commons.codec.binary.Base64;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;





/**
 * 
 * @author vsharma
 *
 */
public class EncryptionHandler {
	
	private static final Logger LOGGER_APP = LoggerFactory.getLogger(EncryptionHandler.class);

    private static String TRIPLE_DES_TRANSFORMATION = "DESede/ECB/PKCS7Padding";
    private static String ALGORITHM = "DESede";
    private static String BOUNCY_CASTLE_PROVIDER = "BC";
    private Cipher encrypter;
    private Cipher decrypter;
    /**
     * Used as encryption/decryption key.
     */
    public static final String ENCRYPTION_STANDARD_KEY = "41-4D-49-54-20-53-48-41-52-4D-41";			
/**
 * Initializes the encrypter and decrypter Cipher 
 * 
 * @param key :  key size should be 26 bytes
 * @throws InvalidKeyException 
 * @throws NoSuchAlgorithmException 
 * @throws InvalidKeySpecException 
 * @throws NoSuchPaddingException 
 * @throws NoSuchProviderException 
 */
    
    public EncryptionHandler(String key) throws InvalidKeyException, InvalidKeySpecException, NoSuchAlgorithmException, NoSuchProviderException, NoSuchPaddingException {
		   Security.addProvider(new BouncyCastleProvider());
		   KeySpec keySpecObj = new DESedeKeySpec(key.getBytes());
		   SecretKey secKey = SecretKeyFactory.getInstance(ALGORITHM).generateSecret(keySpecObj);
		   encrypter = Cipher.getInstance(TRIPLE_DES_TRANSFORMATION, BOUNCY_CASTLE_PROVIDER);
	       encrypter.init(Cipher.ENCRYPT_MODE, secKey);
	       decrypter = Cipher.getInstance(TRIPLE_DES_TRANSFORMATION, BOUNCY_CASTLE_PROVIDER);
	       decrypter.init(Cipher.DECRYPT_MODE, secKey);
    }
/**
 * Encrypt the given string  using defined algorithm and provider
 * @param input
 * @return
 * @throws IllegalBlockSizeException
 * @throws BadPaddingException
 */
    public byte[] encode2Byte(String input) throws IllegalBlockSizeException, BadPaddingException {
       return encrypter.doFinal(input.getBytes());
    }
/**
 * Encrypt the given string  using defined algorithm and provider
 * @param input
 * @return
 * @throws IllegalBlockSizeException
 * @throws BadPaddingException
 */
    public String encode2String(String input) throws IllegalBlockSizeException, BadPaddingException {
    	return Base64.encodeBase64String(encrypter.doFinal(input.getBytes()));
    }
/**
 * Decrypt the given string to original
 * @param input
 * @return
 * @throws IllegalBlockSizeException
 * @throws BadPaddingException
 */
    public byte[] decode2Byte(String input) throws IllegalBlockSizeException, BadPaddingException {
       return decrypter.doFinal(input.getBytes());
    }
/**
 * Decrypt the given string to original
 * @param input
 * @return
 * @throws IllegalBlockSizeException
 * @throws BadPaddingException
 * @throws IOException 
 */
    public String decode2String(String input) throws IllegalBlockSizeException, BadPaddingException, IOException {
    	byte[] inputByte  = Base64.decodeBase64(input);
    	return new String(decrypter.doFinal(inputByte));
    }
/**
 * Encrypt the  given string
 * @param input : string to be encrypted 
 * @param key : key to encrypt should be 26 byte size
 * @return : encrypted byte[]
 * @throws IllegalBlockSizeException
 * @throws BadPaddingException
 * @throws NoSuchAlgorithmException
 * @throws NoSuchProviderException
 * @throws NoSuchPaddingException
 * @throws InvalidKeyException
 * @throws InvalidKeySpecException
 */
    public static byte[] encode2Byte(String input, String key)throws IllegalBlockSizeException, BadPaddingException,NoSuchAlgorithmException, NoSuchProviderException,NoSuchPaddingException, InvalidKeyException, InvalidKeySpecException {
    	Security.addProvider(new BouncyCastleProvider());
    	SecretKey secKey = keyGenerator(key);
        Cipher encrypter = Cipher.getInstance(TRIPLE_DES_TRANSFORMATION,BOUNCY_CASTLE_PROVIDER);
        encrypter.init(Cipher.ENCRYPT_MODE, secKey);
        return encrypter.doFinal(input.getBytes());
    }
/**
 * Encrypt the  given string
 * @param input : string to be encrypted 
 * @param key : key to encrypt should be 26 byte size
 * @return : encrypted string
 * @throws IllegalBlockSizeException
 * @throws BadPaddingException
 * @throws NoSuchAlgorithmException
 * @throws NoSuchProviderException
 * @throws NoSuchPaddingException
 * @throws InvalidKeyException
 * @throws InvalidKeySpecException
 */
    public static String encode2String(String input, String key)throws IllegalBlockSizeException, BadPaddingException,NoSuchAlgorithmException, NoSuchProviderException,NoSuchPaddingException, InvalidKeyException, InvalidKeySpecException {
    	Security.addProvider(new BouncyCastleProvider());
    	SecretKey secKey = keyGenerator(key);
        Cipher encrypter = Cipher.getInstance(TRIPLE_DES_TRANSFORMATION,BOUNCY_CASTLE_PROVIDER);
        encrypter.init(Cipher.ENCRYPT_MODE, secKey);
        return Base64.encodeBase64String(encrypter.doFinal(input.getBytes()));
    }
/**
 * Decrypt the given string to original
 * @param input : string to be encrypted 
 * @param key : key to encrypt should be 26 byte size
 * @return : encrypted byte[]
 * @throws IllegalBlockSizeException
 * @throws BadPaddingException
 * @throws NoSuchAlgorithmException
 * @throws NoSuchProviderException
 * @throws NoSuchPaddingException
 * @throws InvalidKeyException
 * @throws InvalidKeySpecException
 */
    public static byte[] decode2Byte(String input, String key)throws IllegalBlockSizeException, BadPaddingException,NoSuchAlgorithmException, NoSuchProviderException,NoSuchPaddingException, InvalidKeyException, InvalidKeySpecException {
    	Security.addProvider(new BouncyCastleProvider());
    	SecretKey secKey = keyGenerator(key);
        Cipher decrypter = Cipher.getInstance(TRIPLE_DES_TRANSFORMATION,BOUNCY_CASTLE_PROVIDER);
        decrypter.init(Cipher.DECRYPT_MODE, secKey);
        return decrypter.doFinal(input.getBytes());
    }
/**
 * Decrypt the given string to original
 * @param input : string to be encrypted 
 * @param key : key to encrypt should be 26 byte size
 * @return : encrypted String
 * @throws IllegalBlockSizeException
 * @throws BadPaddingException
 * @throws NoSuchAlgorithmException
 * @throws NoSuchProviderException
 * @throws NoSuchPaddingException
 * @throws InvalidKeyException
 * @throws InvalidKeySpecException
 * @throws IOException 
 */
    public static String decode2String(String input, String key)throws IllegalBlockSizeException, BadPaddingException,NoSuchAlgorithmException, NoSuchProviderException,NoSuchPaddingException, InvalidKeyException, InvalidKeySpecException, IOException {
    	byte[] inputByte  = Base64.decodeBase64(input);
    	Security.addProvider(new BouncyCastleProvider());
    	SecretKey secKey = keyGenerator(key);
        Cipher decrypter = Cipher.getInstance(TRIPLE_DES_TRANSFORMATION,BOUNCY_CASTLE_PROVIDER);
        decrypter.init(Cipher.DECRYPT_MODE, secKey);
        return new String(decrypter.doFinal(inputByte));
    }
/**
 * 
 * @param key : should be of 26 bytes size
 * @return SecretKey
 * @throws InvalidKeyException
 * @throws InvalidKeySpecException
 * @throws NoSuchAlgorithmException
 */
    private static SecretKey keyGenerator(String key) throws InvalidKeyException, InvalidKeySpecException, NoSuchAlgorithmException{
    	KeySpec keySpecObj = new DESedeKeySpec(key.getBytes());
    	return SecretKeyFactory.getInstance(ALGORITHM).generateSecret(keySpecObj);
    }
    
    @SuppressWarnings("unused")
	public static String get_SHA_1_SecurePassword(String passwordToHash)
    {
        String generatedPassword = null;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            md.update(ENCRYPTION_STANDARD_KEY.getBytes());
            byte[] bytes = md.digest(passwordToHash.getBytes());
            StringBuilder sb = new StringBuilder();
            for(int i=0; i< bytes.length ;i++)
            {
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            generatedPassword = sb.toString();
        }
        catch (NoSuchAlgorithmException e)
        {
            LOGGER_APP.error("", e);
        }
        return generatedPassword;
    }
    /**********   Logic to encode and decode object****************/
    public static String encodeObject2String(Object input, String key)throws IllegalBlockSizeException, BadPaddingException,NoSuchAlgorithmException, NoSuchProviderException,NoSuchPaddingException, InvalidKeyException, InvalidKeySpecException {
    	Security.addProvider(new BouncyCastleProvider());
    	SecretKey secKey = keyGenerator(key);
        Cipher encrypter = Cipher.getInstance(TRIPLE_DES_TRANSFORMATION,BOUNCY_CASTLE_PROVIDER);
        encrypter.init(Cipher.ENCRYPT_MODE, secKey);
        return DatatypeConverter.printHexBinary(encrypter.doFinal(JavaUtilities.objectToByteArray(input)));
    }
    
    public static Object decodeObject2String(String input, String key)throws IllegalBlockSizeException, BadPaddingException,NoSuchAlgorithmException, NoSuchProviderException,NoSuchPaddingException, InvalidKeyException, InvalidKeySpecException, IOException {
    	byte[] inputByte  = DatatypeConverter.parseHexBinary(input);
    	Security.addProvider(new BouncyCastleProvider());
    	SecretKey secKey = keyGenerator(key);
        Cipher decrypter = Cipher.getInstance(TRIPLE_DES_TRANSFORMATION,BOUNCY_CASTLE_PROVIDER);
        decrypter.init(Cipher.DECRYPT_MODE, secKey);
        return JavaUtilities.byteArrayToObject(decrypter.doFinal(inputByte));
    }
}
