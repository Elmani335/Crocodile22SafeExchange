package encryption;

public class PolybiusSquare extends MotherEncryption {

    // Initialize Polybius' square
    static String[][] scare = {
        {"z", "k", "l", "m", "n"},
        {"y", "i/j", "b", "c", "o"},
        {"x", "h", "a", "d", "p"},
        {"w", "g", "f", "e", "q"},
        {"v", "u", "t", "s", "r"}
    };

    public PolybiusSquare() {
        this.type = "Polybius";
    }

    /**
     * Ciphers a message by changing the letters into numbers according to the nearest square.
     * @param message : String, words to encrypt
     * @param key : String, value null
     * @return String: Encrypted words
     */
    @Override
    public String encryption(String message, String key) {
        System.out.println("Message to be encrypted with polybe scare: " + message);
        StringBuilder result = new StringBuilder();
        String tmpLetter;

        // Message letter browsing
        for (int i = 0; i < message.length(); i++) {
            tmpLetter = String.valueOf(message.charAt(i));

            if (tmpLetter.equals(" ")) {
                result.append(" ");
            } else {
                boolean found = false; // Flag to exit loops once found
                for (int y = 0; y < scare.length && !found; y++) {
                    for (int z = 0; z < scare[y].length; z++) {
                        if (scare[y][z].indexOf(tmpLetter) != -1) {
                            result.append(y).append(z);
                            found = true; // Exit loops once the letter is found
                            break;
                        }
                    }
                }
            }
        }
        return result.toString();
    }

    /**
     * Deciphers a message thanks to the double-dimensional board. Can receive spaces.
     * @param messageEncryption : String, message encrypted by the same array
     * @param key : String, value null
     * @return : String, message decrypted with spaces
     */
    @Override
    public String decipher(String messageEncryption, String key) {
        System.out.println("Message to decipher: " + messageEncryption);
        StringBuilder result = new StringBuilder();
        String[] partOfMessage = messageEncryption.split(" ");

        for (String travelNumber : partOfMessage) {
            if (travelNumber.length() % 2 != 0) {
                System.out.println("Warning: Incorrect length, ignoring last character.");
                travelNumber = travelNumber.substring(0, travelNumber.length() - 1); // Trim the last character
            }
            for (int i = 0; i < travelNumber.length(); i += 2) {
                try {
                    int x = Integer.parseInt(String.valueOf(travelNumber.charAt(i)));
                    int y = Integer.parseInt(String.valueOf(travelNumber.charAt(i + 1)));
                    result.append(scare[x][y]);
                } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
                    System.out.println("Decoding error at indices " + i + " and " + (i + 1));
                }
            }
            result.append(" ");
        }
        if (result.length() > 0) result.setLength(result.length() - 1); 
        return result.toString();
    }

    @Override
    public Boolean checkInputUserKey(String key) {
        return true;
    }
}