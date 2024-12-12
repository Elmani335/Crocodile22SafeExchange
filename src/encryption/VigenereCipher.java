package encryption;

import java.util.Scanner;

public class VigenereCipher extends MotherEncryption{

    public VigenereCipher(){
        this.type = "Vigenere";
    }

    /**
     * Encrypts the given text using the Vigenère cipher with the provided key.
     * 
     * @param text The plaintext message to be encrypted.
     * @param key  The encryption key.
     * @return The encrypted message.
     */
    @Override
    public String encryption(String text, String key) {
        StringBuilder result = new StringBuilder();
        text = text.toLowerCase();
        key = key.toLowerCase();

        for (int i = 0, j = 0; i < text.length(); i++) {
            char pi = text.charAt(i);
            if (pi < 'a' || pi > 'z') { // If not a lowercase letter
                result.append(pi); // Append as is
                continue;
            }
            char ki = key.charAt(j % key.length());
            char ci = (char) (((pi - 'a' + ki - 'a') % 26) + 'a');
            result.append(ci);
            j++; // Only increment key index if the character is a letter
        }

        return result.toString();
    }

    /**
     * Decrypts the given encrypted text using the Vigenère cipher with the provided key.
     * 
     * @param encryptedText The message to be decrypted.
     * @param key           The decryption key.
     * @return The decrypted message.
     */
    @Override
    public String decipher(String encryptedText, String key) {
        StringBuilder result = new StringBuilder();
        encryptedText = encryptedText.toLowerCase();
        key = key.toLowerCase();

        for (int i = 0, j = 0; i < encryptedText.length(); i++) {
            char ci = encryptedText.charAt(i);
            if (ci < 'a' || ci > 'z') { // If not a lowercase letter
                result.append(ci); // Append as is
                continue;
            }
            char ki = key.charAt(j % key.length());
            char pi = (char) (((ci - ki + 26) % 26) + 'a');
            result.append(pi);
            j++; // Only increment key index if the character is a letter
        }

        return result.toString();
    }

    @Override
    public Boolean checkInputUserKey(String key){
        return !key.isEmpty();
    }

}