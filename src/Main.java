//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.

import encryption.PolybiusSquare;

public class Main {
    public static void main(String[] args) {
       PolybiusSquare polybiusSquare = new PolybiusSquare();
       
       String messageEncryption = polybiusSquare.encryption("iest eesti");
       System.out.println(messageEncryption);

       String message = polybiusSquare.decipher(messageEncryption);
       System.out.println(message);
    }
}