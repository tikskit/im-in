package ru.tikskit.imin.services.geocode;

import ru.tikskit.imin.services.dto.AddressDto;

public interface Geocoder {
    RequestResult request(AddressDto address);
}
