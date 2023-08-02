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
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
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
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
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

            // returning the decrypted string
            byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(strToDec)); // decode Base64 and get decrypted bytes
            return new String(decryptedBytes, "UTF-8"); // convert bytes to string using UTF-8
            /*The error you're encountering, "Reliance on default encoding," is a spotbugs warning that suggests using a
             specific character encoding when converting bytes to strings or strings to bytes. This is important to ensure
              consistent behavior across different platforms and avoid unexpected issues that might arise due to different
              default encodings.

            * In your PasswordEncryption class, you're converting bytes to strings and vice versa using the getBytes
            and new String methods without explicitly specifying the character encoding. To address this warning,
            you should specify a character encoding when performing these conversions.

            * By adding "UTF-8" as the argument to the getBytes and new String methods, I ensured that the byte-to-string
            and string-to-byte conversions use the UTF-8 character encoding, which is a widely used standard for text encoding.
            This helps avoid potential issues related to platform-specific default encodings.*/

        } catch (RuntimeException e){
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
