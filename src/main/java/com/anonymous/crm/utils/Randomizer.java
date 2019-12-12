package com.anonymous.crm.utils;

import java.security.SecureRandom;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

public class Randomizer {

    private static final SecureRandom random = new SecureRandom();

    public static <T extends Enum<?>> T randomEnum(Class<T> clazz){
        int x = random.nextInt(clazz.getEnumConstants().length);
        return clazz.getEnumConstants()[x];
    }

    public static <T extends Enum<?>> Map<String, Integer> randomPercentage(Class<T> clazz) {
        Integer numberOfDraws = clazz.getEnumConstants().length;
        List<Integer> load = new ArrayList<>();
        Map<String, Integer> resultMap = new HashMap<>();
        //random numbers
        int sum = 0;
        for (int i = 0; i < numberOfDraws; i++) {
            int next = random.nextInt(100) + 1;
            load.add(next);
            sum += next;
        }

        //scale to the desired target sum
        double scale = 1d * 100 / sum;
        sum = 0;
        for (int i = 0; i < numberOfDraws; i++) {
            load.set(i, (int) (load.get(i) * scale));
            sum += load.get(i);
        }

        //take rounding issues into account
        while(sum++ < 100) {
            int i = random.nextInt(numberOfDraws);
            load.set(i, load.get(i) + 1);
        }

        for(int i = 0; i < clazz.getEnumConstants().length; i++) {
            resultMap.put(clazz.getEnumConstants()[i].name(), load.get(i));
        }

        return resultMap;
    }


    public static LocalDate generateRandomDate() {
        Integer year = 2019;
        Integer month = ThreadLocalRandom.current().nextInt(1,13);
        Integer day;
        switch (month){
            case 1:
            case 3:
            case 5:
            case 7:
            case 8:
            case 10:
            case 12:
                day = ThreadLocalRandom.current().nextInt(1,32);
                break;
            case 2:
                day = ThreadLocalRandom.current().nextInt(1,29);
                break;
            default:
                day = ThreadLocalRandom.current().nextInt(1,30);
        }
        return LocalDate.of(year, month, day);
    }

}
