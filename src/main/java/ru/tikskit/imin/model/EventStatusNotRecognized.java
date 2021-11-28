package ru.tikskit.imin.model;

public class EventStatusNotRecognized extends RuntimeException{
    public EventStatusNotRecognized(int value) {
        super(String.format("Unsupported event status value: %s", value));
    }


}
