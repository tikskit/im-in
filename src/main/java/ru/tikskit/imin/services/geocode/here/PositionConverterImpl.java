package ru.tikskit.imin.services.geocode.here.dto;

import org.springframework.stereotype.Component;
import ru.tikskit.imin.services.geocode.LatLng;

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
