package ru.tikskit.imin.model;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.stream.Stream;

@Converter(autoApply = true)
public class EventStatusConverter implements AttributeConverter<EventStatus, Integer> {
    @Override
    public Integer convertToDatabaseColumn(EventStatus attribute) {
        if (attribute == null) {
            return null;
        } else {
            return attribute.getCode();
        }

    }

    @Override
    public EventStatus convertToEntityAttribute(Integer dbData) {
        if (dbData == null) {
            return null;
        } else {
            return Stream.of(EventStatus.values())
                    .filter(c -> dbData.equals(c.getCode()))
                    .findFirst()
                    .orElseThrow(() -> new EventStatusNotRecognized(dbData));
        }
    }
}
