import java.util.InputMismatchException;
import java.util.Scanner;


public class Menu {

    private static Scanner user_input = new Scanner(System.in);
    private static final String invalid_input_message = "Invalid input. Please try again.";
    private static final String write_number_message = "Write a number (1-3): ";

    public static void menu() {
        while (true) {
            printMainMenu();
            try {
                System.out.print(write_number_message);
                int choice = user_input.nextInt();

                if (choice == 1) {
                    // TODO
                } else if (choice == 2) {
                    // TODO
                } else if (choice == 3) {
                    System.out.println("Exiting...");
                    break;
                } else {
                    System.out.println(invalid_input_message);
                }

            } catch (InputMismatchException e) {
                System.out.println(invalid_input_message);
                user_input.nextLine();
            }
        }
    }

    private static void printMainMenu() {
        System.out.println("""
            
            
            ---------------------------------------------
            MAIN MENU
            ---------------------------------------------
            
            1. Encrypt a File
            2. Decrypt a File
            3. Quit the application
            """);
    }
}
