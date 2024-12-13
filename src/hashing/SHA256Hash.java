package hashing;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SHA256Hash extends MotherHash {

    public SHA256Hash() {
        this.type = "SHA256";
        this.FOLDER_PATH = "hashed_messages256/";
    }

    /**
     * Method to calculate the SHA-256 hash of a message.
     * This method takes a message as a string and returns the SHA-256 hash
     * as a hexadecimal string.
     * 
     * @param message The message to hash.
     * @return The SHA-256 hash of the message in hexadecimal format.
     * @throws NoSuchAlgorithmException If the SHA-256 algorithm is not available.
     */
    @Override
    public String calculateHash(String message) throws NoSuchAlgorithmException {

        MessageDigest md = MessageDigest.getInstance("SHA-256");

        // Update the digest with the bytes of the message
        md.update(message.getBytes());

        // Compute the hash
        byte[] hashBytes = md.digest();

        // Convert the hash into hexadecimal representation
        StringBuilder hexHash = new StringBuilder();
        for (byte b : hashBytes) {
            // Convert each byte to a hexadecimal string
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) hexHash.append('0');
            hexHash.append(hex);
        }

        return hexHash.toString();
    }
}
