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
     * @param messageEncryption : String, message encrypted
     * @param key               : String, the same key that was used for encryption
     */
    public abstract String decipher(String messageEncryption, String key);

    /**
     * Verify the message to be encrypted. Each child class has its own checks
     * @param message : String, message to check
     * @return : Boolean, true if it matches
     */
    public abstract Boolean checkInputUserMessageToEncryption(String message);

    /**
     * Check the message to be decoded. Each child class has its own checks
     * @param message : String, message to check
     * @return : Boolean, true if it matches
     */
    public abstract Boolean checkInputUserMessageToDecryption(String message);

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

            int choice;

            try {
                choice = getChoiceUser(scanner);

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

    /**
     * Encryption control panel
     * @param scanner : Scanner, object used to retrieve user input
     */
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
            try {

                // Retrieves the user's choice and checks that it's a number
                int choice = getChoiceUser(scanner);
                switch (choice) {

                    // Write to a file
                    case 1:
                        FileHandler.writeToFile("./", "message", encrypted);
                        System.out.println("Message written: " + encrypted);
                        backToMainMenu = true;
                        break;

                    // Display the encrypted message
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
     * Decryption control panel
     * @param scanner : Scanner, object used to retrieve user input
     */
    private void performDecryption(Scanner scanner) {
        boolean backToMainMenu = false;

        System.out.println("Do you want to read a file?");
        System.out.println(" 1. Yes");
        System.out.println(" 0. No");

        while (!backToMainMenu) {
            try {
                int choice = getChoiceUser(scanner);

                switch (choice) {

                    // Read from a file
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

                    // Decrypt a message entered by the user
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

    /**
     * Retrieves the message entered by the user 
     * checking that it matches the verification criteria
     * @param scanner : Scanner
     * @param isEncryption : Boolean, true for encryption, false for decryption
     * @return : String, message entered by user
     */
    public String getMessage(Scanner scanner, boolean isEncryption){
        Boolean tmpMessageIsGood = false;
        String text = "";
        // As long as the message does not meet the criteria
        while (!tmpMessageIsGood) {
            System.out.print("\nEnter the text to encrypt: ");
            text = scanner.nextLine();
            
            // Check if the message meets the verification criteria
            if (isEncryption){
                // If it's for encryption

                if (checkInputUserMessageToEncryption(text)){
                    tmpMessageIsGood = true;
                }else{
                    System.out.println("Error, please enter a message containing lowercase letters only");
                }
            }else{
                if (checkInputUserMessageToDecryption(text)){
                    tmpMessageIsGood = true;
                }
            }

        }
        return text;
    }

    /**
     * Retrieve the key entered by the user 
     * passing it to the child class verification method
     * @param scanner : Scanner
     * @return : String, the key entered by the user
     */
    public String getKey(Scanner scanner) {
        Boolean tmpKeyIsGood = false;
        String key = "";
        while (!tmpKeyIsGood) {
            System.out.print("\nEnter the encryption/decryption key: ");
            key = scanner.nextLine();

            // Call the verification method overwritten in child classes
            if (checkInputUserKey(key)) {
                tmpKeyIsGood = true;
            } else {
                System.out.println("Error, please enter a valid key");
            }
        }
        return key;
    }

    /**
     * Retrieves the user's choice, verifying that it's a number they're entering
     * @param scanner : Scanner
     * @return : int, the number entered by the user
     */
    public int getChoiceUser(Scanner scanner){

        boolean choiceIsValide = false;
        String choice = "";
        
        while (!choiceIsValide) {
            System.out.print("Select an option: ");
            choice = scanner.nextLine().trim();

            // Check that it's an int
            if (choice.matches(".*\\\\d.*")){
                choiceIsValide = true;
            }else{
                System.out.println("Please enter a number");
            }

        }

        return Integer.parseInt(choice);
    }
}