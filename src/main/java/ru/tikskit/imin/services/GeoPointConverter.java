package ru.tikskit.imin.services;

import ru.tikskit.imin.model.GeoPoint;
import ru.tikskit.imin.services.dto.GeoPointDto;

public interface GeoPointConverter {
    GeoPointDto toDto(GeoPoint geoPoint);
}
