package encryption;

import java.util.Scanner;
import utils.FileHandler;

public abstract class MotherEncryption {

    // Encryption type
    String type;

    /**
     * Encrypts a message according to a key
     *
     * @param message : String, words to encrypt
     * @param key     : String, key used to encrypt
     * @return String : Ciphered words
     */
    public abstract String encryption(String message, String key);

    /**
     * Decrypts the message according to a key
     *
     * @param messageEncryption : String, message encrypted
     * @param key               : String, the same key that was used for encryption
     */
    public abstract String decipher(String messageEncryption, String key);

    /**
     * As long as the message does not respect the criteria Checks a String to see if it matches
     * the criteria Must be composed of letters only
     *
     * @param message : String, message to check
     * @return : Boolean, true if it matches, false if it doesn't
     */
    public Boolean checkInputUserMessage(String message) {
        return !message.isEmpty() && message.matches("[a-z]+");
    }

    /**
     * Verifies the key entered by the user. Each child class has its own verification manners
     *
     * @param key : String, key to check
     * @return : Boolean, true if it meets criteria, false otherwise
     */
    public abstract Boolean checkInputUserKey(String key);

    /**
     * Interaction with the user using an encryption method
     */
    public void controlPoster(Scanner scanner) {
        boolean backToMainMenu = false;

        // Ask if it wants to encrypt or decrypt
        while (!backToMainMenu) {
            System.out.println("\n=====================================");
            System.out.println("           " + type + " MENU            ");
            System.out.println("=====================================");
            System.out.println(" 1. Encrypt");
            System.out.println(" 2. Decrypt");
            System.out.println(" 0. <- Back to Main Menu");
            System.out.println("=====================================");
            System.out.print("Select an option: ");

            String input = scanner.nextLine();
            int choice;

            try {
                choice = Integer.parseInt(input);

                switch (choice) {
                    case 1:
                        // Encryption
                        performEncryption(scanner);
                        break;
                    case 2:
                        // Decryption
                        performDecryption(scanner);
                        break;
                    case 0:
                        backToMainMenu = true;
                        break;
                    default:
                        System.out.println("\nInvalid option, please choose a valid number from the list.");
                }
            } catch (NumberFormatException e) {
                System.out.println("\nInvalid input, please enter a number.");
            }
        }
    }

    /**
     * Encrypting a message
     */
    private void performEncryption(Scanner scanner) {
        // prompts user to enter message and key
        String text = getMessage(scanner);
        String key = getKey(scanner);
    
        String encrypted = encryption(text, key);
        System.out.println("Encrypted Message: " + encrypted);
    
        // Ask the user if he wants to write the response to a file
        System.out.println("Do you want to write it to a file?");
        System.out.println(" 1. Yes");
        System.out.println(" 0. No");
    
        boolean backToMainMenu = false;
        while (!backToMainMenu) {
            String response = scanner.nextLine().trim();
            int choice;
            try {
                choice = Integer.parseInt(response);
    
                // Enables you to write to the file
                switch (choice) {
                    case 1:
                        // Write to file
                        FileHandler.writeToFile("./", "message", encrypted);
                        System.out.println("Message written: " + encrypted);
                        backToMainMenu = true;
                        break;
                    case 0:
                        System.out.println("Message encrypted: " + encrypted);
                        backToMainMenu = true;
                        break;
                    default:
                        System.out.println("\nInvalid option, please choose a valid number.");
                }
            } catch (NumberFormatException e) {
                System.out.println("\nInvalid input, please enter a number.");
            }
        }
    }

    /**
     * Requests the user to decrypt a message or file
     */
    private void performDecryption(Scanner scanner) {
        boolean backToMainMenu = false;
    
        while (!backToMainMenu) {
            System.out.println("Do you want to read a file?");
            System.out.println(" 1. Yes");
            System.out.println(" 0. No");
            
            String response = scanner.nextLine().trim(); 
            int choice;
    
            try {
                choice = Integer.parseInt(response);
    
                switch (choice) {
                    // Play a file
                    case 1:
                        System.out.print("Read...");
                        System.out.print("Enter the decryption key: ");
                        String key = getKey(scanner);
                        String resultFile = FileHandler.ReadFile("./message.txt", this, key);
                        System.out.print(resultFile);
                        backToMainMenu = true;
                        break;
    
                    // Decrypt a message
                    case 0:
                        // Message retrieval
                        System.out.print("\nEnter the text to decrypt: ");
                        String text = getMessage(scanner);
    
                        // Key recovery
                        System.out.print("Enter the decryption key: ");
                        key = getKey(scanner);
    
                        if (text.isEmpty() || key.isEmpty()) {
                            System.out.println("Text and key cannot be empty.");
                            break;
                        }
    
                        // Message decryption
                        String decrypted = decipher(text, key);
                        System.out.println("Decrypted Message: " + decrypted);
                        backToMainMenu = true;
                        break;
                    default:
                        System.out.println("\nInvalid option, please choose a valid number.");
                }
            } catch (NumberFormatException e) {
                System.out.println("\nInvalid input, please enter a number.");
            }
        }
    }

    /**
     * Retrieves and checks the message to be encrypted or decrypted if it matches certain criteria.
     *
     * @param scanner : Scanner, instance of a scanner object
     * @return : String, the message to be encrypted or decrypted
     */
    public String getMessage(Scanner scanner) {
        Boolean tmpMessageIsGood = false;
        String text = "";

        // As long as the message does not meet the criteria
        while (!tmpMessageIsGood) {
            System.out.print("\nEnter the text to encrypt: ");
            text = scanner.nextLine();
            if (checkInputUserMessage(text)) {
                tmpMessageIsGood = true;
            } else {
                System.out.println("Error, please enter a message containing lowercase letters only");
            }
        }
        return text;
    }

    /**
     * Retrieval and verification of the key entered by the user
     *
     * @param scanner : Scanner, instance of a scanner object
     * @return : String, the key entered by the user corresponding to the criterion
     */
    public String getKey(Scanner scanner) {
        Boolean tmpKeyIsGood = false;
        String key = "";
        while (!tmpKeyIsGood) {
            System.out.print("\nEnter the encryption key: ");
            key = scanner.nextLine();

            // Calls the verification method specific to each child class
            if (checkInputUserKey(key)) {
                tmpKeyIsGood = true;
            } else {
                System.out.println("Error, please enter a valid key");
            }
        }
        return key;
    }
}