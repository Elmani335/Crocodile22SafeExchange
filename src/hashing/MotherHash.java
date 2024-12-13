package hashing;

import java.io.File;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import utils.FileHandler;

public abstract class MotherHash {

    String FOLDER_PATH;
    File selectedFile = null;
    String type;
    String SECRET_SALT = "";
    String SECRET_PEPPER = "pepper";

    /**
     * Abstract method for calculating the hash (implemented by subclasses).
     *
     * @param message The message to hash.
     * @return The hash of the message.
     * @throws NoSuchAlgorithmException If the hashing algorithm is not available.
     */
    public abstract String calculateHash(String message) throws NoSuchAlgorithmException;

    /**
     * Main method that controls user interaction and file management.
     * Provides several options: create a file, select a file, calculate a regular hash or an HMAC.
     *
     * @throws NoSuchAlgorithmException If the hashing algorithm used is not available.
     */
    public void controlPoster() throws NoSuchAlgorithmException {
        Scanner scanner = new Scanner(System.in);
        
        // Create a folder to store hashed messages if not existing
        File folder = new File(FOLDER_PATH);
        if (!folder.exists()) {
            if (folder.mkdirs()) {
                System.out.println("Directory hashed_messages_" + type + "/ created.");
            } else {
                System.out.println("Failed to create directory hashed_messages_" + type + "/. Exiting.");
                scanner.close();
                return;
            }
        } else {
            System.out.println("Directory 'hashed_messages_" + type + "/' exists.");
        }

        boolean isFinish = false;
        while (!isFinish) {
            showMenu(); 
            String choiceInput = scanner.nextLine();

            if (!isValidNumber(choiceInput)) {
                System.out.println("Invalid input. Please try again.");
                continue;
            }

            int choice = Integer.parseInt(choiceInput);
            switch (choice) {
                case 1:
                    selectedFile = FileHandler.createFile(scanner, FOLDER_PATH);
                    break;
                case 2:
                    selectedFile = FileHandler.selectFile(scanner, FOLDER_PATH);
                    break;
                case 3:
                    handleCalculation(scanner, false);
                    break;
                case 4:
                    handleCalculation(scanner, true);
                    break;
                case 5:
                    FileHandler.compareFileHashes(scanner, FOLDER_PATH);
                    break;
                case 0:
                    isFinish = true;
                    return;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    /**
     * Displays the menu of options for user interaction.
     */
    private void showMenu() {
        System.out.println("\n--- " + this.type + " Hash Calculator ---");
        System.out.println("1. Create a new file");
        System.out.println("2. Select an existing file");
        System.out.println("3. Add a message (hash)");
        System.out.println("4. Add a message (HMAC)");
        System.out.println("5. Compare hashes of two files");
        System.out.println("0. Exit");
        System.out.print("Choose an option: ");
    }

    /**
     * Validates if the input string is a valid number (digits only).
     *
     * @param input The user input to validate.
     * @return true if the input is a valid number, false otherwise.
     */
    private boolean isValidNumber(String input) {
        return input.matches("\\d+");
    }

    /**
     * Method to handle hash or HMAC calculation based on the user's choice.
     * If HMAC is selected, it uses salt and pepper in the calculation.
     *
     * @param scanner  The scanner to read user input.
     * @param useHMAC  true to calculate HMAC, false for regular hash.
     * @throws NoSuchAlgorithmException If the hashing or HMAC algorithm is not available.
     */
    private void handleCalculation(Scanner scanner, boolean useHMAC) throws NoSuchAlgorithmException {
        if (selectedFile == null) {
            System.out.println("No file selected. Please create or select a file first.");
            return;
        }
    
        System.out.print("Enter the message to add: ");
        String message = scanner.nextLine();
    
        String resultHash;
        if (useHMAC) {
            // Ask for the secret key when HMAC is being used
            System.out.print("Enter the secret key: ");
            String secretKey = scanner.nextLine();  // Get the secret key from the user
    
            // Calculate HMAC with the secret key
            resultHash = calculateHMAC(message, SECRET_SALT, SECRET_PEPPER, secretKey);
            System.out.println("\nHMAC Hash with Salt + Pepper: " + resultHash);
        } else {
            // Calculate a regular hash
            resultHash = calculateHash(message);
            System.out.println("\nRegular Hash: " + resultHash);
        }
    
        // Write the result to the file
        String result = FileHandler.writeToFileHash(selectedFile, resultHash);
        System.out.println(result);
    }
    

    /**
     * Method to calculate the HMAC (Hash-based Message Authentication Code) with salt and pepper.
     * The message is first combined with the salt and pepper before calculating the HMAC.
     *
     * @param message The message to hash.
     * @param salt    The salt used in the HMAC calculation.
     * @param pepper  The pepper used in the HMAC calculation.
     * @return The HMAC result as a hexadecimal string.
     * @throws NoSuchAlgorithmException If the HMAC algorithm is not available.
     */
    private String calculateHMAC(String message, String salt, String pepper, String secretKey) throws NoSuchAlgorithmException {
        try {
            // Combine the message with salt and pepper
            String messageWithSaltAndPepper = salt + message + pepper;
    
            // Calculates the initial hash
            String initialHash = calculateHash(messageWithSaltAndPepper);
    
            // Adds the secret key to the initial hash
            String hashWithKey = initialHash + secretKey;
    
            // Recalculate the final hash
            String finalHash = calculateHash(hashWithKey);
    
            return finalHash;
        } catch (Exception e) {
            throw new NoSuchAlgorithmException("Error calculating HMAC: " + e.getMessage());
        }
    }
    
    
    
}
