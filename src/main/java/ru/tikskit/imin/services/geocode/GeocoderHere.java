package ru.tikskit.imin.services.geocode;

import org.springframework.stereotype.Service;
import ru.tikskit.imin.services.dto.AddressDto;
import ru.tikskit.imin.services.dto.GeoPointDto;

/**
 * Геокодер, предоставляемый HERE Technologies
 * https://www.here.com/
 */
@Service
public class GeocoderHere implements Geocoder {
    @Override
    public GeoPointDto resolve(AddressDto address) {
        return null;
    }
}
