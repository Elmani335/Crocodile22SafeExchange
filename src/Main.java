//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.

import utils.FileHandler;
import encryption.MotherEncryption;
import encryption.PolybiusSquare;

public class Main {
    public static void main(String[] args) {
       
        String fileName = "./test.txt";
               
        MotherEncryption polEncryption = new PolybiusSquare();
        String messageEncryption = polEncryption.encryption("iest eesti","");
        System.out.println(messageEncryption);

        String message = polEncryption.decipher(messageEncryption,"");
        System.out.println(message);
       

        //FileHandler.WriteFile("./", "test", messageEncryption);
        System.out.println(FileHandler.ReadFile(fileName,polEncryption,""));
    }
}