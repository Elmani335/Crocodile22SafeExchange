// Menu.java
package menu;

import java.util.Scanner;
import encryption.VigenereCipher;

public class Menu {
    public void display() {
        Scanner scanner = new Scanner(System.in);
        boolean exit = false;

        while (!exit) {
            System.out.println("\n=====================================");
            System.out.println("             MAIN MENU              ");
            System.out.println("=====================================");
            System.out.println(" 1. Vigenère");
            System.out.println(" 2. Help");
            System.out.println(" 0. Exit");
            System.out.println("=====================================");
            System.out.print("Select an option: ");

            String input = scanner.nextLine();
            int choice;

            try {
                choice = Integer.parseInt(input);

                switch (choice) {
                    case 1:
                        displayVigenereMenu();
                        break;
                    case 2:
                        Help help = new Help();
                        help.display();
                        break;
                    case 0:
                        exit = true;
                        System.out.println("\nExiting the application. Goodbye!");
                        break;
                    default:
                        System.out.println("\nInvalid option, please choose a valid number.");
                }
            } catch (NumberFormatException e) {
                System.out.println("\nInvalid input, please enter a number.");
            }
        }
        scanner.close();
    }

    private void displayVigenereMenu() {
        Scanner scanner = new Scanner(System.in);
        boolean backToMainMenu = false;

        while (!backToMainMenu) {
            System.out.println("\n=====================================");
            System.out.println("           VIGENERE MENU            ");
            System.out.println("=====================================");
            System.out.println(" 1. Encrypt");
            System.out.println(" 2. Decrypt");
            System.out.println(" 0. <- Back to Main Menu");
            System.out.println("=====================================");
            System.out.print("Select an option: ");

            String input = scanner.nextLine();
            int choice;
            
            try {
                choice = Integer.parseInt(input);

                switch (choice) {
                    case 1:
                        performVigenereEncryption();
                        break;
                    case 2:
                        performVigenereDecryption();
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

    private void performVigenereEncryption() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("\nEnter the text to encrypt: ");
        String text = scanner.nextLine();
        System.out.print("Enter the encryption key: ");
        String key = scanner.nextLine();
        
        if (text.isEmpty() || key.isEmpty()) {
            System.out.println("Text and key cannot be empty.");
            return;
        }

        String encrypted = VigenereCipher.cipher(text, key);
        System.out.println("Encrypted Message: " + encrypted);
    }

    private void performVigenereDecryption() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("\nEnter the text to decrypt: ");
        String text = scanner.nextLine();
        System.out.print("Enter the decryption key: ");
        String key = scanner.nextLine();
        
        if (text.isEmpty() || key.isEmpty()) {
            System.out.println("Text and key cannot be empty.");
            return;
        }

        String decrypted = VigenereCipher.decrypt(text, key);
        System.out.println("Decrypted Message: " + decrypted);
    }
}