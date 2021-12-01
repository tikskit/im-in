package ru.tikskit.imin.services.geocode;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.tikskit.imin.services.dto.AddressDto;
import ru.tikskit.imin.services.dto.GeoPointDto;

@Service
public class AddressResolveServiceImpl implements AddressResolveService {

    private final Geocoder cache;
    private final Geocoder here;

    public AddressResolveServiceImpl(@Qualifier("geocoderCache") Geocoder cache,
                                     @Qualifier("geocoderHere") Geocoder here) {
        this.cache = cache;
        this.here = here;
    }

    @Override
    public GeoPointDto resolve(AddressDto address) {
        GeoPointDto result = cache.request(address);
        if (result != null) {
            return result;
        }

        return here.request(address);
    }
}
