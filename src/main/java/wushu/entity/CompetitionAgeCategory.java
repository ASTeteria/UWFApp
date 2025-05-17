package wushu.entity;

public enum CompetitionAgeCategory {
    YOUNGER_JUNIORS_6_8("Молодші юнаки: 6-8років"),
    OLDER_JUNIORS_9_11("Старші юнаки: 9-11років"),
    YOUNGER_YOUTH_12_14("Молодші юніори: 12-14років"),
    OLDER_YOUTH_15_17("Старші юніори: 15-17років"),
    ADULTS_18_PLUS("Дорослі: 18 років і старше");

    private final String displayName;

    CompetitionAgeCategory(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
