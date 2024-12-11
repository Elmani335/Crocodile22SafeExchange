package hashing;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;

public class MD5Hash {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n--- MD5 Hash Calculator ---");
            System.out.println("1. Calculate MD5 hash of a message");
            System.out.println("2. Exit");
            System.out.print("Choose an option: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume the newline

            switch (choice) {
                case 1:
                    System.out.print("Enter the message to hash: ");
                    String message = scanner.nextLine();
                    
                    try {
                        String hash = calculateMD5(message);
                        System.out.println("\nOriginal Message: " + message);
                        System.out.println("MD5 Hash: " + hash);
                    } catch (NoSuchAlgorithmException e) {
                        System.out.println("Error calculating hash: " + e.getMessage());
                    }
                    break;

                case 2:
                    System.out.println("Goodbye!");
                    scanner.close();
                    System.exit(0);
                    break;

                default:
                    System.out.println("Invalid option. Please try again.");
            }
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
