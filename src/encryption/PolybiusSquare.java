package encryption;

import java.util.Objects;

public class PolybiusSquare {

    String[][] scare;

    public PolybiusSquare(){

        // Initializing Polybius' square
        this.scare = new String[][]{
                {"z",   "k",    "l",    "m",    "n"},
                {"y",   "i/j",  "b",    "c",    "o"},
                {"x",   "h",    "a",    "d",    "p"},
                {"w",   "g",    "f",    "e",    "q"},
                {"v",   "u",    "t",    "s",    "r"}
        };
    }

    /**
     * Ciphers a message by changing the letters into numbers according to the nearest square.
     * @param message : string, words to encrypt
     * @return string: Encrypted words
     */
    public String encryption(String message){

        System.out.println("Message à chiffrer avec le scare de polybe: "+ message);
        StringBuilder result = new StringBuilder();
        String tmpLetter;

        // Message letter browsing
        for (int i=0; i<message.length();i++){

            tmpLetter = String.valueOf(message.charAt(i));

            if (tmpLetter.equals(" ")){
                // Keep the space
                result.append(" ");
            }else {

                String[] tmpList;
                // Square course
                for (int y = 0; y < scare.length; y++) {
                    tmpList = scare[y];

                    // Browse square sub-lists
                    for (int z = 0; z < tmpList.length; z++) {

                        // If we find the letter in the square, we add the coordinates
                        if (tmpList[z].indexOf(tmpLetter) != -1) {
                            //System.out.println("Letter trouvé: " + tmpLetter + ", position: " + y + "," + z);
                            result.append(y).append(z);
                            break;
                        }
                    }
                }
            }

        }
        return result.toString();
    }

    /**
     * Deciphers a message thanks to the double-dimensional board. Can receive spaces 
     * @param messageEncryption : String, message encrypted by the same array
     * @return : String, message decrypted with spaces
     */
    public String decipher(String messageEncryption){

        System.out.println("Message à déchiffrer: "+messageEncryption);
        StringBuilder result = new StringBuilder();
        String[] partOfMessage = messageEncryption.split(" ");
        String x;
        String y;

        // Separate the message between each line break
        // We then go through the strings two by two to retrieve the x and y indexes
        // These will be used to retrieve a string from the double-dimensional array

        for (String travelNumber : partOfMessage){

            for (int i=0;i<travelNumber.length();i+=2){

                // Directly retrieving the variable travelNumber.charAt(i) as an int does not retrieve the correct value
                // To avoid a change when retrieving the char, we store the value in a string, which will be caster as an int

                x = String.valueOf(travelNumber.charAt(i));
                y = String.valueOf(travelNumber.charAt(i+1));
                //System.out.println("{"+x+" "+travelNumber.charAt(i)+"} : " + y);
                result.append(scare[Integer.valueOf(x)][Integer.valueOf(y)]);
            }

            result.append(" ");

        }
        // Delete the last excess space generated
        result.delete(result.length(), result.length());
        return result.toString();
    }

}
