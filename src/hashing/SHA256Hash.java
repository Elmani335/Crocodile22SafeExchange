package hashing;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SHA256Hash extends MotherHash{
        
    public SHA256Hash(){
        this.type = "SHA256";
        this.FOLDER_PATH = "hashed_messages256/";
    }

    // Method to calculate the SHA-256 hash
    @Override
    public String calculateHash(String message) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(message.getBytes());
        byte[] hashBytes = md.digest();

        StringBuilder hexHash = new StringBuilder();
        for (byte b : hashBytes) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) hexHash.append('0');
            hexHash.append(hex);
        }

        return hexHash.toString();
    }
}
