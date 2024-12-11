package utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import encryption.MotherEncryption;

public abstract class FileHandler {

    /**
     * Writes a file with encrypted content
     * @param path : String, location where the file will be written
     * @param fileName : String, File name
     * @param content : String, encrypted content to be written to the file
     * @return : String, message warning of operation success or failure
     */
    public static String WriteFile(String path,String fileName, String content){

        try (FileWriter writer = new FileWriter(path+fileName+".txt")) {
            writer.write(content); // Write content to file
            return "The text was written in the"+fileName+".txt";
        } catch (IOException e) {
            //System.err.println("An error has occurred while writing to the file.");
            e.printStackTrace();
        }
        return "An error has occurred while writing to the file.";
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
