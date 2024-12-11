package hashing;

import java.io.File;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;
import utils.FileHandler;

public class SHA256Hash {
    
    private static final String FOLDER_PATH = "hashed_messages256/";
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String globalMessage = "";
        File folder = createFolder(FOLDER_PATH);

        if (folder == null) scanner.close();

        File file = null;

        while (true) {
            showMenu();
            String choiceInput = scanner.nextLine();

            // Check if the input is a valid number and contains no special characters or letters
            if (!FileHandler.isValidNumber(choiceInput)) {
                System.out.println("Invalid input. Returning to the main menu.");
                continue;
            }

            int choice = Integer.parseInt(choiceInput);
            switch (choice) {
                case 1:
                    file = FileHandler.createFile(scanner, FOLDER_PATH);
                    break;

                case 2:
                    file = FileHandler.selectFile(scanner, FOLDER_PATH);
                    break;

                case 3:
                    file = handleAddMessage(scanner, globalMessage, file);
                    break;

                case 4:
                    FileHandler.compareFileHashes(scanner, FOLDER_PATH);
                    break;

                case 5:
                    System.out.println("Goodbye!");
                    scanner.close();
                    return; // Exit the program

                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    // Method to create folder if it doesn't exist
    private static File createFolder(String folderPath) {
        File folder = new File(folderPath);
        if (!folder.exists() && !folder.mkdirs()) {
            System.out.println("Failed to create directory 'hashed_messages256/'. Exiting.");
            return null;
        }
        System.out.println("Directory 'hashed_messages256/' " + (folder.exists() ? "exists." : "created."));
        return folder;
    }

    // Show the menu options to the user
    private static void showMenu() {
        System.out.println("\n--- SHA-256 Hash Calculator ---");
        System.out.println("1. Create a new file");
        System.out.println("2. Select an existing file");
        System.out.println("3. Add a message and update the hash");
        System.out.println("4. Compare hashes of two files");
        System.out.println("5. Exit");
        System.out.print("Choose an option: ");
    }

    // Handle adding a message to the file and updating the hash
    private static File handleAddMessage(Scanner scanner, String globalMessage, File file) {
        if (file == null) {
            System.out.println("No file selected. Please create or select a file first.");
            return null;
        }

        System.out.print("Enter the message to add: ");
        String message = scanner.nextLine();
        globalMessage += message;

        try {
            // Calculate and print updated hash
            String hash = calculateSHA256(globalMessage);
            System.out.println("\nUpdated SHA-256 Hash: " + hash);

            // Save the updated hash to the selected file
            String result = FileHandler.writeToFileHash(file, hash);
            System.out.println(result);
        } catch (NoSuchAlgorithmException e) {
            System.out.println("Error calculating hash: " + e.getMessage());
        }

        return file;
    }

    // Method to calculate the SHA-256 hash
    public static String calculateSHA256(String message) throws NoSuchAlgorithmException {
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
