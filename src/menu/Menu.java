// Menu.java
package menu;

import java.util.Scanner;
import encryption.MotherEncryption;
import encryption.PolybiusSquare;
import encryption.VigenereCipher;
import encryption.RotXEncryption;
// import encryption.Help;

public class Menu {
    MotherEncryption polybius;
    MotherEncryption vigenere;
    MotherEncryption rotX;

    public Menu() {
        // Initialize encryptions
        polybius = new PolybiusSquare();
        vigenere = new VigenereCipher();
        rotX = new RotXEncryption();
    }

    public void display() {
        Scanner scanner = new Scanner(System.in);
        boolean exit = false;

        while (!exit) {
            System.out.println("\n=====================================");
            System.out.println("             MAIN MENU              ");
            System.out.println("=====================================");
            System.out.println(" 1. Vigenère");
            System.out.println(" 2. Polybius");
            System.out.println(" 3. ROT(X)");
            System.out.println(" 4. Help");
            System.out.println(" 0. Exit");
            System.out.println("=====================================");
            System.out.print("Select an option: ");

            String input = scanner.nextLine();
            int choice;

            try {
                choice = Integer.parseInt(input);

                switch (choice) {
                    case 1:
                        vigenere.controlPoster();
                        break;
                    case 2:
                        polybius.controlPoster();
                        break;
                    case 3:
                        rotX.controlPoster();
                        break;
                    case 4:
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
}