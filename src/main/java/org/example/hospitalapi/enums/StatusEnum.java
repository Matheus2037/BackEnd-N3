package org.example.hospitalapi.enums;

import lombok.Getter;

@Getter
public enum StatusEnum {
    SCHEDULED("Scheduled"),
    COMPLETED("Completed"),
    CANCELED("Canceled");

    private final String status;

    StatusEnum(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return this.status;
    }
}
