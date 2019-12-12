package com.anonymous.crm.types;

public enum ContentType {

    COMEDY("Comedy"),
    VIDEOGAMES("Videogames"),
    FASHION("Fashion"),
    KIDS("Kids"),
    TRAVEL("Travel"),
    VLOG("Vlog"),
    FOOD("Food"),
    SPORTS("Sports"),
    NEWS("News"),
    DISSEMINATION("Dissemination");

    private String ageRange;

    ContentType(String ageRange){
        this.ageRange = ageRange;
    }

    public String getAgeRange() {
        return ageRange;
    }

    public static ContentType fromString(String text) {
        for (ContentType b : ContentType.values()) {
            if (b.ageRange.equalsIgnoreCase(text)) {
                return b;
            }
        }
        return null;
    }
}
