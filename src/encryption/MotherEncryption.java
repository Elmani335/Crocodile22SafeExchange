package encryption;

import java.io.File;
import java.util.Scanner;
import utils.FileHandler;


public abstract class MotherEncryption {

    // Type de chiffrement
    String type;

    /**
     * Encrypts a message according to a key
     * @param message : String, words to encrypt
     * @param key : String, key used to encrypt
     * 
     * @return String : Ciphered words
     */
    public abstract String encryption(String message, String key);

    /**
     * Decript the message according to a key
     * @param messageEncryption : String, message encrypted
     * @param key : String, the same key that was used for encryption
     */
    public abstract String decipher(String messageEncryption, String key);

    public void controlPoster(){
        
        Scanner scanner = new Scanner(System.in);
        boolean backToMainMenu = false;

        while (!backToMainMenu) {
            System.out.println("\n=====================================");
            System.out.println("           " + type +" MENU            ");
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
                        performEncryption();
                        break;
                    case 2:
                        performDecryption();
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

    private void performEncryption() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("\nEnter the text to encrypt: ");
        String text = scanner.nextLine();
        System.out.print("Enter the encryption key: ");
        String key = scanner.nextLine();
        
        if (text.isEmpty() || key.isEmpty()) {
            System.out.println("Text and key cannot be empty.");
            return;
        }

        String encrypted = encryption(text, key);
        System.out.println("Encrypted Message: " + encrypted);

        System.out.println("Voulez vous l'écrire dans un fichier");
        System.out.println(" 1. Oui");
        System.out.println(" 0. Non");

        String response = scanner.nextLine();
        int choice;
        boolean backToMainMenu = false;
        while (!backToMainMenu) {
            try {
                choice = Integer.parseInt(response);

                switch (choice) {
                    case 1:
                        FileHandler.WriteFile("./", "message", encrypted);
                        backToMainMenu = true;
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

    private void performDecryption() {

        Scanner scanner = new Scanner(System.in);
        int choice;
        boolean backToMainMenu = false;
        System.out.println("Voulez vous lire un fichier ?");
        System.out.println(" 1. Oui");
        System.out.println(" 0. Non");
        String response = scanner.nextLine();


        while (!backToMainMenu) {
            try {
                choice = Integer.parseInt(response);

                switch (choice) {
                    case 1:
                        System.out.print("Read...");
                        System.out.print("Enter the decryption key: ");
                        String key = scanner.nextLine();
                        String resultFile = FileHandler.ReadFile("./message.txt", this, key);
                        System.out.print(resultFile);
                        backToMainMenu = true;
                        break;
                    case 0:
                        System.out.print("\nEnter the text to decrypt: ");
                        String text = scanner.nextLine();
                        System.out.print("Enter the decryption key: ");
                        key = scanner.nextLine();
                        
                        if (text.isEmpty() || key.isEmpty()) {
                            System.out.println("Text and key cannot be empty.");
                            return;
                        }

                        String decrypted = decipher(text, key);
                        System.out.println("Decrypted Message: " + decrypted);
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

}
