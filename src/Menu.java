import java.util.InputMismatchException;
import java.util.Scanner;


public class Menu {

    private static final Scanner user_input = new Scanner(System.in);

    public static void start_program() {
        String write_number_message = "Write a number (1-3): ";
        String invalid_input_message = "\nInvalid input. Please try again.";
        String exit_message = "\nExiting...";

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
                skipLine();
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
        skipLine();

        String file_name = getFileNameFromUser();
        byte[] key = encrypt ? AESUtils.generate16ByteRandomKey() : getKeyFromUser();

        process_file(file_name,key,encrypt);

        System.out.println(return_to_main_menu_message);
        skipLine();
    }

    private static String getFileNameFromUser() {
        String file_input_message = "\nEnter file name (format: filename.txt): ";
        String empty_file_name_message = """
            
            ---------------------------------------------
            File name cannot be empty. Try again.
            ---------------------------------------------""";
        String file_not_found_message = """
            
            ---------------------------------------------
            File does not exist. Try again.
            ---------------------------------------------""";
        String file_format = ".txt";

        String file_name;

        while (true) {
            System.out.print(file_input_message);
            file_name = user_input.nextLine().trim();

            // check empty
            if (file_name.isEmpty()) {
                System.out.println(empty_file_name_message);
                continue;
            }

            // add .txt if missing
            if (!file_name.endsWith(file_format)) {
                file_name += file_format;
            }

            // check existence
            if (!FileUtils.checkFileExistence(file_name)) {
                System.out.println(file_not_found_message);
                continue;
            }

            // everything OK
            return file_name;
        }
    }

    private static byte[] getKeyFromUser() {
        String key_prompt = "\nEnter key (16 characters): ";
        String error_message = """
                
                ---------------------------------------------
                Key must be exactly 16 characters. Try again.
                ---------------------------------------------""";

        System.out.print(key_prompt);
        String key_string = user_input.nextLine().trim();

        while (key_string.length() != 16) {
            System.out.println(error_message);
            System.out.print(key_prompt);
            key_string = user_input.nextLine().trim();
        }

        return key_string.getBytes();
    }

    private static void process_file(String file_name, byte[] key, boolean encrypt) {
        String plaintext_file_name = "decrypted_text.txt";
        String ciphertext_file_name = "encrypted_text.txt";
        String key_file_name = "key.txt";

        String error_message = "\nError while processing file: ";
        String result_message = "\nYour result will be saved here: " + (encrypt ? ciphertext_file_name : plaintext_file_name);
        String key_store_message = "\nSee your key in the " + key_file_name;

        try {
            while (true) {
                String content = FileUtils.getFileContent(file_name);
                String file_name_to_write = encrypt ? ciphertext_file_name : plaintext_file_name;
                String content_to_write;
                try {
                    content_to_write = encrypt ? AESUtils.encryptAES(content, key) : AESUtils.decryptAES(content, key);
                } catch (Exception e) {
                    continue;
                }
                FileUtils.writeFile(file_name_to_write, content_to_write);
                if (encrypt) {
                    FileUtils.writeFile(key_file_name, AESUtils.bytesToString(key));
                    result_message += key_store_message;
                }
                System.out.println(result_message);
                break;
            }
        } catch (Exception e) {
            System.out.println(error_message + e.getMessage());
        }
    }

    private static void skipLine() {
        user_input.nextLine();
    }

}
