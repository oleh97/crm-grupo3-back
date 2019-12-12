package com.anonymous.crm.types;

public enum AgeRange {
    A("5-10"),
    B("10-12"),
    C("12-15"),
    D("15-18"),
    E("18-22"),
    F("22-30"),
    G("30-50"),
    H("50+");

    private String ageRange;

    AgeRange(String ageRange) {
        this.ageRange = ageRange;
    }

    public String getAgeRange() {
        return this.ageRange;
    }


    public static AgeRange fromString(String text) {
        for (AgeRange b : AgeRange.values()) {
            if (b.ageRange.equalsIgnoreCase(text)) {
                return b;
            }
        }
        return null;
    }
}
