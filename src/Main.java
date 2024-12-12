import java.security.NoSuchAlgorithmException;
import java.util.Scanner;
import menu.Menu;

public class Main {

    public static void main(String[] args) throws NoSuchAlgorithmException {
        Scanner scanner = new Scanner(System.in); // Create Scanner
        Menu menu = new Menu();
        menu.display(scanner); // Scanner to display method
        // scanner.close(); // Only close the scanner if finished with input
    }
}