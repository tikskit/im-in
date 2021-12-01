package ru.tikskit.imin.services.geocode.here;

public enum ResultStatus {
    RECEIVED, // Данные успешно получены
    LIMIT_EXCEEDED, // Превышен лимит запросов
    EXCEPTION
}
