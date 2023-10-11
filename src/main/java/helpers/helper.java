package helpers;

import java.security.SecureRandom;
import java.util.Scanner;

public class helper {

    private static final SecureRandom random = new SecureRandom();

    public static int getInputAsInt(Scanner scanner) {
        int userInput = 0;

        boolean inputValid = false;
        while (!inputValid) {
            String input = scanner.nextLine();

            try {
                userInput = Integer.parseInt(input);
                inputValid = true; // La conversion en int a réussi, l'entrée est valide
            } catch (NumberFormatException e) {
                System.out.println("Entrée non valide. Veuillez entrer un nombre entier.");
            }
        }

        return userInput;
    }
    public static String generateClientCode(int length) {
        String allowedCharacters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

        // Create a StringBuilder to build the code
        StringBuilder codeBuilder = new StringBuilder(length);

        // Generate random characters for the code
        for (int i = 0; i < length; i++) {
            int randomIndex = random.nextInt(allowedCharacters.length());
            char randomChar = allowedCharacters.charAt(randomIndex);
            codeBuilder.append(randomChar);
        }

        return codeBuilder.toString();
    }

}
