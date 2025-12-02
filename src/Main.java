import java.util.InputMismatchException;
import java.util.Scanner;


public class Main {

    public static void main(String[] args) {
        menu();
    }

    public static void menu() {
        Scanner user_input = new Scanner(System.in);

        System.out.println("""
            
            
            ---------------------------------------------
            MAIN MENU
            ---------------------------------------------
            
            1. Encrypt a File (Task 2)
            2. Decrypt a File (Task 3)
            3. Quit the application
            """);

        try {
            System.out.print("Write a number (1-3): ");
            int choice = user_input.nextInt();

            if (choice == 1) {
                System.out.print("Write a string: ");
                int plain_text = user_input.nextInt(); user_input.nextLine();
                System.out.print("Write a key: ");
                int key = user_input.nextInt(); user_input.nextLine();
                user_input.nextLine();
            } else if (choice == 2) {
                System.out.print(" - - - ");
            }
        } catch (InputMismatchException e) {
            System.out.println("Invalid input. Please try again.");
            menu();
        }

    }
}
