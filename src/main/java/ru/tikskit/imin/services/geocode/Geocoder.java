package ru.tikskit.imin.services.geocode;

import ru.tikskit.imin.services.dto.AddressDto;
import ru.tikskit.imin.services.dto.GeoPointDto;

public interface Geocoder {
    GeoPointDto resolve(AddressDto address);
}
