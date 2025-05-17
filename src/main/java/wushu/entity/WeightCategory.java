package wushu.entity;

public enum WeightCategory {
    UNDER_50("до 50 кг"),
    FROM_50_TO_55("50-55 кг"),
    FROM_55_TO_60("55-60 кг"),
    FROM_60_TO_65("60-65 кг"),
    FROM_65_TO_70("65-70 кг"),
    FROM_70_TO_75("70-75 кг"),
    FROM_75_TO_80("75-80 кг"),
    FROM_80_TO_85("80-85 кг"),
    FROM_85_TO_90("85-90 кг"),
    OVER_90("90+ кг"),
    ;

    private final String displayName;
    WeightCategory(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
