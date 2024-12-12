package encryption;

public class PolybiusSquare extends MotherEncryption{
    
    // Initializing Polybius' square
    static String[][] scare = {    
        
                {"z",   "k",    "l",    "m",    "n"},
                {"y",   "i/j",  "b",    "c",    "o"},
                {"x",   "h",    "a",    "d",    "p"},
                {"w",   "g",    "f",    "e",    "q"},
                {"v",   "u",    "t",    "s",    "r"}
        };


    public PolybiusSquare(){
        this.type = "Polybius";
    }

    /**
     * Ciphers a message by changing the letters into numbers according to the nearest square.
     * @param message : String, words to encrypt
     * @param key : String, value null
     * @return String: Encrypted words
     */
    @Override
    public String encryption(String message, String key){

        System.out.println("Message to be encrypted with polybe scare: "+ message);
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
                            //System.out.println("Letter found: " + tmpLetter + ", position: " + y + "," + z);
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
     * @param key : String, value null
     * @return : String, message decrypted with spaces
     */
    @Override
    public String decipher(String messageEncryption, String key){

        System.out.println("Message to decipher: "+messageEncryption);
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

    @Override
    public Boolean checkInputUserKey(String key){
        return true;
    }

}
