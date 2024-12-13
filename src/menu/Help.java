package menu;

import java.util.Scanner;

public class Help {
    public void display(Scanner scanner) {
        boolean backToMainMenu = false;

        while (!backToMainMenu) {
            System.out.println("\n=====================================");
            System.out.println("             HELP MENU              ");
            System.out.println("=====================================");
            System.out.println(" 1. Encryption");
            System.out.println(" 2. Decryption");
            System.out.println(" 3. ROT(X) Encryption");
            System.out.println(" 4. Vigenère Encryption");
            System.out.println(" 5. Polybius Square");
            System.out.println(" 0. <- Go to Main Menu");
            System.out.println("=====================================");
            System.out.print("Select a topic to learn more: ");
            
            String input = scanner.nextLine();
            int choice;

            try {
                choice = Integer.parseInt(input);

                switch (choice) {
                    case 1:
                        displayEncryptionHelp(scanner);
                        break;
                    case 2:
                        displayDecryptionHelp(scanner);
                        break;
                    case 3:
                        displayRotXHelp(scanner);
                        break;
                    case 4:
                        displayVigenereHelp(scanner);
                        break;
                    case 5:
                        displayPolybiusSquareHelp(scanner);
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

    private void displayEncryptionHelp(Scanner scanner) {
        System.out.println("\n-------------------------------------");
        System.out.println("Encryption Help:");
        System.out.println("Use this option to encrypt your messages with different algorithms.");
        System.out.println("-------------------------------------");
        returnToHelpMenu(scanner);
    }

    private void displayDecryptionHelp(Scanner scanner) {
        System.out.println("\n-------------------------------------");
        System.out.println("Decryption Help:");
        System.out.println("Use this option to decrypt your encrypted messages.");
        System.out.println("-------------------------------------");
        returnToHelpMenu(scanner);
    }

    private void displayRotXHelp(Scanner scanner) {
        System.out.println("\n-------------------------------------");
        System.out.println("ROT(X) Encryption Help:");
        System.out.println("Learn about using ROT(X) for character shifting encryption.");
        System.out.println("-------------------------------------");
        returnToHelpMenu(scanner);
    }

    private void displayVigenereHelp(Scanner scanner) {
        System.out.println("\n-------------------------------------");
        System.out.println("Vigenère Encryption Help:");
        System.out.println("Understand how to apply the Vigenère cipher with a keyword.");
        System.out.println("-------------------------------------");
        returnToHelpMenu(scanner);
    }

    private void displayPolybiusSquareHelp(Scanner scanner) {
        System.out.println("\n-------------------------------------");
        System.out.println("Polybius Square Help:");
        System.out.println("Find out how to encrypt with the Polybius square method.");
        System.out.println("-------------------------------------");
        returnToHelpMenu(scanner);
    }

    private void returnToHelpMenu(Scanner scanner) {
        System.out.println("\nPress Enter to return to the Help Menu...");
        scanner.nextLine();
    }
}