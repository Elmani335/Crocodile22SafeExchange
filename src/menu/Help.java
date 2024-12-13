package menu;

import java.util.Scanner;

public class Help {
    public void display(Scanner scanner) {
        boolean backToMainMenu = false;

        while (!backToMainMenu) {
            System.out.println("\n=====================================");
            System.out.println("             HELP MENU              ");
            System.out.println("=====================================");
            System.out.println(" 1. Vigenère Encryption");
            System.out.println(" 2. Polybius Square");
            System.out.println(" 3. ROT(X) Encryption");
            System.out.println(" 4. Enigma Machine");
            System.out.println(" 5. RC4 Algorithm");
            System.out.println(" 6. MD5 Hashing");
            System.out.println(" 7. SHA-256 Hashing");
            System.out.println(" 8. Generate Pseudo Random Number");
            System.out.println(" 9. Steganography");
            System.out.println("10. AES Encryption");
            System.out.println(" 0. <- Go to Main Menu");
            System.out.println("=====================================");
            System.out.print("Select a topic to learn more: ");
            
            String input = scanner.nextLine();
            int choice;

            try {
                choice = Integer.parseInt(input);

                switch (choice) {
                    case 1:
                        displayVigenereHelp(scanner);
                        break;
                    case 2:
                        displayPolybiusSquareHelp(scanner);
                        break;
                    case 3:
                        displayRotXHelp(scanner);
                        break;
                    case 4:
                        displayEnigmaHelp(scanner);
                        break;
                    case 5:
                        displayRC4Help(scanner);
                        break;
                    case 6:
                        displayMD5Help(scanner);
                        break;
                    case 7:
                        displaySHA256Help(scanner);
                        break;
                    case 8:
                        displayRandomNumberHelp(scanner);
                        break;
                    case 9:
                        displaySteganographyHelp(scanner);
                        break;
                    case 10:
                        displayAESHelp(scanner);
                        break;
                    case 0:
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

    private void displayVigenereHelp(Scanner scanner) {
        System.out.println("\n-------------------------------------");
        System.out.println("Vigenère Encryption Help:");
        System.out.println("The Vigenère cipher is a method of encrypting alphabetic text by using a simple form of polyalphabetic substitution.");
        System.out.println("Utilize a keyword to repeat and encrypt messages.");
        System.out.println("-------------------------------------");
        returnToHelpMenu(scanner);
    }

    private void displayPolybiusSquareHelp(Scanner scanner) {
        System.out.println("\n-------------------------------------");
        System.out.println("Polybius Square Help:");
        System.out.println("The Polybius square is a simple method used in cryptography for substitution encryption.");
        System.out.println("Each character is represented by two numbers forming a coordinate.");
        System.out.println("-------------------------------------");
        returnToHelpMenu(scanner);
    }

    private void displayRotXHelp(Scanner scanner) {
        System.out.println("\n-------------------------------------");
        System.out.println("ROT(X) Encryption Help:");
        System.out.println("ROT(X) is a simple letter substitution cipher that replaces a letter with another letter a fixed number of positions down the alphabet.");
        System.out.println("Configure X to rotate the letters accordingly.");
        System.out.println("-------------------------------------");
        returnToHelpMenu(scanner);
    }

    private void displayEnigmaHelp(Scanner scanner) {
        System.out.println("\n-------------------------------------");
        System.out.println("Enigma Machine Help:");
        System.out.println("The Enigma machine is a famous encryption device used in the early- to mid-20th century.");
        System.out.println("This simulation helps encrypt and decrypt messages through this historic method.");
        System.out.println("-------------------------------------");
        returnToHelpMenu(scanner);
    }

    private void displayRC4Help(Scanner scanner) {
        System.out.println("\n-------------------------------------");
        System.out.println("RC4 Algorithm Help:");
        System.out.println("RC4 is a stream cipher symmetric key algorithm known for its simplicity and speed.");
        System.out.println("It encrypts data by mixing it with a pseudo-random generated key.");
        System.out.println("-------------------------------------");
        returnToHelpMenu(scanner);
    }

    private void displayMD5Help(Scanner scanner) {
        System.out.println("\n-------------------------------------");
        System.out.println("MD5 Hashing Help:");
        System.out.println("MD5 is a widely-used cryptographic hash function generating a 128-bit (16-byte) hash value.");
        System.out.println("Primarily used to check data integrity, but not considered secure against intentional attacks.");
        System.out.println("-------------------------------------");
        returnToHelpMenu(scanner);
    }

    private void displaySHA256Help(Scanner scanner) {
        System.out.println("\n-------------------------------------");
        System.out.println("SHA-256 Hashing Help:");
        System.out.println("SHA-256 is a cryptographic hash function that generates a 256-bit (32-byte) signature for text.");
        System.out.println("It's widely used in security applications to verify data integrity.");
        System.out.println("-------------------------------------");
        returnToHelpMenu(scanner);
    }

    private void displayRandomNumberHelp(Scanner scanner) {
        System.out.println("\n-------------------------------------");
        System.out.println("Generate Pseudo Random Number Help:");
        System.out.println("This function generates pseudo-random numbers using algorithms that produce sequences of numbers which only approximate true randomness.");
        System.out.println("-------------------------------------");
        returnToHelpMenu(scanner);
    }

    private void displaySteganographyHelp(Scanner scanner) {
        System.out.println("\n-------------------------------------");
        System.out.println("Steganography Help:");
        System.out.println("Steganography is the practice of concealing messages within other non-secret text or data.");
        System.out.println("Learn how to embed and extract hidden data within files such as images or audio.");
        System.out.println("-------------------------------------");
        returnToHelpMenu(scanner);
    }

    private void displayAESHelp(Scanner scanner) {
        System.out.println("\n-------------------------------------");
        System.out.println("AES Encryption Help:");
        System.out.println("AES (Advanced Encryption Standard) is a modern algorithm used worldwide to secure data.");
        System.out.println("It uses symmetric keys and supports key lengths of 128, 192, or 256 bits.");
        System.out.println("-------------------------------------");
        returnToHelpMenu(scanner);
    }

    private void returnToHelpMenu(Scanner scanner) {
        System.out.println("\nPress Enter to return to the Help Menu...");
        scanner.nextLine();
    }
}