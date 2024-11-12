package org.example.hospitalapi.enums;

import lombok.Getter;

@Getter
public enum GenderEnum {
    MALE("Male"),
    FEMALE("Female"),
    OTHER("Other");

    private final String gender;

    GenderEnum(String gender) {
        this.gender = gender;
    }

    @Override
    public String toString() {
        return this.gender;
    }
}
