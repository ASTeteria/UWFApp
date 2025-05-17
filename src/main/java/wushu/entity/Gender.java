package wushu.entity;

public enum Gender {
    MALE("Чоловік"),
    FEMALE("Жінка");

    private final String displayName;
    Gender(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
