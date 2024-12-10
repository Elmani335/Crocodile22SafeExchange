import hashing.MD5Hash;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        // Read user input from the terminal
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter a message to hash with MD5:");
        String message = scanner.nextLine();

        // Compute MD5 hash
        String md5Hash = MD5Hash.calculateMD5(message);

        System.out.println("MD5 Hash: " + md5Hash);

        // Close the scanner
        scanner.close();
    }
}
