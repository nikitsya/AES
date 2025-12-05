import java.util.InputMismatchException;
import java.util.Scanner;


public class Menu {

    private static final Scanner user_input = new Scanner(System.in);

    public static void start_program() {
        String invalid_input_message = "Invalid input. Please try again.";
        String write_number_message = "Write a number (1-3): ";
        String exit_message = "Exiting...";

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
                    System.out.println(exit_message);
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

    private static void fileMenu(boolean encrypt) {
        String return_to_main_menu_message = "\nPress ENTER to return to main menu...";

        // clear leftover newline
        user_input.nextLine();

        String file_name = getFileNameFromUser(encrypt);

        byte[] key = encrypt ? AESUtils.generate16ByteRandomKey() : getKeyFromUser();

        process_file(file_name,key,encrypt);

        System.out.println(return_to_main_menu_message);
        user_input.nextLine();
    }

    private static String getFileNameFromUser(boolean encrypt) {
        String file_input_message = "\nEnter file name to " + (encrypt ? "encrypt" : "decrypt") + " (format: filename.txt): ";
        String error_message = "\nFile name cannot be empty. Try again.";
        String file_format = ".txt";

        System.out.print(file_input_message);
        String file_name = user_input.nextLine().trim();

        while (file_name.isEmpty()) {
            System.out.println(error_message);
            file_name = user_input.nextLine().trim();
        }

        if (!file_name.endsWith(file_format)) {
            file_name += file_format;
        }

        return file_name;
    }

    private static byte[] getKeyFromUser() {
        String key_prompt = "\bEnter key (16 characters): ";
        String error_message = "\bKey must be exactly 16 characters. Try again.";

        System.out.print(key_prompt);
        String key_string = user_input.nextLine().trim();

        while (key_string.length() != 16) {
            System.out.println(error_message);
            key_string = user_input.nextLine();
        }

        return key_string.getBytes();
    }

    private static void process_file(String file_name, byte[] key, boolean encrypt) {
        String plaintext_file_name = "plaintext.txt";
        String ciphertext_file_name = "ciphertext.txt";
        String key_file_name = "key.txt";

        String error_message = "\nError while processing file: ";
        String result_message = "\nYour key and result will be saved here: " + key_file_name + ", " +
                (encrypt ? ciphertext_file_name : plaintext_file_name);

        try {
            String content = FileUtils.getFileContent(file_name);
            String result;
            if (encrypt) {
                result = AESUtils.encryptAES(content, key);
                FileUtils.writeFile(ciphertext_file_name, result);
                FileUtils.writeFile(key_file_name, AESUtils.bytesToString(key));
            } else {
                result = AESUtils.decryptAES(content, key);
                FileUtils.writeFile(plaintext_file_name, result);
            }
            System.out.println(result_message);
        } catch (Exception e) {
            System.out.println(error_message + e.getMessage());
        }
    }
}
