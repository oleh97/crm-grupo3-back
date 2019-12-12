package com.anonymous.crm.types;

public enum VisitRange {

    LOW1("0-1K"),
    LOW2("1K-5K"),
    LOW3("5K-10K"),
    MID1("10K-20K"),
    MID2("20K-50K"),
    MID3("50K-100K"),
    HIGH1("100K-200K"),
    HIGH2("200K-500K"),
    HIGH3("500K-1M"),
    SUPER("1M+");

    private String visitRange;

    VisitRange(String visitRange) {
        this.visitRange = visitRange;
    }

    public String getVisitRange() {
        return visitRange;
    }

    public static VisitRange fromString(String text) {
        for (VisitRange b : VisitRange.values()) {
            if (b.visitRange.equalsIgnoreCase(text)) {
                return b;
            }
        }
        return null;
    }
}
