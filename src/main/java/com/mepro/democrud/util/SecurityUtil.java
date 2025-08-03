package com.mepro.democrud.util;

import java.security.Key;
import java.util.Base64;
import java.util.StringTokenizer;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.spec.SecretKeySpec;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUtil {
    private static final String ALGO = "AES";
    private static final byte[] keyValue = new byte[]{'8','0','8','0','m','3','p','1','2','O','f','a','r','m','~','^'};
    
    public static String encrypt(String data) throws Exception {
        Key key = generateKey();
        Cipher cipher = Cipher.getInstance(ALGO);
        cipher.init(Cipher.ENCRYPT_MODE, key);
        byte[] encValue = cipher.doFinal(data.getBytes());
        @SuppressWarnings("oracle.jdeveloper.java.semantic-warning")
        String encryptedValue = Base64.getEncoder().encodeToString(encValue);
        return encryptedValue;
    }
    
    public static String decrypt(String encryptedData) throws Exception {
        Key key = generateKey();
        Cipher cipher = Cipher.getInstance(ALGO);
        cipher.init(Cipher.DECRYPT_MODE, key);
        @SuppressWarnings("oracle.jdeveloper.java.semantic-warning")
        byte[] decordedValue = Base64.getDecoder().decode(encryptedData);
        byte[] decValue = cipher.doFinal(decordedValue);
        String decryptedValue = new String(decValue);
        return decryptedValue;
    }
    
    private static Key generateKey() throws Exception {
        Key key = new SecretKeySpec(keyValue, ALGO);
        return key;
    }
    
    public static boolean passwordCommonDecrypt(String password, String passwordInput, String deskey) {
        boolean statusLogin = false;
        KeyGenerator keyGen = null;
        try {
            keyGen = KeyGenerator.getInstance("DES");
        } catch (Exception e) {
            e.printStackTrace();
        }

        Cipher cipher = null;
        if (keyGen != null) {
            try {
                cipher = Cipher.getInstance("DES");
                StringTokenizer tokenizer = new StringTokenizer(deskey, ",");
                byte[] raw2 = new byte[tokenizer.countTokens()];
                int tokenSize = tokenizer.countTokens();
                for (int i = 0; i < tokenSize; i++) {
                    String token = tokenizer.nextToken();
                    raw2[i] = Byte.parseByte(token);
                }

                tokenizer = new StringTokenizer(password, ",");
                byte[] encryptedPassword = new byte[tokenizer.countTokens()];
                tokenSize = tokenizer.countTokens();
                for (int i = 0; i < tokenSize; i++) {
                    String token = tokenizer.nextToken();
                    encryptedPassword[i] = Byte.parseByte(token);
                }

                SecretKeySpec keySpec2 = new SecretKeySpec(raw2, "DES");
                cipher.init(Cipher.ENCRYPT_MODE, keySpec2);
                byte[] encryptedPassword2 = cipher.doFinal(passwordInput.getBytes());

                boolean equals = false;
                if (encryptedPassword.length == encryptedPassword2.length) {
                    for (int i = 0; i < encryptedPassword.length; i++) {
                        if (encryptedPassword[i] == encryptedPassword2[i]) {
                            equals = true;
                        } else {
                            equals = false;
                            break;
                        }
                    }
                }

                statusLogin = equals;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return statusLogin;
    }
    
    public static String getCurrentUserId() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return auth.getName(); // atau casting ke CustomUserDetails
    }
}
