package encryption;

import java.util.Objects;

public class PolybiusSquare {

    String[][] carre;

    public PolybiusSquare(){

        // Initializing Polybius' square
        this.carre = new String[][]{
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
    public StringBuilder chiffre(String message){

        System.out.println("Message à chiffrer avec le carre de polybe: "+ message);
        StringBuilder resultat = new StringBuilder();
        String tmpLettre;

        // Message letter browsing
        for (int i=0; i<message.length();i++){

            tmpLettre = String.valueOf(message.charAt(i));

            if (tmpLettre.equals(" ")){
                // Keep the space
                resultat.append(" ");
            }else {

                String[] tmpList;
                // Square course
                for (int y = 0; y < carre.length; y++) {
                    tmpList = carre[y];

                    // Browse square sub-lists
                    for (int z = 0; z < tmpList.length; z++) {

                        // If we find the letter in the square, we add the coordinates
                        if (Objects.equals(tmpList[z], tmpLettre)) {
                            //System.out.println("Lettre trouvé: " + tmpLettre + ", position: " + y + "," + z);
                            resultat.append(y).append(z);
                            break;
                        }
                    }
                }
            }

        }
        return resultat;
    }

}
