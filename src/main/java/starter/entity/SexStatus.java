package starter.entity;

import lombok.Getter;

import java.util.Arrays;

@Getter
public enum SexStatus {
    MALE("male"),
    FEMALE("female");

    private final String value;
    SexStatus(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }

    public static SexStatus byValue(String value) {
        return Arrays.stream(SexStatus.values())
                .filter(sexStatus -> sexStatus.getValue().equals(value))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("This enum value not supported"));
    }
}
