package ru.tikskit.imin.model;

public enum EventPlaceType {
    ADDRESS(0),
    GEO(1),
    URI(2);

    private final int code;

    public int getCode() {
        return code;
    }

    EventPlaceType(int code) {
        this.code = code;
    }
}
