package ru.tikskit.imin.services;

import ru.tikskit.imin.model.Address;
import ru.tikskit.imin.services.dto.AddressDto;

public interface AddressConverter {
    AddressDto toDto(Address address);
}
