package menu;

import java.util.Scanner;
import random.PseudoRandomGenerator;
import encryption.Enigma;
import encryption.MotherEncryption;
import encryption.PolybiusSquare;
import encryption.VigenereCipher;
import hashing.MD5Hash;
import hashing.MotherHash;
import hashing.SHA256Hash;
import encryption.RotXEncryption;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;
import java.util.Random;
import utils.LoadingAudio;

public class Menu {
    private MotherEncryption polybius;
    private MotherEncryption vigenere;
    private MotherEncryption rotX;
    private MotherEncryption enigma;
    private MotherHash md5;
    private MotherHash sha256;
    private Random rand;

    public Menu() {
        polybius = new PolybiusSquare();
        vigenere = new VigenereCipher();
        rotX = new RotXEncryption();
        enigma = new Enigma();
        md5 = new MD5Hash();
        sha256 = new SHA256Hash();
        rand = new Random();
    }

    public void display(Scanner scanner) throws NoSuchAlgorithmException {
        boolean exit = false;

        while (!exit) {
            displayMainMenu();
            String input = scanner.nextLine().trim();
            int choice;

            try {
                choice = Integer.parseInt(input);

                switch (choice) {
                    case 1:
                        vigenere.controlPoster(scanner); // Pass scanner here
                        break;
                    case 2:
                        polybius.controlPoster(scanner); // Pass scanner here
                        break;
                    case 3:
                        rotX.controlPoster(scanner); // Pass scanner here
                        break;
                    case 4:
                        enigma.controlPoster(scanner); // Pass scanner here
                        break;
                    case 5:
                        handleHashing(md5); // Add a method to handle hashing without Scanner
                        break;
                    case 6:
                        handleHashing(sha256); // Add a method to handle hashing without Scanner
                        break;
                    case 7:
                        handleRandomNumberGeneration(scanner);
                        break;
                    case 8:
                        Help help = new Help();
                        help.display(scanner); // Pass the scanner to Help
                        break;
                    case 0:
                        exit = true;
                        System.out.println("\nExiting the application. Goodbye!");
                        break;
                    case 42:
                        handleEasterEgg();
                        break;
                    case 99:
                        playDiceGame();
                        break;
                    default:
                        System.out.println("\nInvalid option, please choose a valid number from the list 2.");
                }
            } catch (NumberFormatException e) {
                System.out.println("\nInvalid input, please enter a number.");
            }
        }
    }


    private void displayMainMenu() {
        System.out.println("\n=====================================");
        System.out.println("             MAIN MENU              ");
        System.out.println("=====================================");
        System.out.println(" 1. Vigenère");
        System.out.println(" 2. Polybius");
        System.out.println(" 3. ROT(X)");
        System.out.println(" 4. Enigma");
        System.out.println(" 5. MD5 ");
        System.out.println(" 6. SHA256 ");
        System.out.println(" 7. Generate Pseudo Random Number");
        System.out.println(" 8. Help");
        System.out.println(" 0. Exit");
        System.out.println("=====================================");
        System.out.print("Select an option: ");

    }


    private void handleHashing(MotherHash hash) {
        System.out.println("Hashing functionality is not implemented yet.");
    }

    private void handleRandomNumberGeneration(Scanner scanner) {
        System.out.print("Enter the seed for LFSR (UTF-8 characters): ");
        String seed = scanner.nextLine().trim();

        if (seed.isEmpty()) {
            seed = String.valueOf(Instant.now().toEpochMilli());
            System.out.println("No seed provided, using system time as seed: " + seed);
        }

        PseudoRandomGenerator prg = new PseudoRandomGenerator(seed);
        prg.runLFSR(); // This runs the LFSR and prints the generated numbers
    }

    private void handleEasterEgg() {
        System.out.println("🤔 Ah, 42... 🚀 But sadly, you're not at École 42. You're at the Coding Factory!");
        LoadingAudio.playAudio("src/audio/effect.wav");
    }

    private void playDiceGame() {
        System.out.println("\n🎲 Welcome to the Dice Roll Game! 🎲");
        int diceRoll = rand.nextInt(6) + 1;
        System.out.println("You rolled a " + diceRoll + "!");
        if (diceRoll == 6) {
            System.out.println("🎉 Congratulations! You rolled a 6! You've won!");
        } else {
            System.out.println("Try again for a better roll.");
        }
    }
}