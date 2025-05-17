package wushu.entity;

public enum ContactAgeCategory {
    AGE_6_7("6-7років"),
    AGE_8_9("8-9років"),
    AGE_10_11("10-11років"),
    AGE_12_13("12-13років"),
    AGE_14_15("14-15років"),
    AGE_16_17("16-17років"),
    AGE_18_PLUS("18 років і старше");


    private final String displayName;

    ContactAgeCategory(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
