package ru.tikskit.imin.model;

public class PlaceTypeCodeNotRecognized extends RuntimeException{
    public PlaceTypeCodeNotRecognized(int code) {
        super(String.format("Unsupported place type code: %s", code));
    }
}
