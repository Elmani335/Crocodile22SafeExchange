package encryption;

import java.util.Scanner;

public class VigenereCipher {

    /**
     * Encrypts the given text using the Vigenère cipher with the provided key.
     * 
     * @param text The plaintext message to be encrypted.
     * @param key  The encryption key.
     * @return The encrypted message.
     */
    public static String cipher(String text, String key) {
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
    public static String decrypt(String encryptedText, String key) {
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

    /**
     * Main method to interact with the Vigenère cipher encryption/decryption.
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean exit = false;

        while (!exit) {
            System.out.println("\n=====================================");
            System.out.println("         VIGENERE CIPHER MENU        ");
            System.out.println("=====================================");
            System.out.println(" 1. Encrypt a message");
            System.out.println(" 2. Decrypt a message");
            System.out.println(" 0. Exit");
            System.out.println("=====================================");
            System.out.print("Select an option: ");

            String input = scanner.nextLine();
            int choice;
            
            try {
                choice = Integer.parseInt(input);

                switch (choice) {
                    case 1: // Encryption
                        System.out.print("\nEnter the text to encrypt: ");
                        String text = scanner.nextLine();
                        System.out.print("Enter the encryption key: ");
                        String encryptKey = scanner.nextLine();
                        if (text.isEmpty() || encryptKey.isEmpty()) {
                            System.out.println("Text and key cannot be empty.");
                            break;
                        }
                        String encrypted = cipher(text, encryptKey);
                        System.out.println("Encrypted Message: " + encrypted);
                        break;
                    case 2: // Decryption
                        System.out.print("\nEnter the text to decrypt: ");
                        String encryptedText = scanner.nextLine();
                        System.out.print("Enter the decryption key: ");
                        String decryptKey = scanner.nextLine();
                        if (encryptedText.isEmpty() || decryptKey.isEmpty()) {
                            System.out.println("Text and key cannot be empty.");
                            break;
                        }
                        String decrypted = decrypt(encryptedText, decryptKey);
                        System.out.println("Decrypted Message: " + decrypted);
                        break;
                    case 0:
                        exit = true;
                        System.out.println("\nExiting the application. Goodbye!");
                        break;
                    default:
                        System.out.println("\nInvalid option, please choose a valid number.");
                }
            } catch (NumberFormatException e) {
                System.out.println("\nInvalid input, please enter a number.");
            }
        }
        scanner.close();
    }
}