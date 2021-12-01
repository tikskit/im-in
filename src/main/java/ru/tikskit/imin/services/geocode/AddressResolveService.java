package ru.tikskit.imin.services.geocode;

import ru.tikskit.imin.services.dto.AddressDto;
import ru.tikskit.imin.services.dto.GeoPointDto;

/**
 * Служба определяет геокоординаты (lat, lon) для адреса, содержащего страну, область, город, улицу, здание
 */
public interface AddressResolveService {
    GeoPointDto resolve(AddressDto address);
}
