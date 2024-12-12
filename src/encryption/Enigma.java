package encryption;

public class Enigma extends MotherEncryption {

    String[] listRotors = {"a","a","a"};
    RotXEncryption rotXEncryption;
    int[] firstToLast = {0,1,2};
    int[] lastToFirst = {2,1,0};

    public Enigma() {
        rotXEncryption = new RotXEncryption();
        this.type = "Enigma";
    }

    @Override
    public String encryption(String message, String keys){
        return encryptionOrDecipher(message, keys,true);
    }

    @Override
    public String decipher(String message, String keys){
        return encryptionOrDecipher(message, keys,false);
    }

    /**
     * Check that the key has the same number of letters as rotors.    
    */
    @Override
    public Boolean checkInputUserKey(String key){
        if (key.length() != listRotors.length){
            System.out.println("Error: you need "+ listRotors.length+ " letters");
            return false;
        }
        return !key.isEmpty() && key.matches("[a-z]+");
    }

    /**
     * Main method for encryption/decryption
     * @param message : String, contenu à chiffrer ou à déchiffrer
     * @param keys : String, clefs de chiffrement ou de déchiffrement
     * @param isEncryption : Boolean, si c'est un chiffrement (true) ou un déchiffrement (false)
     * @return
     */
    public String encryptionOrDecipher(String message, String keys ,boolean isEncryption){


        System.out.print("Rotors positions: ");
        for (int i =0; i<keys.length();i++){
            listRotors[i] = String.valueOf(keys.charAt(i));
            System.out.print("|"+listRotors[i]);
        }
        System.out.println();

        StringBuilder result = new StringBuilder();

        String tmpLetter = "";

        // Si on souhaite chiffrer, l'ordre de passage dans les rotors est du premier
        // A l'inverse pour déchiffrer, du dernier au premier
        int[] tpmFirstoLast = isEncryption? firstToLast : lastToFirst;
        int[] tpmLastToFirst = isEncryption? lastToFirst : firstToLast;

        for (int i=0;i<message.length();i++){

            tmpLetter = String.valueOf(message.charAt(i));
            // System.out.println("Change of letter: "+tmpLetter);

            // First run through the three Rotors*
            tmpLetter = goToRotor(tpmFirstoLast , tmpLetter);

            // Mirror
            int numberMirror = tmpLetter.charAt(0) - 96;
            // System.out.println("Mirror letter");;
            // System.out.print("letter: "+tmpLetter+" : " + numberMirror);
            
            // The further the letter is from the last letter (i.e. the lower its decimal value), the closer the subtraction with the last letter will be to the last letter.            // 26 - (x-1)
            //      26-x+1           x
            // [1           13           26]
            numberMirror = 26 - (numberMirror-1);
            // Decimal to letter conversion
            tmpLetter = String.valueOf((char)(numberMirror + 96));
            // System.out.print("<- |13|-> "+(numberMirror-1)+ tmpLetter +"\n");

            // Reverses rotor direction
            tmpLetter = goToRotor(tpmLastToFirst, tmpLetter);
            result.append(tmpLetter);
            
        }
        // String tmpType = isEncryption ? "Encryption" : "Decipher";
        // System.out.println("Message "+tmpType +" : "+ result);
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
        char rotor;
        for (int i : runningOrder){
            rotor = listRotors[i].charAt(0);
            // Retrieve the decimal value (interval [1,26]) to then use the Rotation algorithm
            number = Integer.valueOf(rotor) - 96;
            // System.out.print(rotor.getString()+" : "+number+" "+tmpLetter);
            tmpLetter = rotXEncryption.encryption(tmpLetter, String.valueOf(number));
            // System.out.print(" -> "+tmpLetter+"\n");

        }
        return tmpLetter;
    }

}