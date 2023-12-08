package finalVersionBitmexBot.model.util;

import java.util.Random;

public class RandomOrderSideGenerator {
    public static int generateRandomNumber() {
        Random random = new Random();
        // Генерация случайного числа в диапазоне [0, 1]
        return random.nextInt(2);
    }

    public static String generateRandomSide() {
        String side;
        if (generateRandomNumber() == 1) {
            side = "Buy";
        } else {
            side = "Sell";
        }
        return side;
    }
}
