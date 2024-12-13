package menu;

import java.util.Scanner;
import random.PseudoRandomGenerator;
import steganography.Steganography;
import encryption.Enigma;
import encryption.MotherEncryption;
import encryption.PolybiusSquare;
import encryption.RC4Method;
import encryption.VigenereCipher;
import hashing.MD5Hash;
import hashing.MotherHash;
import hashing.SHA256Hash;
import encryption.RotXEncryption;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;
import java.util.Random;
import utils.LoadingAudio;
import java.io.IOException;



public class Menu {
    private MotherEncryption polybius;
    private MotherEncryption vigenere;
    private MotherEncryption rotX;
    private MotherEncryption enigma;
    private MotherHash md5;
    private MotherHash sha256;
    private Random rand;
    private MotherEncryption rc4;
    private Steganography steganography;
    

    public Menu() {
        polybius = new PolybiusSquare();
        vigenere = new VigenereCipher();
        rotX = new RotXEncryption();
        enigma = new Enigma();
        md5 = new MD5Hash();
        sha256 = new SHA256Hash();
        rand = new Random();
        rc4 = new RC4Method();
        steganography = new Steganography();

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
                        vigenere.controlPoster(scanner); 
                        break;
                    case 2:
                        polybius.controlPoster(scanner); 
                        break;
                    case 3:
                        rotX.controlPoster(scanner); 
                        break;
                    case 4:
                        enigma.controlPoster(scanner); 
                        break;
                    case 5:
                        rc4.controlPoster(scanner); 
                        break;
                    case 6:
                        md5.controlPoster();;
                        break;
                    case 7:
                        sha256.controlPoster();
                        break;
                    case 8:
                        handleRandomNumberGeneration(scanner);
                        break;
                    case 9:
                        handleSteganography(scanner);
                        break;
                    case 10:
                        Help help = new Help();
                        help.display(scanner); 
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
            } catch (IOException e) {
                System.err.println("Steganography error: " + e.getMessage());
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
        System.out.println(" 5. RC4");
        System.out.println(" 6. MD5 ");
        System.out.println(" 7. SHA256 ");
        System.out.println(" 8. Generate Pseudo Random Number");
        System.out.println(" 9. Steganography");
        System.out.println(" 10. Help");
        System.out.println(" 0. Exit");
        System.out.println("=====================================");
        System.out.print("Select an option: ");

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
private void handleSteganography(Scanner scanner) throws IOException {
    System.out.println("\nSteganography Options:");
    System.out.println("1. Hide a message in an image");
    System.out.println("2. Retrieve a hidden message from an image");
    System.out.print("Choose an option: ");
    int choice = Integer.parseInt(scanner.nextLine().trim());

    System.out.print("Enter the image file path: ");
    String imagePath = scanner.nextLine().trim();

    switch (choice) {
        case 1:
            System.out.print("Enter the output image file path: ");
            String outputImagePath = scanner.nextLine().trim();
            System.out.print("Enter the message to hide: ");
            String message = scanner.nextLine();
            steganography.hideMessage(imagePath, outputImagePath, message);
            System.out.println("Message successfully hidden in: " + outputImagePath);
            break;
        case 2:
            String extractedMessage = steganography.retrieveMessage(imagePath);
            System.out.println("Extracted message: " + extractedMessage);
            break;
        default:
            System.out.println("Invalid option for steganography.");
    }
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