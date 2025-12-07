import java.util.Scanner;


/**
 * Menu class manages the console-based interaction for encrypting and decrypting files.
 * It handles user input, validates menu selections, and delegates actions to utility classes.
 */
public class Menu {

    /** Global scanner for reading user input from console. */
    private static final Scanner user_input = new Scanner(System.in);

    /**
     * Entry point for the console menu.
     * Loops until user chooses to exit and validates menu input.
     */
    public static void start_program() {
        String write_number_message = "Write a number (1-3): ";
        String invalid_input_message = "\nInvalid input. Please try again.";
        String exit_message = "\nExiting...";

        while (true) {
            printMainMenu();
            try {
                System.out.print(write_number_message);

                // Read entire line to avoid issues with leftover newline characters
                String line = user_input.nextLine().trim();
                int choice = Integer.parseInt(line);

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

            } catch (NumberFormatException e) {
                // Handles cases where input is not a number (e.g., letters, empty input)
                System.out.println(invalid_input_message);
            }
        }
    }

    /**
     * Prints the main menu layout.
     */
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

    /**
     * Handles both encryption and decryption flow.
     *
     * @param encrypt true to encrypt a file, false to decrypt
     */
    private static void fileMenu(boolean encrypt) {
        String file_name = getFileNameFromUser();

        if (file_name == null) {
            // user pressed 0
            return;
        }

        // Key is generated only for encryption; user enters key for decryption
        byte[] key = encrypt ? AESUtils.generate16ByteRandomKey() : getKeyFromUser();

        if (key == null) {
            // also exit to main menu if user typed 0 in key input
            return;
        }

        process_file(file_name,key,encrypt);
    }

    /**
     * Prompts the user to enter a file name, validates format and existence.
     *
     * @return valid file name ending with ".txt"
     */
    private static String getFileNameFromUser() {
        String file_input_message = "\nEnter file name (or '0' to return to Main Menu): ";
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

            // Return to main menu
            if (isExitToMainMenu(file_name)) {
                return null;
            }

            // Reject empty input
            if (file_name.isEmpty()) {
                System.out.println(empty_file_name_message);
                continue;
            }

            // Automatically append .txt if user forgets it
            if (!file_name.endsWith(file_format)) {
                file_name += file_format;
            }

            // Validate existence
            if (!FileUtils.checkFileExistence(file_name)) {
                System.out.println(file_not_found_message);
                continue;
            }

            // everything OK
            return file_name;
        }
    }

    /**
     * Reads a Base64-encoded AES key from user input.
     *
     * @return decoded 16-byte AES key, or null if invalid
     */
    private static byte[] getKeyFromUser() {
        String key_prompt = "\nEnter key (or '0' to return to Main Menu): ";

        System.out.print(key_prompt);
        String key_string = user_input.nextLine().trim();

        return AESUtils.decodeBase64Key(key_string);
    }

    /**
     * Handles file encryption or decryption, including reading and writing the result.
     *
     * @param file_name name of the input file
     * @param key AES key to use for encryption/decryption
     * @param encrypt true for encryption, false for decryption
     */
    private static void process_file(String file_name, byte[] key, boolean encrypt) {

        // If key is invalid, silently exit — error message handled elsewhere
        if (key == null) {
            System.out.println("Invalid Base64 key format.");
            return;
        }

        String plaintext_file_name = "plaintext.txt";
        String ciphertext_file_name = "ciphertext.txt";

        String key_message = "\nYour key (save to decrypt the file!): ";
        String result_message = "Your result will be saved here: " + (encrypt ? ciphertext_file_name : plaintext_file_name);

        try {
            // Read file content
            String content = FileUtils.getFileContent(file_name);

            // Determine output file and apply encryption/decryption
            String file_name_to_write = encrypt ? ciphertext_file_name : plaintext_file_name;
            String content_to_write = encrypt ? AESUtils.encryptAES(content, key) : AESUtils.decryptAES(content, key);

            // Write processed file
            FileUtils.writeFile(file_name_to_write, content_to_write);

            // Print key only when generating a new one during encryption
            if (encrypt) {
                System.out.println(key_message + AESUtils.encodeKeyToBase64(key));
            }

            System.out.println(result_message);
        } catch (Exception ignored) {
            // Error suppressed intentionally to prevent duplicate message output
        }
    }

    private static boolean isExitToMainMenu(String input) {
        return input.equals("0");
    }
}
