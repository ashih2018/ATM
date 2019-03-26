package ATM_0354_phase2;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class PasswordHash {

    public static byte[] getSalt() {
        SecureRandom secureRandom = new SecureRandom();
        byte[] salt = new byte[16];
        secureRandom.nextBytes(salt);
        return salt;
    }

    public static String hashPassword(String password, String salt){
        try {
            StringBuilder hashedPassword = new StringBuilder();
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-512");
            messageDigest.update(salt.getBytes());
            byte[] byteArr = messageDigest.digest(password.getBytes());
            for(int i=0; i< byteArr.length ;i++)
            {
                hashedPassword.append(Integer.toString((byteArr[i] & 0xff) + 256, 16).substring(1));
            }
            return hashedPassword.toString();
        }
        catch (NoSuchAlgorithmException e) {
            return "No such hashing algorithm.";
        }
    }
}