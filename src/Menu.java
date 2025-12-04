import java.util.InputMismatchException;
import java.util.Scanner;


public class Menu {

    public static void menu() {
        Scanner user_input = new Scanner(System.in);
        System.out.println("""
            
            
            ---------------------------------------------
            MAIN MENU
            ---------------------------------------------
            
            1. Encrypt a File
            2. Decrypt a File
            3. Quit the application
            """);
        try {
            System.out.print("Write a number (1-3): ");
            int choice = user_input.nextInt();

            if (choice == 1) {
                System.out.print(" - - - ");
            } else if (choice == 2) {
                System.out.print(" - - - ");
            }
        } catch (InputMismatchException e) {
            System.out.println("Invalid input. Please try again.");
            menu();
        }
    }
}
