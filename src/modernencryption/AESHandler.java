package modernencryption;

import encryption.MotherEncryption;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Scanner;

public class AESHandler extends MotherEncryption {

    private SecretKey secretKey;

    public AESHandler() {
        this.type = "AES";
        try {
            KeyGenerator keyGen = KeyGenerator.getInstance("AES");
            keyGen.init(128); // AES key size
            secretKey = keyGen.generateKey();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error initializing AESHandler: " + e.getMessage());
        }
    }

    @Override
    public String encryption(String message, String key) {
        try {
            String adjustedKey = adjustKey(key); // Automatically adjust the key
            SecretKey secretKey = new SecretKeySpec(Base64.getDecoder().decode(adjustedKey), "AES");
            System.out.println("Using AES Key: " + adjustedKey); // Print the key being used
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            byte[] encryptedBytes = cipher.doFinal(message.getBytes());
            return Base64.getEncoder().encodeToString(encryptedBytes);
        } catch (Exception e) {
            throw new RuntimeException("Error encrypting message: " + e.getMessage());
        }
    }

    @Override
    public String decipher(String messageEncryption, String key) {
        try {
            // Use the provided key directly for decryption
            SecretKey secretKey = new SecretKeySpec(Base64.getDecoder().decode(key), "AES");
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(messageEncryption));
            return new String(decryptedBytes);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Invalid key format. Ensure the key is a valid Base64-encoded AES key.");
        } catch (Exception e) {
            throw new RuntimeException("Error decrypting message: " + e.getMessage());
        }
    }

    @Override
    public Boolean checkInputUserMessageToEncryption(String message) {
        return !message.isEmpty();
    }

    @Override
    public Boolean checkInputUserMessageToDecryption(String message) {
        return !message.isEmpty();
    }

    @Override
    public Boolean checkInputUserKey(String key) {
        if (key == null || key.isEmpty()) {
            System.out.println("Key is empty or invalid. A valid AES key will be generated.");
            return true;
        }
        try {
            byte[] keyBytes = Base64.getDecoder().decode(key);
            if (keyBytes.length == 16) { // AES-128 requires a 16-byte key
                return true;
            } else {
                System.out.println("Invalid key length. Key must be a Base64-encoded 16-byte AES key. A valid key will be generated.");
                return true;
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid key format. A valid AES key will be generated.");
            return true;
        }
    }

    /**
     * Generates a pseudo-random byte using a simple LFSR approach.
     * If the seed is empty, it uses a default fallback.
     *
     * @param seedBytes The seed for the LFSR.
     * @return A pseudo-random byte.
     */
    private byte generateLFSRByte(byte[] seedBytes) {
        int seed = seedBytes.length > 0 ? seedBytes[0] : 0xA5; // Default seed
        int lfsr = seed & 0xFF;
        int tap = 0xB8; // Example polynomial tap (XOR mask)

        for (int i = 0; i < 8; i++) { // Generate 8 bits
            int bit = (lfsr ^ tap) & 1;
            lfsr = (lfsr >> 1) | (bit << 7);
        }

        return (byte) lfsr;
    }

    /**
     * Completes or adjusts the key to ensure it is 16 bytes for AES-128.
     * Automatically generates or adjusts the key and prints it for transparency.
     *
     * @param key The user-provided key.
     * @return A Base64-encoded valid AES key.
     */
    private String adjustKey(String key) {
        byte[] keyBytes;

        try {
            keyBytes = Base64.getDecoder().decode(key);
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid key format. A valid AES key will be generated.");
            return generateKey();
        }

        if (keyBytes.length != 16) {
            System.out.println("Invalid key length. A valid AES key will be generated.");
            return generateKey();
        }

        return Base64.getEncoder().encodeToString(keyBytes);
    }

    public String generateKey() {
        try {
            KeyGenerator keyGen = KeyGenerator.getInstance("AES");
            keyGen.init(128); // AES key size
            SecretKey secretKey = keyGen.generateKey();
            String generatedKey = Base64.getEncoder().encodeToString(secretKey.getEncoded());
            System.out.println("Generated AES Key: " + generatedKey);
            return generatedKey;
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error generating AES key: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        AESHandler aesHandler = new AESHandler();
        Scanner scanner = new Scanner(System.in);

        System.out.println("Generated AES Key: " + aesHandler.generateKey());

        boolean backToMainMenu = false;
        while (!backToMainMenu) {
            aesHandler.controlPoster(scanner);
        }
    }
}