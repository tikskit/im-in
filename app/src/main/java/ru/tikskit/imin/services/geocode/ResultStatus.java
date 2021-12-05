package ru.tikskit.imin.services.geocode;

public enum ResultStatus {
    RECEIVED, // Данные успешно получены
    EMPTY, // Запрос был выполнен, но данных не вернул
    LIMIT_EXCEEDED, // Превышен лимит запросов
    EXCEPTION
}
