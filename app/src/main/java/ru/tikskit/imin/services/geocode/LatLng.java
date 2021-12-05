package ru.tikskit.imin.services.geocode;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class LatLng {
    private final double lat;
    private final double lng;
}
