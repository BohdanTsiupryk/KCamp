package bts.KCamps.util;

import java.util.Random;

public class RandomGenerator {

    public static String randomOrderId() {
        String source = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz1234567890";
        int size = 254;
        char[] text = new char[size];
        for (int i = 0; i < size; i++) {
            text[i] = source.charAt(new Random().nextInt(source.length()));
        }
        return new String(text);
    }
}
