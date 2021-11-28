package ru.tikskit.imin.model;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.stream.Stream;

@Converter(autoApply = true)
public class PlaceTypeConverter implements AttributeConverter<EventPlaceType, Integer> {

    @Override
    public Integer convertToDatabaseColumn(EventPlaceType attribute) {
        if (attribute == null) {
            return null;
        } else {
            return attribute.getCode();
        }
    }

    @Override
    public EventPlaceType convertToEntityAttribute(Integer dbData) {
        if (dbData == null) {
            return null;
        } else {
            return Stream.of(EventPlaceType.values())
                    .filter(t -> dbData.equals(t.getCode()))
                    .findFirst()
                    .orElseThrow(() -> new PlaceTypeCodeNotRecognized(dbData));
        }
    }
}
