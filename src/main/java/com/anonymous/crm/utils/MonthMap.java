package com.anonymous.crm.utils;

import java.util.HashMap;
import java.util.Map;

public class MonthMap {

    public static Map<Integer, Double> getMonthMap(Integer monthDays) {
        Map<Integer, Double> monthMonetization = new HashMap<>();
        for (int i = 1; i <= monthDays; i++) {
            monthMonetization.put(i, 0.0);
        }
        return monthMonetization;
    }

    public static Map<Integer, Double> getMonthsMap() {
        Map<Integer, Double> monthsMap = new HashMap<>();
        for (int i = 1; i <= 12; i++) {
            monthsMap.put(i, 0.0);
        }
        return monthsMap;
    }

    public static Integer monthMaxDay(Integer month) {
        switch (month){
            case 1:
            case 3:
            case 5:
            case 7:
            case 8:
            case 10:
            case 12:
                return 31;
            case 2:
                return 28;
            default:
                return 30;
        }
    }

}
