package hashing;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Hash {

    // Method to calculate the MD5 hash of a message
    public static String calculateMD5(String message) {
        try {
            // Initialize MessageDigest with MD5
            MessageDigest md = MessageDigest.getInstance("MD5");

            // Compute hash and convert to byte array
            byte[] hashBytes = md.digest(message.getBytes());

            // Convert hash bytes to hexadecimal string
            StringBuilder hexString = new StringBuilder();
            for (byte b : hashBytes) {
                hexString.append(String.format("%02x", b));
            }

            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("MD5 algorithm not supported", e);
        }
    }
}
