package ru.tikskit.imin.services;

import org.springframework.stereotype.Component;
import ru.tikskit.imin.model.GeoPoint;
import ru.tikskit.imin.services.dto.GeoPointDto;

@Component
public class GeoPointConverterImpl implements GeoPointConverter {
    @Override
    public GeoPointDto toDto(GeoPoint geoPoint) {
        if (geoPoint == null) {
            return null;
        } else {
            return new GeoPointDto(geoPoint.getLat(), geoPoint.getLon());
        }
    }
}
