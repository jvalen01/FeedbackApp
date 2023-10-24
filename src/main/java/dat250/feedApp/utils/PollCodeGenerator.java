package dat250.feedApp.utils;

import java.util.Random;

public class PollCodeGenerator {

    private static final String ALPHA_NUMERIC_STRING = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final int CODE_LENGTH = 6;  // or any desired length

    public static String generateCode() {
        StringBuilder builder = new StringBuilder();
        Random rnd = new Random();
        for (int i = 0; i < CODE_LENGTH; i++) {
            int character = (int)(rnd.nextFloat() * ALPHA_NUMERIC_STRING.length());
            builder.append(ALPHA_NUMERIC_STRING.charAt(character));
        }
        return builder.toString();
    }
}

