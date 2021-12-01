package ru.tikskit.imin.services.geocode.here.dto;

import ru.tikskit.imin.services.geocode.LatLng;

public interface PositionConverter {
    LatLng convert(Position position);
}
