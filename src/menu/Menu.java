package menu;

import java.util.Scanner;
import encryption.MotherEncryption;
import encryption.PolybiusSquare;
import encryption.VigenereCipher;
import encryption.RotXEncryption;
import java.util.Random;
import utils.LoadingAudio;

public class Menu {
    MotherEncryption polybius;
    MotherEncryption vigenere;
    MotherEncryption rotX;
    private Random rand;

    public Menu() {
        // Initialize encryptions
        polybius = new PolybiusSquare();
        vigenere = new VigenereCipher();
        rotX = new RotXEncryption();
        rand = new Random();
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
                    case 42: // Easter egg for 42
                        System.out.println("🤔 Ah, 42... 🚀 But sadly, you're not at École 42. You're at the Coding Factory!");
                        LoadingAudio.playAudio("src/audio/effect.wav");
                        break;
                    case 99: // Hidden mini-game
                        System.out.println("\n🎲 Welcome to the Dice Roll Game! 🎲");
                        int diceRoll = rand.nextInt(6) + 1;
                        System.out.println("You rolled a " + diceRoll + "!");
                        if (diceRoll == 6) {
                            System.out.println("🎉 Congratulations! You rolled a 6! You've won!");
                        }else {
                            System.out.println("Try again for a better roll.");
                        }  
                        break;
                    default:
                        System.out.println("\nInvalid option, please choose a valid number.");
                }
            } catch (NumberFormatException e) {
                // If the input is not a number, check for a secret string
                if (input.equalsIgnoreCase("konami")) {
                    System.out.println("\n🕹️ You've discovered the Konami code! 🕹️");
                    System.out.println("Special bonus unlocked!");
                } else {
                    System.out.println("\nInvalid input, please enter a number.");
                }
            }
        }
        scanner.close();
    }
}
