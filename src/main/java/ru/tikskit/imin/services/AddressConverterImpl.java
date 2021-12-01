package ru.tikskit.imin.services;

import org.springframework.stereotype.Component;
import ru.tikskit.imin.model.Address;
import ru.tikskit.imin.services.dto.AddressDto;

@Component
public class AddressConverterImpl implements AddressConverter {

    @Override
    public AddressDto toDto(Address address) {
        if (address == null) {
            return null;
        } else {
            return new AddressDto(address.getCountry(), address.getReqion(), address.getCity(), address.getStreet(),
                    address.getBuilding());
        }
    }

}
