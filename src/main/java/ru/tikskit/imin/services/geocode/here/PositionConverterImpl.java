package ru.tikskit.imin.services.geocode.here;

import org.springframework.stereotype.Component;
import ru.tikskit.imin.services.geocode.LatLng;
import ru.tikskit.imin.services.geocode.here.dto.Position;

@Component
public class PositionConverterImpl implements PositionConverter {
    @Override
    public LatLng convert(Position position) {
        if (position == null) {
            return null;
        } else {
            return new LatLng(position.getLat(), position.getLng());
        }
    }
}
