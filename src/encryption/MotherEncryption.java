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
                        performEncryption(scanner);
                        break;
                    case 2:
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

    private void performEncryption(Scanner scanner) {
        String text = getMessage(scanner, true); // Correct context for encryption
        String key = getKey(scanner);

        String encrypted = encryption(text, key);
        System.out.println("Encrypted Message: " + encrypted);

        System.out.println("Do you want to write it to a file?");
        System.out.println(" 1. Yes");
        System.out.println(" 0. No");

        boolean backToMainMenu = false;
        while (!backToMainMenu) {
            String response = scanner.nextLine().trim();
            try {
                int choice = Integer.parseInt(response);
                switch (choice) {
                    case 1:
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

    private void performDecryption(Scanner scanner) {
        boolean backToMainMenu = false;

        System.out.println("Do you want to read a file?");
        System.out.println(" 1. Yes");
        System.out.println(" 0. No");

        while (!backToMainMenu) {
            String response = scanner.nextLine().trim();
            try {
                int choice = Integer.parseInt(response);

                switch (choice) {
                    case 1:
                        System.out.print("Enter the decryption key: ");
                        String fileKey = getKey(scanner);
                        String resultFile = FileHandler.ReadFile("./message.txt", this, fileKey);

                        if (resultFile != null && !resultFile.isEmpty()) {
                            System.out.println("Decrypted Message: " + resultFile);
                        } else {
                            System.out.println("Failed to read or decode the file.");
                        }
                        backToMainMenu = true;
                        break;

                    case 0:
                        String text = getMessage(scanner, false); // Correct context for decryption
                        String key = getKey(scanner);

                        if (!text.isEmpty() && !key.isEmpty()) {
                            String decrypted = decipher(text, key);
                            System.out.println("Decrypted Message: " + decrypted);
                        } else {
                            System.out.println("Text and key cannot be empty.");
                        }
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

    public String getMessage(Scanner scanner, boolean isEncrypting) {
        Boolean tmpMessageIsGood = false;
        String text = "";
    
        while (!tmpMessageIsGood) {
            if (isEncrypting) {
                System.out.print("\nEnter the text to encrypt: ");
            } else if (type.equalsIgnoreCase("RC4")) {
                System.out.print("\nEnter the base64 encoded text to decrypt: ");
            } else {
                System.out.print("\nEnter the text to decrypt: ");
            }
    
            text = scanner.nextLine();
    
            if (isEncrypting) {
                if (checkInputUserMessage(text)) {
                    tmpMessageIsGood = true;
                } else {
                    System.out.println("Error, please enter a message containing lowercase letters only.");
                }
            } else {
                if (type.equalsIgnoreCase("RC4")) {
                    if (!text.isEmpty()) {
                        tmpMessageIsGood = true;
                    } else {
                        System.out.println("Error, please enter a valid base64 encoded message.");
                    }
                } else {
                    if (checkInputUserMessage(text)) {
                        tmpMessageIsGood = true;
                    } else {
                        System.out.println("Error, please enter a message containing lowercase letters only.");
                    }
                } 
            }
        }
        return text;
    }

    public String getKey(Scanner scanner) {
        Boolean tmpKeyIsGood = false;
        String key = "";
        while (!tmpKeyIsGood) {
            System.out.print("\nEnter the encryption/decryption key: ");
            key = scanner.nextLine();

            if (checkInputUserKey(key)) {
                tmpKeyIsGood = true;
            } else {
                System.out.println("Error, please enter a valid key");
            }
        }
        return key;
    }
}