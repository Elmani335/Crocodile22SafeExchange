package utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import encryption.MotherEncryption;

public abstract class FileHandler {

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

}
