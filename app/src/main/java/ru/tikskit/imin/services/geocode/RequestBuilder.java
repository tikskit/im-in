package ru.tikskit.imin.services.geocode;

import ru.tikskit.imin.services.dto.AddressDto;

import java.net.URI;

public interface RequestBuilder {
    URI build(AddressDto address);
}
