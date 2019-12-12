package com.anonymous.crm.types;

public enum GeographicZone {

    SPAIN("Spain"),
    FRANCE("France"),
    ANDORRA("Andorra"),
    UK("United Kingdom"),
    USA("United States of America"),
    SOUTH_AMERICA("South America"),
    CHINA("China"),
    JAPAN("Japan");

    private String country;

    GeographicZone(String country) {
        this.country = country;
    }

    public String getCountry() {
        return country;
    }

    public static GeographicZone fromString(String text) {
        for (GeographicZone b : GeographicZone.values()) {
            if (b.country.equalsIgnoreCase(text)) {
                return b;
            }
        }
        return null;
    }
}
