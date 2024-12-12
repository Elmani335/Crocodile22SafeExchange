package menu;

import java.util.Scanner;

import encryption.Enigma;
import encryption.MotherEncryption;
import encryption.PolybiusSquare;
import encryption.VigenereCipher;
import hashing.MD5Hash;
import hashing.MotherHash;
import hashing.SHA256Hash;
import encryption.RotXEncryption;

import java.security.NoSuchAlgorithmException;
import java.util.Random;
import utils.LoadingAudio;

public class Menu {
    MotherEncryption polybius;
    MotherEncryption vigenere;
    MotherEncryption rotX;
    private Random rand;
    MotherEncryption enigma;
    MotherHash md5;
    MotherHash sha256;

    public Menu() {
        // Initialize encryptions
        polybius = new PolybiusSquare();
        vigenere = new VigenereCipher();
        rotX = new RotXEncryption();
        rand = new Random();
        enigma = new Enigma();
        md5 = new MD5Hash();
        sha256 = new SHA256Hash();
    }

    public void display() throws NoSuchAlgorithmException {
        Scanner scanner = new Scanner(System.in);
        boolean exit = false;

        while (!exit) {
            System.out.println("\n=====================================");
            System.out.println("             MAIN MENU              ");
            System.out.println("=====================================");
            System.out.println(" 1. Vigenère");
            System.out.println(" 2. Polybius");
            System.out.println(" 3. ROT(X)");
            System.out.println(" 4. Enigma");
            System.out.println(" 5. MD5 ");
            System.out.println(" 6. SHA256 ");
            System.out.println(" 7. Help");
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
                        enigma.controlPoster();
                        break;
                    case 5:
                        md5.controlPoster();
                        break;
                    case 6:
                        sha256.controlPoster();
                        break;
                    case 7:
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
                System.out.println("\nInvalid input, please enter a number.");
            }
        }
        scanner.close();
    }
}
