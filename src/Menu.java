import java.io.IOException;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.Scanner;


public class Menu {

    private static final Scanner user_input = new Scanner(System.in);
    private static final String invalid_input_message = "Invalid input. Please try again.";
    private static final String write_number_message = "Write a number (1-3): ";

    public static void start_program() {
        while (true) {
            printMainMenu();
            try {
                System.out.print(write_number_message);
                int choice = user_input.nextInt();

                if (choice == 1) {
                    fileMenu(true);
                } else if (choice == 2) {
                    fileMenu(false);
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

    private static void fileMenu(boolean encrypt) {
        // clear leftover newline
        user_input.nextLine();

        // FILE NAME INPUT
        System.out.print("Enter file name to " + (encrypt ? "encrypt" : "decrypt") + " (format: filename.txt): ");
        String file_name = getFileName();

        // KEY INPUT FOR DECRYPT
        byte[] key = AESUtils.generate16ByteRandomKey();
        if (!encrypt) {
            System.out.print("Enter key (16 characters): ");
            key = getKeyString().getBytes();
        } else {
            System.out.print("Your key (Save to decryption the file!): " + Arrays.toString(key));
        }

        // PROCESS THE FILE
        process_file(file_name,key,encrypt);

        System.out.println("Press ENTER to return to main menu...");
        user_input.nextLine();
    }

    private static String getFileName() {
        String file_name = user_input.nextLine().trim();

        while (file_name.isEmpty()) {
            System.out.println("File name cannot be empty. Try again.");
            file_name = user_input.nextLine().trim();
        }

        if (!file_name.endsWith(".txt")) {
            file_name += ".txt";
        }

        return file_name;
    }

    private static String getKeyString() {
        System.out.print("Enter key (16 characters): ");
        String key_string = user_input.nextLine();
        while (key_string.length() != 16) {
            System.out.println("Key must be exactly 16 characters. Try again.");
            key_string = user_input.nextLine();
        }
        return key_string;
    }

    private static void process_file(String file_name, byte[] key, boolean encrypt) {
        try {
            String content = FileUtils.getFileContent(file_name);
            String result;
            if (encrypt) {
                result = AESUtils.encryptAES(content, key);
                FileUtils.writeFile("ciphertext.txt", result);
            } else {
                result = AESUtils.decryptAES(content, key);
                FileUtils.writeFile("plaintext.txt", result);
            }
            System.out.println("Your result will be saved here: " + (encrypt ? "ciphertext.txt" : "plaintext.txt"));
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
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
