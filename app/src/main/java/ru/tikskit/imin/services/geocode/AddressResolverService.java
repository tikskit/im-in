package ru.tikskit.imin.services.geocode;

import ru.tikskit.imin.services.dto.AddressDto;

import java.util.Optional;

/**
 * Служба определяет геокоординаты (lat, lon) для адреса, содержащего страну, область, город, улицу, здание
 */
public interface AddressResolverService {
    Optional<LatLng> resolve(AddressDto address);
}
