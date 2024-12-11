// Menu.java
package menu;

import java.util.Scanner;

public class Menu {
    public void display() {
        Scanner scanner = new Scanner(System.in);
        boolean exit = false;

        while (!exit) {
            System.out.println("\n=====================================");
            System.out.println("             MAIN MENU              ");
            System.out.println("=====================================");
            System.out.println(" 1. Encryption");
            System.out.println(" 2. Decryption");
            System.out.println(" 3. Help");
            System.out.println(" 0. Exit");
            System.out.println("=====================================");
            System.out.print("Select an option: ");

            String input = scanner.nextLine();
            int choice;

            try {
                choice = Integer.parseInt(input);

                switch (choice) {
                    case 1:
                        System.out.println("\nYou have selected: Encryption");
                        break;
                    case 2:
                        System.out.println("\nYou have selected: Decryption");
                        break;
                    case 3:
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