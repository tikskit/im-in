package ru.tikskit.imin.services.geocode;

import org.springframework.stereotype.Service;
import ru.tikskit.imin.services.dto.AddressDto;
import ru.tikskit.imin.services.dto.GeoPointDto;

@Service
public class GeocoderCache implements Geocoder {
    @Override
    public GeoPointDto resolve(AddressDto address) {
        return null;
    }
}
