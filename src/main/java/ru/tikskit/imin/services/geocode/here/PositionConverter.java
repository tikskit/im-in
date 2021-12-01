package ru.tikskit.imin.services.geocode.here;

import ru.tikskit.imin.services.geocode.LatLng;
import ru.tikskit.imin.services.geocode.here.dto.Position;

public interface PositionConverter {
    LatLng convert(Position position);
}
