package hashing;

import java.io.File;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;
import utils.FileHandler;

public class MD5Hash {

    private static final String FOLDER_PATH = "hashed_messages_md5/";
    private static final String EXIT_MESSAGE = "Goodbye!";
    private static File selectedFile = null;
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        //Create folder if it doesn't exist
        File folder = new File(FOLDER_PATH);
        if (!folder.exists()) {
            if (folder.mkdirs()) {
                System.out.println("Directory 'hashed_messages_md5/' created.");
            } else {
                System.out.println("Failed to create directory 'hashed_messages_md5/'. Exiting.");
                scanner.close();            }
        } else {
            System.out.println("Directory 'hashed_messages_md5/' exists.");
        }

        while (true) {
            showMenu();
            String choiceInput = scanner.nextLine();

            // Check if the input is a valid number and contains no special characters or letters
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
                    handleMD5Calculation(scanner);
                    break;
                case 4:
                    FileHandler.compareFileHashes(scanner, FOLDER_PATH);
                    break;
                case 5:
                    System.out.println(EXIT_MESSAGE);
                    scanner.close();
                    return; // Exit the program
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    // Show the menu options to the user
    private static void showMenu() {
        System.out.println("\n--- MD5 Hash Calculator ---");
        System.out.println("1. Create a new file");
        System.out.println("2. Select an existing file");
        System.out.println("3. Add a message");
        System.out.println("4. Compare hashes of two files");
        System.out.println("5. Exit");
        System.out.print("Choose an option: ");
    }

    // Validate if the input is a number (no special characters or letters)
    private static boolean isValidNumber(String input) {
        return input.matches("\\d+"); // Matches digits only
    }

    // Handle MD5 hash calculation for the user's input message
    private static void handleMD5Calculation(Scanner scanner) {
        if (selectedFile == null) {
            System.out.println("No file selected. Please create or select a file first.");
            return;
        }
    
        System.out.print("Enter the message to add: ");
        String message = scanner.nextLine();
        
        try {
            // Calculate the hash of the message
            String hash = calculateMD5(message);
            System.out.println("\nMD5 Hash: " + hash);
    
            //Save hash to file
            String result = FileHandler.writeToFileHash(selectedFile, hash);
            System.out.println(result);
    
        } catch (NoSuchAlgorithmException e) {
            System.out.println("Error calculating hash: " + e.getMessage());
        }
    }

    // Method to calculate the MD5 hash
    public static String calculateMD5(String message) throws NoSuchAlgorithmException {
        // Get a MessageDigest instance for MD5
        MessageDigest md = MessageDigest.getInstance("MD5");

        // Update the digest with the bytes of the message
        md.update(message.getBytes());

        // Compute the hash
        byte[] hashBytes = md.digest();

        // Convert the hash into hexadecimal representation
        StringBuilder hexHash = new StringBuilder();
        for (byte b : hashBytes) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) hexHash.append('0');
            hexHash.append(hex);
        }

        return hexHash.toString();
    }
}
