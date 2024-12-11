package encryption;

import java.util.ArrayList;
import java.util.Scanner;

public class Enigma extends MotherEncryption {

    ArrayList<Rotor> listRotors ;
    RotXEncryption rotXEncryption;
    int[] firstToLast = {0,1,2};
    int[] lastToFirst = {2,1,0};

    public Enigma() {
        // Default rotor configurations (Enigma I type)
        Rotor rotor1 = new Rotor("abcdefghijklmnopqrstuvwxyz", 0);
        Rotor rotor2 = new Rotor("abcdefghijklmnopqrstuvwxyz", 0);
        Rotor rotor3 = new Rotor("abcdefghijklmnopqrstuvwxyz", 0);
        listRotors = new ArrayList<>();
        listRotors.add(rotor1);
        listRotors.add(rotor2);
        listRotors.add(rotor3);
        rotXEncryption = new RotXEncryption();
        this.type = "Enigma";
    }

    // Main method for encryption/decryption

    @Override
    public String encryption(String message, String keys){
        StringBuilder result = new StringBuilder();

        String tmpLetter = "";
        for (int i=0;i<message.length();i++){

            tmpLetter = String.valueOf(message.charAt(i));
            System.out.println("Change of letter: "+tmpLetter);

            // First run through the three Rotors*
            tmpLetter = goToRotor(firstToLast , tmpLetter);

            // Mirror
            int numberMirror = tmpLetter.charAt(0) - 96;
            System.out.println("Mirror letter");;
            System.out.print("letter: "+tmpLetter+" : " + numberMirror);
            
            // The further the letter is from the last letter (i.e. the lower its decimal value), the closer the subtraction with the last letter will be to the last letter.            // 26 - (x-1)
            //      26-x+1           x
            // [1           13           26]
            numberMirror = 26 - (numberMirror-1);
            // Decimal to letter conversion
            tmpLetter = String.valueOf((char)(numberMirror + 96));
            System.out.print("<- |13|-> "+(numberMirror-1)+ tmpLetter +"\n");

            // Reverses rotor direction
            tmpLetter = goToRotor(lastToFirst, tmpLetter);
            result.append(result);
            
        }
        System.out.println("Message encryption"+result);
        return result.toString();
    }

    /**
     * Passes the letter to the rotors according to a direction
     * @param runningOrder : int[], indices at which rotors will be retrieved
     * @param tmpLetter : String, letter to modify
     * @return : String, the letter to modify after the rotors have passed through
     */
    public String goToRotor(int[] runningOrder, String tmpLetter){
        int number;
        Rotor rotor;
        for (int i : runningOrder){
            rotor = listRotors.get(i);
            // Retrieve the decimal value (interval [1,26]) to then use the Rotation algorithm
            number = Integer.valueOf(rotor.getString()) - 96;
            System.out.print(rotor.getString()+" : "+number+" "+tmpLetter);
            tmpLetter = rotXEncryption.encryption(tmpLetter, String.valueOf(number));
            System.out.print(" -> "+tmpLetter+"\n");

        }
        return tmpLetter;
    }

    @Override
    public String decipher(String message, String key){
        return "";
    }

    @Override
    public void controlPoster(){
        Scanner scanner = new Scanner(System.in);
        boolean userFinish = false;
        while (!userFinish) {
            System.out.println("\n--- Enigma ---");
            System.out.print("1. Configure rotor positions");
            for (Rotor rotor : listRotors){
                System.out.print("|"+rotor.getString());
            }
            System.out.print("|\n");
            System.out.println("2. Encrypt a message");
            System.out.println("3. Decrypt a message");
            System.out.println("0. <- Back to Main Menu");
            System.out.print("Choose an option: ");

            int choice = -1;

            // Error handling for option selection
            try {
                choice = Integer.parseInt(scanner.nextLine());
                if (choice < 1 || choice > 4) {
                    throw new IllegalArgumentException("Invalid choice, please enter a number between 1 and 4.");
                }
            } catch (IllegalArgumentException e) {
                System.out.println("Error: " + e.getMessage());
                continue; // Go back to the menu if the choice is invalid
            }

            switch (choice) {
                case 1:
                    
                    // Change la position de tout les rotors
                    boolean isNumber;
                    for (int i=0;i<listRotors.size();i++){

                        isNumber = false;
                        while (!isNumber) {

                            System.out.println("Changer le rotor n°"+i+", seul les nombres sont accepté");
                            String number = scanner.nextLine();
                            
                            // Si l'utilisateur rentre bien un nombre
                            if (number.matches("-?\\d+")){

                                isNumber = true;
                                listRotors.get(i).setPosition(Integer.parseInt(number));

                            }else{
                                System.out.println("Veuillez entrer un nombre");
                            }
                        }

                    }

                    break;

                case 2:
                    System.out.print("Enter the message to encrypt: ");
                    String messageToEncrypt = scanner.nextLine();
                    encryption(messageToEncrypt, "");
                    // Ensure the message contains only letters
                    if (!messageToEncrypt.matches("[A-Za-z]+")) {
                        System.out.println("Error: The message should only contain letters.");
                        break;
                    }

                    break;

                case 3:
                    System.out.print("Enter the message to decrypt: ");
                    String messageToDecode = scanner.nextLine();

                    // Ensure the message contains only letters
                    if (!messageToDecode.matches("[A-Za-z]+")) {
                        System.out.println("Error: The message should only contain letters.");
                        break;
                    }

                    break;

                case 0:
                    userFinish = true;
                    break;

                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }
    
}

class Rotor {
    String wiring;
    int currentPosition;

    public Rotor(String wiring, int notchPosition) {
        this.wiring = wiring;
        this.currentPosition = 0;
    }

    public void setPosition(int x){
        currentPosition = (currentPosition + x) % 26;
    }

    public char getString(){
        return wiring.charAt(currentPosition);
    }
}