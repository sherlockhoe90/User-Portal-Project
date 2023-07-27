package com.inexture.userportal.userportalproject.utility;

/*AES - Advanced Encryption Standard */

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64;

public class PasswordEncryption {

    private static SecretKeySpec secretkey;
    private static byte[] key;

    //setkey
    public static void setKey() {
        try {
            String myKey = "HastaLaVista";
            key = myKey.getBytes("UTF-8");
            /* Checksumn: error or integrity detection method*/
            /* hash ufunction: it is a function to produce checksum  */
            /* hash value (is a numeric va;ue of fixed length that uniquely indentifies dta. */
            /* message digest: it is a fixed sized numeric representation of the contents of the message... computed by the hash function */
            /*so, In Java, MessageDigest class provides functionality of a message digest using algorithms such as SHA-1 or SHA-256 */
            /* SHA stands for Secure Hashing Algorithm*/
            MessageDigest sha = MessageDigest.getInstance("SHA-1");
            key = sha.digest(key);

            key = Arrays.copyOf(key, 16); //orig, new length
            secretkey = new SecretKeySpec(key, "AES");
        } catch (NoSuchAlgorithmException e) {
            //TODO
        } catch (UnsupportedEncodingException e) {
            //TODO
        }
    }

    // encryption
    public static String encrypt(String strToEnc) {
//    public static String encrypt(String strToEnc, String sec) { /*the commented method declaration includes the secretKey 'sec' which can be used to encrypt and decrypt*/
        try {
            setKey(); //setting HastaLaVista as the key in this method

            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding"); // use AES
            cipher.init(Cipher.ENCRYPT_MODE, secretkey); // initializing cipher in encrypt mode

            return Base64.getEncoder().encodeToString(cipher.doFinal(strToEnc.getBytes("UTF-8"))); // returning the encrypted string
        } catch (Exception e) {
//            //TODO
        }
        return null;
    }

    // decryption
    public static String decrypt(String strToDec) {
//    public static String decrypt(String strToDec, String sec) { /*the commented method declaration includes the secretKey 'sec' which can be used to encrypt and decrypt
        try {
            setKey(); //setting HastaLaVista as the key in this method

            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding"); // use AEs
            cipher.init(Cipher.DECRYPT_MODE, secretkey); // initializing cipher in decrypt mode

            return new String(cipher.doFinal(Base64.getDecoder().decode(strToDec))); // returning the decrypted string
        } catch (Exception e) {
//            //TODO
        }
        return null;
    }
}
