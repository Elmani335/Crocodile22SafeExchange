package utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import encryption.MotherEncryption;

public abstract class FileHandler {    
    
    private static final String INVALID_INPUT_MESSAGE = "Invalid input. Returning to the main menu.";

    /**
     * Writes content to a file. Appends if the file already exists.
     *
     * @param path     The path where the file is located or will be created.
     * @param fileName The name of the file (without extension).
     * @param content  The content to write into the file.
     * @return Status message of the write operation.
     */
    public static String writeToFile(String path, String fileName, String content) {
        try (FileWriter writer = new FileWriter(path + fileName + ".txt", true)) { // 'true' enables appending
            writer.write(content + System.lineSeparator()); // Appends content to the file with a new line
            return "The text was appended to " + fileName + ".txt";
        } catch (IOException e) {
            e.printStackTrace();
            return "An error has occurred while writing to the file.";
        }
    }

    /**
     * @param fileName: Location of the file to be read
     * @param motherEncryption: Class with which the file has been encrypted
     * @param key : String, key used for encryption
     * @return String, the contents of the decrypted file
     */
    public static String ReadFile(String fileName, MotherEncryption motherEncryption, String key){

        StringBuilder result = new StringBuilder();
        // Reads the file line by line, then decrypts them by calling the method common to each encryption class
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            System.out.println("Play file: "+fileName);
            String ligne;
            while ((ligne = br.readLine()) != null) {
                result.append(motherEncryption.decipher(ligne,key));
            }
        } catch (IOException e) {
            result.append("Error reading file : " + e.getMessage());
        }
        return result.toString();
    }

    /**
     * Reads the hash from a file.
     * 
     * @param file The file from which the hash will be read.
     * @return The hash content from the file.
     * @throws IOException If there is an error reading the file or if the file is empty.
     */
    public static String readHashFromFile(File file) throws IOException {
        try (Scanner fileScanner = new Scanner(file)) {
            if (fileScanner.hasNextLine()) {
                return fileScanner.nextLine().trim();
            } else {
                throw new IOException("The file is empty.");
            }
        }
    }

    /**
     * Compares the hashes of two selected files.
     * 
     * @param scanner    The scanner used to capture user input.
     * @param folderPath The path of the folder where the files are located.
     */
    public static void compareFileHashes(Scanner scanner, String folderPath) {
        System.out.println("Select two files to compare their hashes:");

        File[] files = new File(folderPath).listFiles((dir, name) -> name.endsWith(".txt"));

        if (files == null || files.length < 2) {
            System.out.println("Not enough files to compare.");
            return;
        }

        // Display files
        for (int i = 0; i < files.length; i++) {
            System.out.println((i + 1) + ". " + files[i].getName());
        }

        // Get user input for file selection
        System.out.print("Enter the number of the first file: ");
        String fileChoice1Input = scanner.nextLine();

        if (!isValidNumber(fileChoice1Input)) {
            System.out.println(INVALID_INPUT_MESSAGE);
            return;
        }

        System.out.print("Enter the number of the second file: ");
        String fileChoice2Input = scanner.nextLine();

        if (!isValidNumber(fileChoice2Input)) {
            System.out.println(INVALID_INPUT_MESSAGE);
            return;
        }

        int fileChoice1 = Integer.parseInt(fileChoice1Input);
        int fileChoice2 = Integer.parseInt(fileChoice2Input);

        if (fileChoice1 < 1 || fileChoice1 > files.length || fileChoice2 < 1 || fileChoice2 > files.length || fileChoice1 == fileChoice2) {
            System.out.println("Invalid choice(s). Please try again.");
            return;
        }

        File file1 = files[fileChoice1 - 1];
        File file2 = files[fileChoice2 - 1];

        try {
            String hash1 = readHashFromFile(file1);
            String hash2 = readHashFromFile(file2);

            if (hash1.equals(hash2)) {
                System.out.println("The hashes of the two files are identical.");
            } else {
                System.out.println("The hashes of the two files are different.");
            }

        } catch (IOException e) {
            System.out.println("Error reading the files: " + e.getMessage());
        }
    }

    /**
     * Validates if the given input is a valid number.
     * 
     * @param input The string input to validate.
     * @return true if the input contains only digits, false otherwise.
     */
    public static boolean isValidNumber(String input) {
        // Regular expression to match digits only (no letters, no special characters)
        return input.matches("\\d+");
    }

    /**
     * Allows the user to select a file from a folder by entering its number.
     * 
     * @param scanner    The scanner used to capture user input.
     * @param folderPath The path of the folder where the files are located.
     * @return The selected file or null if invalid selection.
     */
    public static File selectFile(Scanner scanner, String folderPath) {
        System.out.println("Files in the directory:");
        File folder = new File(folderPath);
        File[] files = folder.listFiles((dir, name) -> name.endsWith(".txt"));

        if (files == null || files.length == 0) {
            System.out.println("No files found in the directory.");
            return null;
        }

        for (int i = 0; i < files.length; i++) {
            System.out.println((i + 1) + ". " + files[i].getName());
        }

        System.out.print("Enter the number of the file to select: ");
        String fileChoiceInput = scanner.nextLine();

        if (!isValidNumber(fileChoiceInput)) {
            System.out.println(INVALID_INPUT_MESSAGE);
            return null;
        }

        int fileChoice = Integer.parseInt(fileChoiceInput);

        if (fileChoice < 1 || fileChoice > files.length) {
            System.out.println("Invalid choice. Please try again.");
            return null;
        }

        File selectedFile = files[fileChoice - 1];
        System.out.println("File '" + selectedFile.getName() + "' selected.");
        return selectedFile;
    }

    /**
     * Creates a new file in the specified folder.
     * 
     * @param scanner    The scanner used to capture user input for the file name.
     * @param folderPath The path of the folder where the file will be created.
     * @return The created file or null if there is an error.
     */
    public static File createFile(Scanner scanner, String folderPath) {
        System.out.print("Enter the name of the new file (without extension): ");
        String fileName = scanner.nextLine();
        File file = new File(folderPath + fileName + ".txt");

        if (file.exists()) {
            System.out.println("File already exists. Please choose a different name.");
            return null;
        }

        try {
            if (file.createNewFile()) {
                System.out.println("File '" + fileName + ".txt' created.");
                return file;
            } else {
                System.out.println("Failed to create file '" + fileName + ".txt'.");
            }
        } catch (IOException e) {
            System.out.println("Error creating file: " + e.getMessage());
        }

        return null;
    }

    /**
     * Writes the hash content to a file, overwriting any existing content.
     * 
     * @param file    The file where the hash will be written.
     * @param content The hash content to write to the file.
     * @return Status message of the write operation.
     */
    public static String writeToFileHash(File file, String content) {
        try (FileWriter writer = new FileWriter(file, false)) { // Overwrites the file with the updated hash
            writer.write(content);
            return "The updated hash was saved to " + file.getName();
        } catch (IOException e) {
            e.printStackTrace();
            return "An error occurred while writing to the file.";
        }
    }

}
