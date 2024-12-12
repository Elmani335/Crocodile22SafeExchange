package hashing;

import java.io.File;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;

import utils.FileHandler;

public abstract class MotherHash {
    
    String FOLDER_PATH;
    File selectedFile = null;
    String type;

    public abstract String calculateHash(String message)throws NoSuchAlgorithmException;

    public void controlPoster() throws NoSuchAlgorithmException{
        Scanner scanner = new Scanner(System.in);

        File folder = new File(FOLDER_PATH);
        if (!folder.exists()) {
            if (folder.mkdirs()) {
                System.out.println("Directory hashed_messages_"+type+"/ created.");
            } else {
                System.out.println("Failed to create directory hashed_messages_"+type+"/. Exiting.");
                scanner.close();            }
        } else {
            System.out.println("Directory 'hashed_messages_"+type+"/' exists.");
        }
        boolean isFinish = false;
        while (!isFinish) {
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
                    handleCalculation(scanner);
                    break;
                case 4:
                    FileHandler.compareFileHashes(scanner, FOLDER_PATH);
                    break;
                case 0:
                    isFinish = true;
                    return; // Exit the program
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private void showMenu() {
        System.out.println("\n--- "+ this.type +" Hash Calculator ---");
        System.out.println("1. Create a new file");
        System.out.println("2. Select an existing file");
        System.out.println("3. Add a message");
        System.out.println("4. Compare hashes of two files");
        System.out.println("0. Exit");
        System.out.print("Choose an option: ");
    }

    // Validate if the input is a number (no special characters or letters)
    private boolean isValidNumber(String input) {
        return input.matches("\\d+"); // Matches digits only
    }

    private void handleCalculation(Scanner scanner) throws NoSuchAlgorithmException {
        if (selectedFile == null) {
            System.out.println("No file selected. Please create or select a file first.");
            return;
        }
    
        System.out.print("Enter the message to add: ");
        String message = scanner.nextLine();
        
        // Calculate the hash of the message
        String hash = calculateHash(message);
        System.out.println("\nMD5 Hash: " + hash);
   
        //Save hash to file
        String result = FileHandler.writeToFileHash(selectedFile, hash);
        System.out.println(result);
    }

}
