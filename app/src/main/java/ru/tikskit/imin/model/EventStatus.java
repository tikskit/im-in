package ru.tikskit.imin.model;

/**
 * Статус события
 */
public enum EventStatus {
    /**
     * Событие запланировано
     */
    ARRANGED(0),
    /**
     * Событие успешно прошло
     */
    COMPLETED(1),
    /**
     * Событие было отменено
     */
    CANCELLED(2);

    private final int code;

    public int getCode() {
        return code;
    }

    EventStatus(int code) {
        this.code = code;
    }
}
